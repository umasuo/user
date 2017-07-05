package com.umasuo.user.application.service;

import com.umasuo.authentication.JwtUtil;
import com.umasuo.authentication.Token;
import com.umasuo.user.application.dto.LoginStatus;
import com.umasuo.user.application.dto.UserSession;
import com.umasuo.user.infrastructure.util.RedisUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by umasuo on 17/3/9.
 * Sign in status service.
 */
@Service
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
   * @param userId 用户ID
   * @param developerId 开发者ID
   * @param token token
   * @return LoginStatus
   */
  public LoginStatus checkSignInStatus(String userId, String developerId, String token) {
    logger.debug("CheckSignInStatus: id: {}", userId);

    LoginStatus loginStatus = new LoginStatus(developerId, userId, false);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT, developerId, userId);

    UserSession userSession = (UserSession) redisTemplate.opsForHash().get(userKey,
        RedisUtils.USER_TOKEN_KEY);

    if (userSession != null && userSession.getUserView().getDeveloperId().equals(developerId)) {
      loginStatus.setLogin(true);
    }

    //检查token是否还在有效期内
    if (!verifyToken(userSession, token)) {
      loginStatus.setLogin(false);
    }

    return loginStatus;
  }

  /**
   * 检查token和session的合法性.
   * token是否能够解出, session是否在有效期内
   *
   * @param session session
   * @param tokenStr tokenStr
   * @return boolean
   */
  private boolean verifyToken(UserSession session, String tokenStr) {
    if (tokenStr == null) {
      return false;
    }

    // 检查传入token是否合法
    jwtUtil.parseToken(tokenStr);

    //检查当前session 是否合法
    Token token = session.getToken();

    long lifeTime = token.getGenerateTime() + token.getExpiresIn();
    long curTime = System.currentTimeMillis();
    if (curTime > lifeTime) {
      // TODO: 17/6/19 这里是否需要把session删除了
      return false;
    }

    //自动延长token 有效期
    token.setGenerateTime(curTime);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT,
        session.getUserView().getDeveloperId(), session.getUserView().getUserId());

    redisTemplate.boundHashOps(userKey).put(RedisUtils.USER_TOKEN_KEY, session);

    redisTemplate.expire(userKey, 7, TimeUnit.DAYS);//7天后过期
    //TODO check the scope.

    return true;
  }
}
