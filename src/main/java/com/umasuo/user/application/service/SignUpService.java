package com.umasuo.user.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.TokenType;
import com.umasuo.user.application.dto.SignIn;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.application.dto.mapper.SignInMapper;
import com.umasuo.user.application.dto.mapper.UserViewMapper;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.domain.service.PlatformUserService;
import com.umasuo.user.infrastructure.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * Created by umasuo on 17/3/9.
 * PlatformUser sign up service.
 * TODO we should used transaction here.
 */
@Service
public class SignUpService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignUpService.class);

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
   * platform user service.
   */
  @Autowired
  private transient PlatformUserService platformUserService;

  /**
   * developer user service.
   */
  @Autowired
  private transient DeveloperUserService developerUserService;

  /**
   * sign up.
   *
   * @param signUp SignIn
   * @return
   */
  public SignInResult signUp(SignIn signUp) {
    logger.debug("SignUp: {}", signUp);
    Assert.notNull(signUp);
    //create platform user.
    PlatformUser platformUser = SignInMapper.toPlatformUser(signUp);
    PlatformUser pUser = platformUserService.createUser(platformUser);

    //create developer user.
    DeveloperUser developerUser = SignInMapper.toDeveloperUser(signUp);
    developerUser.setPUid(pUser.getId());
    String hashedPassword = PasswordUtil.hashPassword(developerUser.getPassword());
    developerUser.setPassword(hashedPassword);
    DeveloperUser dUser = developerUserService.createUser(developerUser);

    //TODO add user sign in status to cache.
    UserView userView = UserViewMapper.toUserView(pUser, dUser);
    String token = jwtUtil.generateToken(TokenType.CUSTOMER, dUser.getId(), Integer.MAX_VALUE,
        new ArrayList<>());
    SignInResult signInResult = new SignInResult(userView, token);

    cacheSignInStatus(signInResult, dUser.getId());
    logger.debug("SignInResult: {}", signInResult);
    return signInResult;
  }

  /**
   * cache the user's sign in status and info.
   *
   * @param signInResult
   * @param userId
   */
  private void cacheSignInStatus(SignInResult signInResult, String userId) {
    String userKey = SignInService.USER_CACHE_KEY_PRE + userId;
    //cache the result
    redisTemplate.boundHashOps(userKey).put(SignInService.SIGN_IN_CACHE_KEY, signInResult);
  }

}
