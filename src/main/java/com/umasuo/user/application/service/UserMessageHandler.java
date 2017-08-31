package com.umasuo.user.application.service;

import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/30.
 * Device message handler, used to handler messages that device send to cloud.
 */
@Service
public class UserMessageHandler {

  /**
   * 这里处理与数据相关的消息。例如上传数据等.
   *
   * @param deviceId deviceId
   * @param payload  the real content
   * @return handler result
   */
  public boolean handler(String deviceId, byte[] payload) {

    // TODO: 17/7/14 定义用户端能够处理的消息
    // app 与云端的所有通采用API的方式，APP与设备的通信需要采用MQTT，服务端不处理MQTT中的消息，除非下发通知之类的下发类操作.

    //1 通过deviceId 获取其public key
    //2 通过public key解密content
    //3 根据格式解析content
    //4 执行对应的服务
    //5 正确处理之后，返回true，否则返回false，以供再次处理消息

    return false;
  }
}
