package com.umasuo.user.application.service;

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
 * Sign in status service.
 */
@Service
public class StatusService {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(StatusService.class);

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Message application.
   */
  @Autowired
  private transient MessageApplication messageApplication;

  /**
   * check login status. for auth check.
   * this can only be accessed in internal net work.
   *
   * @param userId      用户ID
   * @param developerId 开发者ID
   * @param token       token
   * @return LoginStatus
   */
  public LoginStatus checkSignInStatus(String userId, String developerId, String token) {
    LOGGER.debug("CheckSignInStatus: id: {}", userId);

    LoginStatus loginStatus = new LoginStatus(developerId, userId, false);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT, developerId, userId);

    UserSession userSession = (UserSession) redisTemplate.opsForHash().get(userKey,
      RedisUtils.USER_SESSION_KEY);

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
   * @param session  session
   * @param tokenStr tokenStr
   * @return boolean
   */
  private boolean verifyToken(UserSession session, String tokenStr) {
    if (tokenStr == null) {
      return false;
    }

    if (session == null) {
      return false;
    }

    //检查当前session 是否合法
    String token = session.getToken();
    if (!tokenStr.equals(token)) {
      return false;
    }

    long lifeTime = session.getLastActiveTime() + UserSession.EXPIRE_IN;
    long curTime = System.currentTimeMillis();
    if (curTime > lifeTime) {
      // TODO: 17/6/19 这里是否需要把session删除了
      return false;
    }

    session.setLastActiveTime(curTime);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT,
      session.getUserView().getDeveloperId(), session.getUserView().getUserId());

    redisTemplate.boundHashOps(userKey).put(RedisUtils.USER_SESSION_KEY, session);

    redisTemplate.expire(userKey, 30, TimeUnit.DAYS);//30天后过期，此时间在登录的时候也有设置
    messageApplication.addMqttUser(session.getUserView().getUserId(), token);

    return true;
  }
}
