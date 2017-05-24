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
import com.umasuo.user.infrastructure.validator.ValidationCodeValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;

/**
 * The type Sign in service.
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
  /**
   * The constant USER_CACHE_KEY_PRE.
   */
  public final static String USER_CACHE_KEY_PRE = "user:";

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
   * @param signIn the sign in
   * @return the sign in result
   */
  public SignInResult quickSignIn(SignIn signIn) {
    logger.debug("SignUp: {}", signIn);
    Assert.notNull(signIn, "SingIn object can not be null");

    SignInResult result = null;

    checkValidationCode(signIn);

    PlatformUser pUser = platformUserService.getWithPhone(signIn.getPhone());

    if (pUser == null) {
      result = signUpPlatformUser(signIn);
    } else {
      DeveloperUser dUser = developerUserService
          .getUserInfo(pUser.getId(), signIn.getDeveloperId());

      if (dUser == null) {
        result = signUpDeveloperUser(signIn, pUser);
      } else {
        result = signIn(pUser, dUser);
      }
    }

    logger.debug("Exit. SignInResult: {}.", result);
    return result;
  }

  /**
   * SignUp a new PlatformUser.
   *
   * @param signUp the signUp info
   * @return SignInResult
   */
  private SignInResult signUpPlatformUser(SignIn signUp) {
    logger.debug("Enter. signUp: {}.", signUp);

    PlatformUser savedPlatformUser = createPlatformUser(signUp);
    SignInResult result = signUpDeveloperUser(signUp, savedPlatformUser);

    logger.debug("Exit. signInResult: {}.", result);

    return result;
  }

  /**
   * SignUp.
   *
   * @param signUp sign up info
   * @param pUser PlatformUser entity
   * @return SignInResult
   */
  private SignInResult signUpDeveloperUser(SignIn signUp, PlatformUser pUser) {
    logger.debug("Enter. signUp: {}, platformUser: {}.", signUp, pUser);

    DeveloperUser savedDeveloperUser = createDeveloperUser(signUp, pUser);
    SignInResult result = signIn(pUser, savedDeveloperUser);

    logger.debug("Exit. signInResult: {}.", result);
    return result;
  }

  /**
   * SignIn.
   *
   * @param pUser the PlatformUser entity.
   * @param dUser the DeveloperUser entity.
   * @return SignInResult
   */
  private SignInResult signIn(PlatformUser pUser, DeveloperUser dUser) {
    logger.debug("Enter. pUser: {}, dUser: {}.", pUser, dUser);
    UserView userView = UserViewMapper.toUserView(pUser, dUser);
    String token = jwtUtil.generateToken(TokenType.CUSTOMER, dUser.getId(), Integer.MAX_VALUE,
        new ArrayList<>());
    SignInResult signInResult = new SignInResult(userView, token);

    cacheSignInStatus(signInResult, dUser.getId());

    logger.debug("Exit. signInResult: {}.", signInResult);
    return signInResult;
  }


  /**
   * Create a new PlatformUser entity.
   *
   * @param signUp the signUpDeveloperUser info.
   * @return PlatformUser
   */
  private PlatformUser createPlatformUser(SignIn signUp) {
    logger.debug("Enter. signUpDeveloperUser: {}.", signUp);

    PlatformUser platformUser = SignInMapper.toPlatformUser(signUp);
    PlatformUser savedPlatformUser = platformUserService.createUser(platformUser);

    logger.debug("Exit.");
    return savedPlatformUser;
  }

  /**
   * create a new DeveloperUser entity.
   *
   * @param signUp the signUpDeveloperUser info.
   * @param platformUser the PlatformUser entity.
   * @return DeveloperUser
   */
  private DeveloperUser createDeveloperUser(SignIn signUp, PlatformUser platformUser) {
    logger.debug("Enter. signUpDeveloperUser: {}, platformUser: {}.", signUp, platformUser);

    DeveloperUser developerUser = SignInMapper.toDeveloperUser(signUp);
    developerUser.setPUid(platformUser.getId());
    DeveloperUser savedDeveloperUser = developerUserService.createUser(developerUser);

    logger.debug("Exit.");
    return savedDeveloperUser;
  }

  /**
   * check the ValidationCode.
   *
   * @param signIn the signIn info
   */
  private void checkValidationCode(SignIn signIn) {
    logger.debug("Enter. signIn: {}.", signIn);

    String phoneNumber = signIn.getPhone();
    String validationCode = signIn.getValidationCode();

    String basicValidationCode = (String) redisTemplate.opsForValue().get(phoneNumber);

    ValidationCodeValidator.validate(basicValidationCode, validationCode, phoneNumber);

    logger.debug("Exit.");
  }

  /**
   * cache the user's sign in status and info.
   */
  private void cacheSignInStatus(SignInResult signInResult, String userId) {
    logger.debug("Enter. signInResult: {}, userId: {}.", signInResult, userId);

    String userKey = USER_CACHE_KEY_PRE + userId;
    //cache the result
    redisTemplate.boundHashOps(userKey).put(SIGN_IN_CACHE_KEY, signInResult);

    logger.debug("Exit.");
  }
}
