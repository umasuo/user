package com.umasuo.user.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.TokenType;
import com.umasuo.exception.PasswordErrorException;
import com.umasuo.user.application.dto.SignIn;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.dto.UserView;
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
 */
@Service
public class SignInService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignInService.class);

  /**
   * the key in map which in cache.
   */
  public final static String SIGN_IN_CACHE_KEY = "signin";
  public final static String USER_IN_CACHE_KEY_PRE = "user:";

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
   * sign in.
   *
   * @param signIn
   * @return
   */
  public SignInResult signIn(SignIn signIn) {
    logger.debug("SignUp: {}", signIn);
    Assert.notNull(signIn);

    PlatformUser pUser = platformUserService.getWithPhone(signIn.getPhone());
    DeveloperUser dUser = developerUserService.getUserInfo(pUser.getId(), signIn.getDeveloperId());

    boolean isPwdRight = PasswordUtil.checkPassword(signIn.getPassword(), dUser.getPassword());
    if (!isPwdRight) {
      throw new PasswordErrorException("Password or phone is not correct.");
    }

    UserView userView = UserViewMapper.toUserView(pUser, dUser);
    String token = jwtUtil.generateToken(TokenType.CUSTOMER, dUser.getId(), jwtUtil.getExpiresIn(),
        new ArrayList<>());
    SignInResult signInResult = new SignInResult(userView, token);


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
    String userKey = USER_IN_CACHE_KEY_PRE + userId;
    //cache the result
    redisTemplate.boundHashOps(userKey).put(SIGN_IN_CACHE_KEY, signInResult);
  }
}
