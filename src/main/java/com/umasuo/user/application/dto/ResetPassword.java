package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/7/21.
 */
@Data
public class ResetPassword implements Serializable {

  private static final long serialVersionUID = 2333151638836537082L;

  private String phone;
  private String smsCode;
  private String password;
  private String developerId;
}
