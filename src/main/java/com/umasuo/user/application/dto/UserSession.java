package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * User session.
 */
@Data
public class UserSession implements Serializable {
  /**
   * Auto generated serial version id.
   */
  private static final long serialVersionUID = 3640903092407994272L;

  /**
   * User view.
   */
  private UserView userView;

  /**
   * Token.
   */
  private String token;

  /**
   * Last active time.
   */
  private long lastActiveTime;

  /**
   * Session expire time.
   */
  public static final long EXPIRE_IN = 30 * 24 * 60 * 60 * 1000L;//30 天过期

  /**
   * Constructor.
   *
   * @param userView
   * @param token
   */
  public UserSession(UserView userView, String token) {
    this.userView = userView;
    this.token = token;
    lastActiveTime = System.currentTimeMillis();
  }

  /**
   * Getter.
   *
   * @return
   */
  public UserView getUserView() {
    return userView;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setUserView(UserView userView) {
    this.userView = userView;
  }

  /**
   * Getter.
   *
   * @return
   */
  public String getToken() {
    return token;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * Getter.
   *
   * @return
   */
  public long getLastActiveTime() {
    return lastActiveTime;
  }

  /**
   * Setter.
   *
   * @return
   */
  public void setLastActiveTime(long lastActiveTime) {
    this.lastActiveTime = lastActiveTime;
  }

}
