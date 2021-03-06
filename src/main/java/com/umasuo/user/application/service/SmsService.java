package com.umasuo.user.application.service;

import com.umasuo.user.infrastructure.util.SmsUrlUtils;
import com.yunpian.sdk.YunpianException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.yunpian.sdk.util.HttpUtil.post;

/**
 * Service for Sms.
 */
@Getter
@Setter
@Service
public class SmsService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

  /**
   * Api key for yunpian.
   */
  @Value("${sms.yunpian.key}")
  private String yunpianApiKey;

  /**
   * Send Validation code.
   *
   * @param validationCode the validation code
   * @param phoneNumber    the phone number
   */
  @Async
  public String sendValidationCode(String validationCode, String phoneNumber)
    throws YunpianException {
    Map<String, String> params = new ConcurrentHashMap<>();

    params.put("apikey", yunpianApiKey);
    params.put("text", createSmsText(validationCode));
    params.put("mobile", phoneNumber);

    return post(SmsUrlUtils.URI_SEND_SMS, params);
  }

  /**
   * Create Sms text.
   *
   * @param validationCode validation code.
   * @return string
   */
  private String createSmsText(String validationCode) {
    return String.format("【伊娃App】您的验证码是%s。如非本人操作，请忽略本短信", validationCode);
  }
}
