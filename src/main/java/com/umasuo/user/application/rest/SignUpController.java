package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.SignIn;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.service.SignUpService;
import com.umasuo.user.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by umasuo on 17/3/9.
 */
@RestController
public class SignUpController {


  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignUpController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient SignUpService signUpService;

  /**
   * user sign up.
   *
   * @param signUp
   * @return
   */
  @PostMapping(value = Router.USER_SIGN_UP)
  public SignInResult signUp(@RequestBody @Valid SignIn signUp) {
    logger.info("UserSignUp: signUp: {}", signUp);

    SignInResult signInResult = signUpService.signUp(signUp);

    logger.info("UserSignUp: signInResult: {}", signInResult);
    return signInResult;
  }

}
