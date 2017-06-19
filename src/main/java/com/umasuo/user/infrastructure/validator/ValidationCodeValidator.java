package com.umasuo.user.infrastructure.validator;

import com.umasuo.exception.ParametersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Validation code validator.
 */
public final class ValidationCodeValidator {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(ValidationCodeValidator.class);

  /**
   * Instantiates a new Validation code validator.
   */
  private ValidationCodeValidator() {
  }


  /**
   * Validate.
   *
   * @param cachedCode the basic validation code
   * @param inputCode  the validation code
   */
  public static void validate(String cachedCode,
                              String inputCode) {
    if (!cachedCode.equals(inputCode)) {
      logger.debug("Validation code not match. request code: {}, basic code: {}.",
          inputCode, cachedCode);
      throw new ParametersException("Validation code not match");
    }
  }
}
