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

  private Token token;

  public UserSession(UserView userView, Token token) {
    this.userView = userView;
    this.token = token;
  }

  public UserView getUserView() {
    return userView;
  }

  public void setUserView(UserView userView) {
    this.userView = userView;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }
}
