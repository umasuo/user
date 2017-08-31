package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Updater for reset password.
 */
@Data
public class ResetPassword implements Serializable {

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
   * DeveloperId.
   */
  private String developerId;
}
