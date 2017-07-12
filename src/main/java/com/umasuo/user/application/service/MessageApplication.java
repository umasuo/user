package com.umasuo.user.application.service;

import com.umasuo.user.infrastructure.config.AppConfig;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
@Service
public class MessageApplication implements CommandLineRunner {
  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(MessageApplication.class);

  private transient AppConfig appConfig;

  private transient MQTT mqtt;

  private transient BlockingConnection connection;

  private static final String USERNAME_PREFIX = "mqtt_user:";
  private static final String DEVICE_TOPIC_SUB_PREFIX = "device/sub/";
  private static final String DEVICE_TOPIC_PUB_PREFIX = "device/pub/";

  private List<Topic> topics = new ArrayList<>();
  /**
   * redis ops.
   */
  private transient StringRedisTemplate redisTemplate;

  private transient UserMessageHandler deviceMessageHandler;


  /**
   * 初始化和message broker的连接.
   *
   * @param appConfig 系统配置
   */
  @Autowired
  public MessageApplication(StringRedisTemplate redisTemplate,
                            UserMessageHandler deviceMessageHandler,
                            AppConfig appConfig) {
    this.appConfig = appConfig;
    this.redisTemplate = redisTemplate;
    this.deviceMessageHandler = deviceMessageHandler;
    redisTemplate.boundHashOps(USERNAME_PREFIX + appConfig.getUsername()).put("password",
        appConfig.getPassword());

    mqtt = new MQTT();
    mqtt.setUserName(appConfig.getUsername());
    mqtt.setPassword(appConfig.getPassword());

    try {
      mqtt.setHost(appConfig.getMsgBrokerHost(),appConfig.getMsgBrokerPort());

      connection = mqtt.blockingConnection();
      connection.connect();
      logger.info("Connect to message broker: " + appConfig.getMsgBrokerHost());
    } catch (Exception e) {
      logger.error("Connect message broker failed.", e);
    }
  }

  /**
   * 发布消息.
   *
   * @param topic   topic，如果是设备的topic，则为topicID
   * @param payload 内容
   * @param qos     消息发送等级（0，1，2）
   * @param retain  是否保持在broker上
   */
  public void publish(final String topic, final byte[] payload, final QoS qos, final boolean
      retain) {
    logger.debug("Enter. topic: {}, payload: {}, qos: {}, retain: {}.", topic, new String
        (payload), qos, retain);
    try {
      connection.publish(topic, payload, qos, retain);
    } catch (Exception e) {
      logger.error("publish message failed.", e);
    }
  }

  /**
   * Service 启动时自动接受
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {
    logger.info("start process data.");
    while (true) {
      Message message = connection.receive();
      if (message != null) {
        String topic = message.getTopic();//从这里可以获得UserId，
        String deviceId = topic.substring(DEVICE_TOPIC_PUB_PREFIX.length() - 1);
        String payload = new String(message.getPayload());//从这里可以获取device上发的命令和数据

        boolean handlerResult = deviceMessageHandler.handler(deviceId, payload);
        if (handlerResult) {
          message.ack();
        }
      }
    }
  }
}
