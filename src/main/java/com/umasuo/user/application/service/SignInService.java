package com.umasuo.user.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Token;
import com.umasuo.authentication.TokenType;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.user.application.dto.QuickSignIn;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.dto.UserSession;
import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.application.dto.mapper.SignInMapper;
import com.umasuo.user.application.dto.mapper.UserViewMapper;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.domain.service.PlatformUserService;
import com.umasuo.user.infrastructure.config.AppConfig;
import com.umasuo.user.infrastructure.util.RedisUtils;
import com.umasuo.user.infrastructure.util.TokenUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
   * configs.
   */
  @Autowired
  private transient AppConfig appConfig;

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
   * TODO 事务性!
   *
   * @param signIn the sign in
   * @return the sign in result
   */
  public SignInResult quickSignIn(QuickSignIn signIn) {
    logger.debug("Enter. signIn: {}", signIn);

    SignInResult result;

    checkValidationCode(signIn);

    PlatformUser pUser = platformUserService.getWithPhone(signIn.getPhone());

    if (pUser == null) {
      result = signUpPlatformUser(signIn);
    } else {
      DeveloperUser dUser =
          developerUserService.getUserByPlatform(pUser.getId(), signIn.getDeveloperId());

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
  private SignInResult signUpPlatformUser(QuickSignIn signUp) {
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
  private SignInResult signUpDeveloperUser(QuickSignIn signUp, PlatformUser pUser) {
    logger.debug("Enter. signUp: {}, platformUser: {}.", signUp, pUser);

    DeveloperUser savedDeveloperUser = createDeveloperUser(signUp, pUser);
    SignInResult result = signIn(pUser, savedDeveloperUser);

    logger.debug("Exit. signInResult: {}.", result);
    return result;
  }

  /**
   * 登录接口.
   *
   * @param pUser the PlatformUser entity.
   * @param dUser the DeveloperUser entity.
   * @return SignInResult
   */
  private SignInResult signIn(PlatformUser pUser, DeveloperUser dUser) {
    logger.debug("Enter. pUser: {}, dUser: {}.", pUser, dUser);

    UserView userView = UserViewMapper.toUserView(pUser, dUser);

    Token token = TokenUtil.generateToken(TokenType.CUSTOMER, dUser.getId(), appConfig
        .getTokenExpiredIn(), null);

    String tokenString = jwtUtil.generateToken(token);
    SignInResult signInResult = new SignInResult(userView, tokenString);

    cacheSignInStatus(userView, token);

    logger.debug("Exit. signInResult: {}.", signInResult);
    return signInResult;
  }


  /**
   * Create a new PlatformUser entity.
   *
   * @param signUp the signUpDeveloperUser info.
   * @return PlatformUser
   */
  private PlatformUser createPlatformUser(QuickSignIn signUp) {
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
  private DeveloperUser createDeveloperUser(QuickSignIn signUp, PlatformUser platformUser) {
    logger.debug("Enter. quickSignIn: {}, platformUser: {}.", signUp, platformUser);

    DeveloperUser dUser = SignInMapper.toDeveloperUser(signUp);
    dUser.setPUid(platformUser.getId());
    dUser.setDeveloperId(signUp.getDeveloperId());
    DeveloperUser savedUser = developerUserService.createUser(dUser);

    logger.debug("Exit. savedDpUser: {}.", savedUser);
    return savedUser;
  }

  /**
   * check the ValidationCode.
   *
   * @param signIn the signIn info
   */
  private void checkValidationCode(QuickSignIn signIn) {
    logger.debug("Enter. signIn: {}.", signIn);

    String phoneNumber = signIn.getPhone();
    String validationCode = signIn.getValidationCode();

    String key = String.format(RedisUtils.PHONE_VERIFY_KEY_FORMAT, phoneNumber);
    String cachedCode = redisTemplate.opsForValue().get(key).toString();
    if (StringUtils.isBlank(cachedCode)) {
      logger.debug("Can not find validation code by phone: {}.", phoneNumber);
      throw new NotExistException("Validation code not exist.");
    }

    if (!cachedCode.equals(validationCode)) {
      logger.debug("Validation code not match. request code: {}, basic code: {}.",
          validationCode, cachedCode);
      throw new ParametersException("Validation code not match");
    }

    redisTemplate.delete(key);

    logger.debug("Exit.");
  }

  /**
   * cache the user's sign in status and info.
   */
  private void cacheSignInStatus(UserView userView, Token token) {
    logger.debug("Enter. userView: {}, token: {}.", userView, token);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT,
        userView.getDeveloperId(), userView.getUserId());

    UserSession session = new UserSession(userView, token);

    //cache the result
    redisTemplate.boundHashOps(userKey).put(RedisUtils.USER_TOKEN_KEY, session);
    redisTemplate.expire(userKey, 7, TimeUnit.DAYS);//7天后过期

    logger.debug("Exit.");
  }
}
