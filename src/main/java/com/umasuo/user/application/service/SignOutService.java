package com.umasuo.user.application.service;

import com.umasuo.user.infrastructure.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Sign out service.
 */
@Service
public class SignOutService {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignOutService.class);

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * configs.
   */
  @Autowired
  private transient MessageApplication messageApplication;

  /**
   * 用户退出登录.
   *
   * @param userId
   * @param developerId
   */
  public void signOut(String userId, String developerId) {
    LOGGER.debug("Enter. userId: {}, developerId: {}.", userId, developerId);

    String userKey = String.format(RedisUtils.USER_KEY_FORMAT, developerId, userId);
    redisTemplate.delete(userKey);

    messageApplication.deleteMqttUser(userId);

    LOGGER.debug("Exit.");
  }

}
