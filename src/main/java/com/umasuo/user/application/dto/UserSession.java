package com.umasuo.user.application.dto;

import com.umasuo.authentication.Token;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by umasuo on 17/6/19.
 */
@Data
public class UserSession implements Serializable {
  private static final long serialVersionUID = 3640903092407994272L;

  private UserView userView;

  private String token;

  private long lastActiveTime;

  private static long expireIn = 30 * 24 * 60 * 60 * 1000L;//30 天过期

  public UserSession(UserView userView, String token) {
    this.userView = userView;
    this.token = token;
    lastActiveTime = System.currentTimeMillis();
  }

  public UserView getUserView() {
    return userView;
  }

  public void setUserView(UserView userView) {
    this.userView = userView;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getLastActiveTime() {
    return lastActiveTime;
  }

  public void setLastActiveTime(long lastActiveTime) {
    this.lastActiveTime = lastActiveTime;
  }

  public long getExpireIn() {
    return expireIn;
  }

  public void setExpireIn(long expireIn) {
    this.expireIn = expireIn;
  }
}
