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

  /**
   * The constant VALIDATION_CODE.
   */
  public static final String VALIDATION_CODE = USER_ROOT + "/validationCode";

  /**
   * The constant PHONE_NUMBER.
   */
  public static final String PHONE_NUMBER = "phoneNumber";

  /**
   * The constant GROUP.
   */
  public static final String GROUP = USER_ROOT + "/group";

  /**
   * The constant GROUP_ID.
   */
  public static final String GROUP_ID = "groupId";

  /**
   * The constant GROUP_WITH_ID.
   */
  public static final String GROUP_WITH_ID = GROUP + "/{" + GROUP_ID + "}";

  /**
   * The constant developer_id.
   */
  public static final String DEVELOPER_ID = "developerId";
}
