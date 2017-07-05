package com.umasuo.user.application.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.user.infrastructure.util.RedisUtils;
import com.umasuo.user.infrastructure.util.ValidateCodeGenerator;
import com.yunpian.sdk.YunpianException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * The type Sms service.
 */
@Service
public class ValidationService {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(ValidationService.class);

  /**
   * Validation code expire time;
   */
  private static final long EXPIRE_TIME = 10 * 60L;

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * The SmsService.
   */
  @Autowired
  private transient SmsService smsService;

  /**
   * Send validation code.
   *
   * @param phoneNumber the phone number
   */
  public void sendValidationCode(String phoneNumber) throws YunpianException {
    logger.debug("Enter. phoneNumber: {}.", phoneNumber);

    String validationCode = ValidateCodeGenerator.generate();

    String key = String.format(RedisUtils.PHONE_VERIFY_KEY_FORMAT, phoneNumber);

    validateExistPhone(key);

    smsService.sendValidationCode(validationCode, phoneNumber);

    redisTemplate.opsForValue().set(key, validationCode, EXPIRE_TIME, TimeUnit.SECONDS);

    logger.debug("Exit.");
  }

  /**
   * Validate if this code exist.
   *
   * @param key the phone key in redis
   */
  private void validateExistPhone(String key) {
    String existValidationCode = (String) redisTemplate.opsForValue().get(key);
    if (existValidationCode != null) {
      logger.debug("This phone has an exist validation code: {}.", existValidationCode);
      throw new AlreadyExistException("ValidationCode already exist");
    }
  }
}
