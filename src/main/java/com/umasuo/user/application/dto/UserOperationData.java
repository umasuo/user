package com.umasuo.user.application.dto;

import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;
import lombok.Data;

/**
 * 用户统计数据，用来给开发者查看用户注册情况的.
 */
@Data
public class UserOperationData {

  /**
   * User id.
   */
  private String userId;

  /**
   * Phone.
   */
  private String phone;

  /**
   * Register time.
   */
  private String registerTime;

  /**
   * Builder.
   *
   * @param platformUser
   * @param developerUser
   * @return
   */
  public static UserOperationData build(PlatformUser platformUser, DeveloperUser developerUser) {
    UserOperationData userData = new UserOperationData();

    userData.setPhone(platformUser.getPhone());
    userData.setUserId(developerUser.getId());
    userData.setRegisterTime(developerUser.getCreatedAt().toString());

    return userData;
  }
}
