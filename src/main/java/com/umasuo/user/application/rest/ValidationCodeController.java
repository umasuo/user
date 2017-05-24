package com.umasuo.user.application.rest;

import static com.umasuo.user.infrastructure.Router.PHONE_NUMBER;
import static com.umasuo.user.infrastructure.Router.VALIDATION_CODE;

import com.umasuo.user.application.service.SmsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Validation code controller.
 */
@RestController
public class ValidationCodeController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ValidationCodeController.class);

  /**
   * The sms service.
   */
  @Autowired
  private transient SmsService smsService;

  /**
   * Request validation code.
   *
   * @param phoneNumber the phone number
   */
  @PostMapping(VALIDATION_CODE)
  public void getValidationCode(@RequestParam(PHONE_NUMBER) String phoneNumber) {
    LOG.info("Enter. phoneNumber: {}.", phoneNumber);

    smsService.sendValidationCode(phoneNumber);

    LOG.info("Exit");
  }
}
