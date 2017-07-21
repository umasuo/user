package com.umasuo.user.application.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.user.infrastructure.util.RedisUtils;
import com.umasuo.user.infrastructure.util.ValidateCodeGenerator;
import com.yunpian.sdk.YunpianException;

import org.apache.commons.lang3.StringUtils;
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

    String code = ValidateCodeGenerator.generate();

    String key = String.format(RedisUtils.PHONE_KEY_FORMAT, phoneNumber);

    validateExistPhone(key);

    String codeKey = String.format(RedisUtils.PHONE_CODE_KEY_FORMAT, phoneNumber, code);

    smsService.sendValidationCode(code, phoneNumber);

    redisTemplate.opsForValue().set(codeKey, code, EXPIRE_TIME, TimeUnit.SECONDS);
    redisTemplate.opsForValue().set(key, "", 60, TimeUnit.SECONDS);

    logger.debug("Exit.");
  }


  /**
   * 检查code是否正确.
   *
   * @param phone
   * @param smsCode
   * @return
   */
  public void validateCode(String phone, String smsCode) {

    String key = String.format(RedisUtils.PHONE_CODE_KEY_FORMAT, phone, smsCode);
    String cachedCode = (String) redisTemplate.opsForValue().get(key);
    if (StringUtils.isBlank(cachedCode)) {
      logger.debug("Can not find validation code by phone: {}.", phone);
      throw new NotExistException("Validation code not exist.");
    }

    if (!cachedCode.equals(smsCode)) {
      logger.debug("Validation code not match. request code: {}, basic code: {}.",
          smsCode, cachedCode);
      throw new ParametersException("Validation code not match");
    }

    redisTemplate.delete(key);
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
