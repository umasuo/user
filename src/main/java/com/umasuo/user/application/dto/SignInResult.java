package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignInResult implements Serializable {

  /**
   * customer for view.
   */
  private UserView userView;

  /**
   * String token.
   */
  private String token;

  public SignInResult(UserView userView, String token) {
    this.userView = userView;
    this.token = token;
  }
}
