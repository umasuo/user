package com.umasuo.user.infrastructure;

/**
 * Created by umasuo on 17/3/9.
 */
public class Router {
  /**
   * authentication root.
   */
  public static final String USER_ROOT = "/users";


  /**
   * login.
   */
  public static final String USER_SIGN_IN = USER_ROOT + "/signin";

  /**
   * login.
   */
  public static final String USER_SIGN_IN_STATUS = USER_SIGN_IN + "/status/{id}";

  /**
   * logout.
   */
  public static final String USER_SIGN_OUT = USER_ROOT + "/signout";

  /**
   * sign up.
   */
  public static final String USER_SIGN_UP = USER_ROOT + "/signup";

}
