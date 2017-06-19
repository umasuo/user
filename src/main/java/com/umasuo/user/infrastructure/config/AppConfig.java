package com.umasuo.user.infrastructure.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by umasuo on 17/6/19.
 */
@Configuration
@Data
public class AppConfig {

  /**
   * JWT 的密钥.
   */
  @Value("${jwt.secret:QWERTYUIOPASDFGHJKLZXCVBNM}")
  private String jwtSecret;

  /**
   * token过期时间.
   */
  @Value("${jwt.expires:72000}")
  private long tokenExpiredIn;
}
