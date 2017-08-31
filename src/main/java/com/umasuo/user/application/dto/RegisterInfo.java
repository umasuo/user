package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Register info.
 */
@Data
public class RegisterInfo implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 2333151638836537082L;

  /**
   * Phone.
   */
  private String phone;

  /**
   * Sms code.
   */
  private String smsCode;

  /**
   * Password.
   */
  private String password;

  /**
   * Developer id.
   */
  private String developerId;
}
