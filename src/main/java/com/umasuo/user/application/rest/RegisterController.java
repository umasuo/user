package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.RegisterInfo;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.service.RegisterService;
import com.umasuo.user.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by umasuo on 17/7/20.
 */
@RestController
public class RegisterController {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient RegisterService registerService;

  /**
   * user sign up and sign in.
   *
   * @param register register info
   * @return sign in result
   */
  @PostMapping(value = Router.USER_REGISTER)
  public SignInResult register(@RequestBody @Valid RegisterInfo register) {
    logger.info("Enter. register: {}", register);

    SignInResult signInResult = registerService.register(register);

    logger.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }
}
