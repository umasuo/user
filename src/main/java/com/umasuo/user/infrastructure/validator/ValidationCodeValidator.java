package com.umasuo.user.infrastructure.validator;

import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Validation code validator.
 */
public final class ValidationCodeValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ValidationCodeValidator.class);

  /**
   * Instantiates a new Validation code validator.
   */
  private ValidationCodeValidator() {
  }


  /**
   * Validate.
   *
   * @param basicValidationCode the basic validation code
   * @param validationCode the validation code
   * @param phoneNumber the phone number
   */
  public static void validate(String basicValidationCode,
      String validationCode, String phoneNumber) {
    if (StringUtils.isBlank(basicValidationCode)) {
      LOG.debug("Can not find validation code by phone: {}.", phoneNumber);
      throw new NotExistException("Validation code not exist.");
    }

    if (!basicValidationCode.equals(validationCode)) {
      LOG.debug("Validation code not match. request code: {}, basic code: {}.",
          validationCode, basicValidationCode);
      throw new ParametersException("Validation code not match");
    }
  }
}
