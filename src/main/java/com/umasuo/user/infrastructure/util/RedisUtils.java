package com.umasuo.user.infrastructure.util;

/**
 * Created by Davis on 17/7/5.
 */
public final class RedisUtils {

  public static final String PHONE_KEY_FORMAT = "user:%s";

  public static final String PHONE_CODE_KEY_FORMAT = "user:%s:%s";

  public static final String USER_KEY_FORMAT = "user:%s:%s";

  public static final String USER_TOKEN_KEY = "token";
}
