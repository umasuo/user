package com.umasuo.user.application.dto;

import lombok.ToString;

import java.io.Serializable;

/**
 * Login status.
 */
@ToString
public class LoginStatus implements Serializable {

  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 593519935544850012L;

  /**
   * 开发者ID.
   */
  private String developerId;

  /**
   * 用户Id.
   */
  private String userId;

  /**
   * 登录状态.
   */
  private boolean isLogin;

  /**
   * Constructor.
   *
   * @param developerId
   * @param userId
   * @param isLogin
   */
  public LoginStatus(String developerId, String userId, boolean isLogin) {
    this.developerId = developerId;
    this.userId = userId;
    this.isLogin = isLogin;
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getDeveloperId() {
    return developerId;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setDeveloperId(String developerId) {
    this.developerId = developerId;
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Getter.
   *
   * @return
   */
  public boolean isLogin() {
    return isLogin;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setLogin(boolean login) {
    isLogin = login;
  }
}
