package com.umasuo.user.application.service;

import com.google.common.collect.Lists;
import com.umasuo.user.application.dto.UserOperationData;
import com.umasuo.user.application.dto.mapper.OperationDataMapper;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.domain.service.PlatformUserService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Davis on 17/7/7.
 */
@Service
public class UserApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UserApplication.class);

  @Autowired
  private transient PlatformUserService platformUserService;


  @Autowired
  private transient DeveloperUserService developerUserService;

  public List<UserOperationData> getUsers(String developerId, String userId, String phone) {
    LOG.info("Enter. developerId: {}, userId: {}, phone: {}.", developerId, userId, phone);

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

    LOG.info("Exit. user size: {}.", result.size());

    return result;
  }

  private List<String> getPlatformUserIds(List<DeveloperUser> developerUsers) {

    List<String> result = developerUsers.stream()
        .map(user -> user.getPUid()).collect(Collectors.toList());

    return result;
  }
}
