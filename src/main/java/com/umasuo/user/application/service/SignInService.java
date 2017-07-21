package com.umasuo.user.application.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.application.dto.QuickSignIn;
import com.umasuo.user.application.dto.SignIn;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
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

  @Autowired
  private MessageApplication messageApplication;

  @Autowired
  private ValidationService validationService;

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

    validationService.validateCode(signIn.getPhone(), signIn.getValidationCode());

    PlatformUser pUser = platformUserService.getWithPhone(signIn.getPhone());

    if (pUser == null) {
      pUser = createPlatformUser(signIn);
    }

    DeveloperUser dUser = developerUserService.getUserByPlatform(pUser.getId(), signIn
        .getDeveloperId());
    if (dUser == null) {
      dUser = createDeveloperUser(signIn, pUser);
    }

    result = signIn(pUser, dUser);

    logger.debug("Exit. SignInResult: {}.", result);
    return result;
  }

  /**
   * 手机密码登录.
   *
   * @param signIn
   * @return
   */
  public SignInResult signIn(SignIn signIn) {
    logger.debug("Enter. signIn: {}.", signIn);

    PlatformUser pUser = platformUserService.getWithPhone(signIn.getPhone());
    if (pUser == null) {
      throw new NotExistException("User not exit for phone: " + signIn.getPhone());
    }

    DeveloperUser dUser = developerUserService.getUserByPlatform(pUser.getId(), signIn
        .getDeveloperId());
    if (dUser == null) {
      throw new NotExistException("User not exit for developer: " + signIn.getDeveloperId());
    }

    SignInResult result = signIn(pUser, dUser);

    logger.debug("Exit. result: {}.", result);
    return result;
  }

  /**
   * 登录接口.
   *
   * @param pUser the PlatformUser entity.
   * @param dUser the DeveloperUser entity.
   * @return SignInResult
   */
  public SignInResult signIn(PlatformUser pUser, DeveloperUser dUser) {
    logger.debug("Enter. pUser: {}, dUser: {}.", pUser, dUser);

    UserView userView = UserViewMapper.toUserView(pUser, dUser);

    String token = UUID.randomUUID().toString();

    SignInResult signInResult = new SignInResult(userView, token);

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
   * @param signUp       the signUpDeveloperUser info.
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
   * cache the user's sign in status and info.
   */
  private void cacheSignInStatus(UserView userView, String token) {
    logger.debug("Enter. userView: {}, token: {}.", userView, token);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT,
        userView.getDeveloperId(), userView.getUserId());

    UserSession session = new UserSession(userView, token);

    //cache the result
    redisTemplate.boundHashOps(userKey).put(RedisUtils.USER_SESSION_KEY, session);
    redisTemplate.expire(userKey, 30, TimeUnit.DAYS);//7天后过期
    messageApplication.addMqttUser(userView.getUserId(), token);
    logger.debug("Exit.");
  }

}
