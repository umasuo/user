package com.umasuo.user.infrastructure;

/**
 * Created by Davis on 17/6/2.
 */
public class AuthorizationRouter {
  /**
   * Authentication root.
   */
  public static final String AUTHENTICATION_ROOT = "/";

  /**
   * Id params.
   */
  public static final String ID = "{id}";

  /**
   * The constant AUTHENTICATION_HEALTH_CHECK.
   */
  public static final String AUTHENTICATION_HEALTH_CHECK = AUTHENTICATION_ROOT + "health";

  /**
   * Module root.
   */
  public static final String MODULE_ROOT = AUTHENTICATION_ROOT + "modules/";

  /**
   * Module with id.
   */
  public static final String MODULE_WITH_ID = MODULE_ROOT + ID;


  /**
   * Scope root.
   */
  public static final String SCOPE_ROOT = AUTHENTICATION_ROOT + "scopes/";

  /**
   * Scope with id.
   */
  public static final String SCOPE_WITH_ID = SCOPE_ROOT + ID;


  /**
   * Role root.
   */
  public static final String ROLE_ROOT = AUTHENTICATION_ROOT + "roles/";

  /**
   * Role with id.
   */
  public static final String ROLE_WITH_ID = ROLE_ROOT + ID;

  /**
   * Admin root.
   */
  public static final String ADMIN_ROOT = AUTHENTICATION_ROOT + "admins/";

  /**
   * Admin with id.
   */
  public static final String ADMIN_WITH_ID = ADMIN_ROOT + ID;

  /**
   * Admin login.
   */
  public static final String ADMIN_LOGIN = AUTHENTICATION_ROOT + "login/";

  /**
   * Admin session status.
   */
  public static final String ADMIN_SESSION_STATUS = AUTHENTICATION_ROOT + "status/";
}
