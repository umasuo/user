package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.QuickSignIn;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.service.SignInService;
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
public class SignInController {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignInController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient SignInService signInService;

  /**
   * user sign up.
   *
   * @param signIn the sign in
   * @return sign in result
   */
  @PostMapping(value = Router.USER_SIGN_IN)
  public SignInResult quickSignIn(@RequestBody @Valid QuickSignIn signIn) {
    logger.info("UserSignIn: quickSignIn: {}", signIn);

    SignInResult signInResult = signInService.quickSignIn(signIn);

    logger.info("UserSignIn: signInResult: {}", signInResult);
    return signInResult;
  }
}
