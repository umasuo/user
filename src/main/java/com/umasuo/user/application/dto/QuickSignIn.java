package com.umasuo.user.application.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 用户通过短信验证码进行快速登录.
 * Created by umasuo on 17/3/9.
 */
@Data
@ToString
public class QuickSignIn implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -1226855896214404115L;

  /**
   * user's mobile phone. unique on this platform.
   */
  @NotNull
  private String phone;

  /**
   * developer id.
   */
  @NotNull
  private String developerId;

  /**
   * The validation code.
   */
  @NotNull
  private String validationCode;
}
