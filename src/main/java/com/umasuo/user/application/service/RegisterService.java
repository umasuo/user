package com.umasuo.user.application.service;

import com.umasuo.user.application.dto.RegisterInfo;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.domain.service.PlatformUserService;
import com.umasuo.user.infrastructure.config.AppConfig;
import com.umasuo.user.infrastructure.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * The type register service.
 */
@Service
public class RegisterService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(RegisterService.class);

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * configs.
   */
  @Autowired
  private transient AppConfig appConfig;

  /**
   * platform user service.
   */
  @Autowired
  private transient PlatformUserService platformUserService;

  /**
   * developer user service.
   */
  @Autowired
  private transient DeveloperUserService developerUserService;

  @Autowired
  private MessageApplication messageApplication;

  @Autowired
  private SignInService signInService;

  @Autowired
  private ValidationService validationService;

  /**
   * register.
   * TODO 事务性!
   *
   * @param register the sign in
   * @return the sign in result
   */
  public SignInResult register(RegisterInfo register) {
    logger.debug("Enter. register: {}", register);


    validationService.validateCode(register.getPhone(), register.getSmsCode());

    PlatformUser pUser = platformUserService.getWithPhone(register.getPhone());

    if (pUser == null) {
      pUser = createPlatformUser(register.getPhone());
    }

    DeveloperUser dUser = developerUserService.getUserByPlatform(pUser.getId(), register
        .getDeveloperId());

    if (dUser == null) {
      dUser = createDeveloperUser(pUser.getId(), register.getDeveloperId(), register.getPassword());
    }

    SignInResult result = signInService.signIn(pUser, dUser);


    logger.debug("Exit. SignInResult: {}.", result);
    return result;
  }


  /**
   * 创建平台用户
   *
   * @param phone
   * @return
   */
  private PlatformUser createPlatformUser(String phone) {
    PlatformUser user = new PlatformUser();
    user.setPhone(phone);
    return platformUserService.save(user);
  }

  /**
   * 创建开发者用户.
   *
   * @param pId
   * @param developerId
   * @param password
   * @return
   */
  private DeveloperUser createDeveloperUser(String pId, String developerId, String password) {
    DeveloperUser user = new DeveloperUser();
    user.setPUid(pId);
    user.setDeveloperId(developerId);
    user.setPassword(PasswordUtil.hashPassword(password));
    return developerUserService.save(user);
  }
}
