package com.umasuo.user.application.service;

import com.umasuo.user.infrastructure.util.SmsUrlUtils;
import com.yunpian.sdk.YunpianException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.yunpian.sdk.util.HttpUtil.post;

/**
 * Service for Sms.
 * Created by Davis on 17/5/26.
 */
@Getter
@Setter
@Service
public class SmsService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

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
  public String sendValidationCode(String validationCode, String phoneNumber)
      throws YunpianException {
    Map<String, String> params = new HashMap<String, String>();

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
    return validationCode;
  }
}
