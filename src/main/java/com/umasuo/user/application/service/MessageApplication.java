package com.umasuo.user.application.service;

import com.umasuo.user.infrastructure.config.AppConfig;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Message application.
 */
@Service
public class MessageApplication implements CommandLineRunner {
  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(MessageApplication.class);

  /**
   * Mqtt user prefix.
   */
  private static final String USERNAME_PREFIX = "mqtt_user:";

  /**
   * Mqtt connection.
   */
  private transient BlockingConnection connection;

  /**
   * Redis client.
   */
  private transient StringRedisTemplate redisTemplate;

  /**
   * User message handler.s
   */
  private transient UserMessageHandler userMessageHandler;

  /**
   * 初始化和message broker的连接.
   */
  @Autowired
  public MessageApplication(StringRedisTemplate redisTemplate,
                            UserMessageHandler deviceMessageHandler,
                            AppConfig appConfig) {
    this.redisTemplate = redisTemplate;
    this.userMessageHandler = deviceMessageHandler;
    redisTemplate.boundHashOps(USERNAME_PREFIX + appConfig.getUsername()).put("password",
      appConfig.getPassword());

    MQTT mqtt = new MQTT();
    mqtt.setUserName(appConfig.getUsername());
    mqtt.setPassword(appConfig.getPassword());

    try {
      mqtt.setHost(appConfig.getMsgBrokerHost(), appConfig.getMsgBrokerPort());

      connection = mqtt.blockingConnection();
      connection.connect();
      LOGGER.info("Connect to message broker: " + appConfig.getMsgBrokerHost());
    } catch (Exception e) {
      LOGGER.error("Connect message broker failed.", e);
    }
  }

  /**
   * 添加broker user.
   */
  public void addMqttUser(String userId, String token) {
    LOGGER.debug("Add broker user: {}.", userId);
    BoundHashOperations setOperations = redisTemplate.boundHashOps(USERNAME_PREFIX + userId);
    //TODO MQTT 的的密码需要采用加密模式
    //TODO 这里其实需要考虑redis失效的场景
    setOperations.put("password", token);//为用户的APP添加用户名密码
  }

  /**
   * Delete mqtt user.
   *
   * @param userId
   */
  public void deleteMqttUser(String userId) {
    LOGGER.debug("Delete broker userId: {}.", userId);
    redisTemplate.delete(USERNAME_PREFIX + userId);

  }

  /**
   * Service 启动时自动接受
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {
    LOGGER.info("start process data.");
    while (true) {
      Message message = connection.receive();
      if (message != null) {
        String topic = message.getTopic();//从这里可以获得UserId，
        boolean handlerResult = userMessageHandler.handler(topic, message.getPayload());
        if (handlerResult) {
          message.ack();
        }
      }
    }
  }
}
