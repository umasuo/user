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

  /**
   * message broker's host.
   */
  @Value("${message-broker.host:127.0.0.1}")
  public String msgBrokerHost;

  /**
   * message broker's port
   */
  @Value("${message-broker.port:1883}")
  public long msgBrokerPort;

  /**
   * super user's username for message broker.
   */
  @Value("${message-broker.username:umasuo}")
  public String username;

  /**
   * super user's password for message broker.
   */
  @Value("${message-broker.password:password}")
  public String password;
}
