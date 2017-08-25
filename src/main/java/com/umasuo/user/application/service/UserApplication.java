package com.umasuo.user.application.service;

import com.google.common.collect.Lists;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.application.dto.ResetPassword;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.dto.UserOperationData;
import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.application.dto.mapper.OperationDataMapper;
import com.umasuo.user.application.dto.mapper.UserViewMapper;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.domain.service.PlatformUserService;
import com.umasuo.user.infrastructure.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

/**
 * Created by Davis on 17/7/7.
 */
@Service
public class UserApplication {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);

  @Autowired
  private transient PlatformUserService platformUserService;

  @Autowired
  private transient DeveloperUserService developerUserService;

  @Autowired
  private transient ValidationService validationService;

  @Autowired
  private transient SignInService signInService;


  public List<UserOperationData> getUsers(String developerId, String userId, String phone) {
    logger.info("Enter. developerId: {}, userId: {}, phone: {}.", developerId, userId, phone);

    List<UserOperationData> result = Lists.newArrayList();

    List<DeveloperUser> developerUsers = developerUserService.getUsers(developerId, userId);

    if (StringUtils.isNotBlank(phone)) {
      PlatformUser platformUser = platformUserService.getWithPhone(phone);
      result = OperationDataMapper.filter(platformUser, developerUsers);
    } else {
      List<String> platformUserIds = getPlatformUserIds(developerUsers);
      List<PlatformUser> platformUsers = platformUserService.getWithIds(platformUserIds);

      result = OperationDataMapper.merge(platformUsers, developerUsers);
    }

    logger.info("Exit. user size: {}.", result.size());

    return result;
  }

  private List<String> getPlatformUserIds(List<DeveloperUser> developerUsers) {

    List<String> result = developerUsers.stream()
        .map(user -> user.getPUid()).collect(Collectors.toList());

    return result;
  }

  /**
   * Update group.
   *
   * @param id       the id
   * @param userView new values
   * @return the group
   */
  @Transactional
  public UserView update(String id, UserView userView) {
    logger.debug("Enter. groupId: {}, version: {}, userView: {}.", id, userView);

    DeveloperUser dUser = developerUserService.getUserById(id);
    PlatformUser pUser = platformUserService.getWithId(dUser.getPUid());

    UserViewMapper.copyValue(pUser, dUser, userView);

    pUser = platformUserService.save(pUser);
    dUser = developerUserService.save(dUser);

    logger.debug("Exit. CategoryId: {}.", id);
    return UserViewMapper.toUserView(pUser, dUser);
  }

  /**
   * 重置用户密码.
   *
   * @param resetPassword
   * @return
   */
  public SignInResult resetPassword(ResetPassword resetPassword) {
    logger.debug("Enter. resetPassword: {}.", resetPassword);

    validationService.validateCode(resetPassword.getPhone(), resetPassword.getSmsCode());
    PlatformUser pUser = platformUserService.getWithPhone(resetPassword.getPhone());
    if (pUser == null) {
      throw new NotExistException("User not exit for phone: " + resetPassword.getPhone());
    }

    DeveloperUser dUser = developerUserService.getUserByPlatform(pUser.getPhone(), resetPassword
        .getDeveloperId());
    if (dUser == null) {
      throw new NotExistException("User not exit for developer: " + resetPassword.getDeveloperId());
    }

    dUser.setPassword(PasswordUtil.hashPassword(resetPassword.getPassword()));
    developerUserService.save(dUser);

    SignInResult result = signInService.signIn(pUser, dUser);

    logger.debug("Exit. result: {}.", result);
    return result;
  }

  public Long countUsers() {
    logger.debug("Enter.");

    Long result = platformUserService.countUsers();

    logger.debug("Exit. platform user count: {}.", result);

    return result;
  }
}
