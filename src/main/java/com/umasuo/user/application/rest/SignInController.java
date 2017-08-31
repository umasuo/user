package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.QuickSignIn;
import com.umasuo.user.application.dto.SignIn;
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
 * Sign in controller.
 */
@RestController
public class SignInController {

  /**
   * LOGGER.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

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
    LOGGER.info("Enter. quickSignIn: {}", signIn);

    SignInResult signInResult = signInService.quickSignIn(signIn);

    LOGGER.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }


  /**
   * user sign in with phone and password.
   *
   * @param signIn the sign in
   * @return sign in result
   */
  @PostMapping(value = Router.USER_SIGN_IN_PWD)
  public SignInResult signin(@RequestBody @Valid SignIn signIn) {
    LOGGER.info("Enter. quickSignIn: {}", signIn);

    SignInResult signInResult = signInService.signIn(signIn);

    LOGGER.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }
}
