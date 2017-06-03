package com.umasuo.user.infrastructure;

/**
 * Created by Davis on 17/6/2.
 */
public class AuthorizationRouter {

  /**
   * Authentication root.
   */
  public static final String AUTHENTICATION_ROOT = "/auth";

  /**
   * Id params.
   */
  public static final String ID = "{id}";

  /**
   * The constant AUTHORIZATION_WITH_ID.
   */
  public static final String AUTHORIZATION_WITH_ID = AUTHENTICATION_ROOT + ID;

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
}
