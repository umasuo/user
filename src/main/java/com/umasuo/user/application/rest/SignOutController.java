package com.umasuo.user.application.rest;

import com.umasuo.user.application.service.SignOutService;
import com.umasuo.user.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/3/21.
 */
@RestController
public class SignOutController {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignOutController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient SignOutService signOutService;

  /**
   * sign out.
   *
   * @param userId
   * @param developerId
   */
  @PostMapping(value = Router.USER_SIGN_OUT)
  public void quickSignIn(@PathVariable String userId, @RequestHeader String developerId) {
    logger.info("Enter. userId: {}, developerId: {}.", userId, developerId);

    signOutService.signOut(userId, developerId);

    logger.info("Exit.");
  }

}
