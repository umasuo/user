package com.umasuo.user.application.dto;

import lombok.Data;

/**
 * Created by umasuo on 17/3/9.
 */
@Data
public class UserView {

  /**
   * userId of the developer user.
   */
  private String userId;

  /**
   * developer id.
   */
  private String developerId;

  /**
   * Which device this user from.
   */
  private String deviceDefinitionId;

  /**
   * user's email. unique on this platform.
   */
  private String email;

  /**
   * user's mobile phone. unique on this platform.
   */
  private String phone;

  /**
   * user's externalId.
   */
  private String externalId;

  /**
   * user's name.
   */
  private String name;

  /**
   * user's icon.
   */
  private String icon;

  private int age;

  private String signature;

}
