package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * The type Sign in result.
 */
@Data
public class SignInResult implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = 517596150259943683L;

  /**
   * Customer for view.
   */
  private UserView userView;

  /**
   * String token.
   */
  private String token;

  /**
   * Instantiates a new Sign in result.
   *
   * @param userView the user view
   * @param token the token
   */
  public SignInResult(UserView userView, String token) {
    this.userView = userView;
    this.token = token;
  }
}
