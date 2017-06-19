package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/6/19.
 */
public class LoginStatus implements Serializable {

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

  public LoginStatus(String developerId, String userId, boolean isLogin) {
    this.developerId = developerId;
    this.userId = userId;
    this.isLogin = isLogin;
  }

  public String getDeveloperId() {
    return developerId;
  }

  public void setDeveloperId(String developerId) {
    this.developerId = developerId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public boolean isLogin() {
    return isLogin;
  }

  public void setLogin(boolean login) {
    isLogin = login;
  }
}
