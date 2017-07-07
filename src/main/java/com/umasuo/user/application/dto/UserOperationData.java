package com.umasuo.user.application.dto;

import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;

import lombok.Data;

/**
 * Created by Davis on 17/7/7.
 */
@Data
public class UserOperationData {

  private String userId;

  private String phone;

  private String registerTime;

  public static UserOperationData build(PlatformUser platformUser, DeveloperUser developerUser) {
    UserOperationData userData = new UserOperationData();

    userData.setPhone(platformUser.getPhone());
    userData.setUserId(developerUser.getId());
    userData.setRegisterTime("" + developerUser.getCreatedAt());

    return userData;
  }
}
