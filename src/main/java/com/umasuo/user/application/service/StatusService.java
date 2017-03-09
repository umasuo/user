package com.umasuo.user.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.user.application.dto.SignInResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by umasuo on 17/3/9.
 * Sign in status service.
 */
public class StatusService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(StatusService.class);

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * JWT(json web token) update
   */
  @Autowired
  private transient JwtUtil jwtUtil;

  /**
   * check login status. for auth check.
   * this can only be accessed in internal net work.
   *
   * @param id
   * @return boolean
   */
  public boolean checkSignInStatus(String id) {
    logger.debug("CheckSignInStatus: id: {}", id);
    String userKey = SignInService.USER_CACHE_KEY_PRE + id;

    SignInResult signInResult = (SignInResult) redisTemplate.opsForHash().get(userKey,
        SignInService.SIGN_IN_CACHE_KEY);
    if (signInResult == null) {
      return false;
    }

    return checkToken(signInResult.getToken());
  }

  private boolean checkToken(String tokenString) {
    if (tokenString == null) {
      return false;
    }

//    Token token = jwtUtil.parseToken(tokenString);
    //TODO check the scope.

    return true;
  }
}
