package com.umasuo.user.application.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.user.infrastructure.util.ValidateCodeGenerator;

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
public class SmsService {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SmsService.class);

  /**
   * Validation code expire time;
   */
  private static final long EXPIRE_TIME = 60 * 1000L;

  /**
   * Redis time util.
   */
  private static final TimeUnit TIME_UTIL = TimeUnit.MILLISECONDS;

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Send validation code.
   *
   * @param phoneNumber the phone number
   */
  public void sendValidationCode(String phoneNumber) {
    LOG.debug("Enter. phoneNumber: {}.", phoneNumber);

    String validationCode = ValidateCodeGenerator.generate();

    // TODO: 17/5/24 send validation code to the phone

    validateExistCode(phoneNumber);

    redisTemplate.opsForValue().set(phoneNumber, validationCode, EXPIRE_TIME, TIME_UTIL);

    LOG.debug("Exit.");
  }

  /**
   * Validate if this code exist.
   *
   * @param phoneNumber the phone number
   */
  private void validateExistCode(String phoneNumber) {
    String existValidationCode = (String) redisTemplate.opsForValue().get(phoneNumber);
    if (existValidationCode != null) {
      LOG.debug("This phone has an exist validation code: {}.", existValidationCode);
      throw new AlreadyExistException("ValidationCode already exist");
    }
  }
}
