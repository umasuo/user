package com.umasuo.user.infrastructure.util;

/**
 * Redis utils.
 */
public final class RedisUtils {

  /**
   * Phone key.
   */
  public static final String PHONE_KEY_FORMAT = "user:%s";

  /**
   * Sms code key
   */
  public static final String PHONE_CODE_KEY_FORMAT = "user:%s:%s";

  /**
   * User key.
   */
  public static final String USER_KEY_FORMAT = "user:%s:%s";

  /**
   * User session key.
   */
  public static final String USER_SESSION_KEY = "session";
}
