package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * User view.
 *
 * Created by umasuo on 17/3/9.
 */
@Data
public class UserView implements Serializable{

  /**
   * The serialVersionUID
   */
  private static final long serialVersionUID = 6276176099504266922L;

  /**
   * UserId of the developer user.
   */
  private String userId;

  /**
   * Developer id.
   */
  private String developerId;

  /**
   * Which device this user from.
   */
  private String deviceDefinitionId;

  /**
   * User's email. unique on this platform.
   */
  private String email;

  /**
   * User's mobile phone. unique on this platform.
   */
  private String phone;

  /**
   * User's externalId.
   */
  private String externalId;

  /**
   * User's name.
   */
  private String name;

  /**
   * User's icon.
   */
  private String icon;

  /**
   * User's age.
   */
  private int age;

  /**
   * User's signature.
   */
  private String signature;
}
