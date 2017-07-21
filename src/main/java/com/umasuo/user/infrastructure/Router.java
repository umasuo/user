package com.umasuo.user.infrastructure;

/**
 * Created by umasuo on 17/3/9.
 */
public class Router {

  public static final String VERSION = "/v1";
  /**
   * user root.
   */
  public static final String USER_ROOT = VERSION + "/users";

  /**
   * user root.
   */
  public static final String USER_WITH_ID = VERSION + "/users/{id}";

  /**
   * login.
   */
  public static final String USER_SIGN_IN = USER_ROOT + "/signin";

  /**
   * login with phone and password.
   */
  public static final String USER_SIGN_IN_PWD = USER_ROOT + "/login";

  /**
   * register.
   */
  public static final String USER_REGISTER = USER_ROOT + "/register";

  /**
   * register.
   */
  public static final String USER_RESET_PASSWORD = USER_ROOT + "/resetPassword";

  /**
   * login.
   */
  public static final String USER_SIGN_IN_STATUS = USER_ROOT + "/{id}/status";

  /**
   * logout.
   */
  public static final String USER_SIGN_OUT = USER_ROOT + "/signout";

  /**
   * sign up.
   */
  public static final String USER_SIGN_UP = USER_ROOT + "/signup";

  /**
   * The constant VALIDATION_CODE.
   */
  public static final String VALIDATION_CODE = USER_ROOT + "/validationCodes";

  /**
   * The constant PHONE_NUMBER.
   */
  public static final String PHONE_NUMBER = "phoneNumber";

  /**
   * The constant GROUP.
   */
  public static final String GROUP = USER_ROOT + "/groups";

  /**
   * The constant GROUP_ID.
   */
  public static final String GROUP_ID = "id";

  /**
   * The constant GROUP_WITH_ID.
   */
  public static final String GROUP_WITH_ID = GROUP + "/{" + GROUP_ID + "}";

  /**
   * The constant developer_id.
   */
  public static final String DEVELOPER_ID = "developerId";

  /**
   * The constant REPORT_ROOT.
   */
  public static final String REPORT_ROOT = USER_ROOT + "/reports";
}
