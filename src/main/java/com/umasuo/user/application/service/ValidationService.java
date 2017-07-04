package com.umasuo.user.application.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.user.infrastructure.util.ValidateCodeGenerator;
import com.yunpian.sdk.YunpianException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
  private static final Logger LOG = LoggerFactory.getLogger(ValidationService.class);

  /**
   * Validation code expire time;
   */
  private static final long EXPIRE_TIME = 60L;

  /**
   * redis ops.
   */
  @Autowired
  private transient StringRedisTemplate redisTemplate;

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
    LOG.debug("Enter. phoneNumber: {}.", phoneNumber);

    String validationCode = ValidateCodeGenerator.generate();

    validateExistPhone(phoneNumber);

    smsService.sendValidationCode(validationCode, phoneNumber);

    redisTemplate.opsForValue().set(phoneNumber, validationCode, EXPIRE_TIME, TimeUnit.SECONDS);

    LOG.debug("Exit.");
  }

  /**
   * Validate if this code exist.
   *
   * @param phoneNumber the phone number
   */
  private void validateExistPhone(String phoneNumber) {
    String existValidationCode = (String) redisTemplate.opsForValue().get(phoneNumber);
    if (existValidationCode != null) {
      LOG.debug("This phone has an exist validation code: {}.", existValidationCode);
      throw new AlreadyExistException("ValidationCode already exist");
    }
  }
}
