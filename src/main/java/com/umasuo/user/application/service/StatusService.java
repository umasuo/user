package com.umasuo.user.application.service;

import com.umasuo.authentication.JwtUtil;
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

}
