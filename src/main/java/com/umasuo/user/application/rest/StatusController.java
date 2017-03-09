package com.umasuo.user.application.rest;

import com.umasuo.user.application.service.StatusService;
import com.umasuo.user.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by umasuo on 17/3/9.
 */
@RestController
public class StatusController {


  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(StatusController.class);

  /**
   * sign up service
   */
  @Autowired
  private transient StatusService statusService;

  /**
   * user sign up.
   *
   * @param id
   * @return
   */
  @GetMapping(value = Router.USER_SIGN_IN_STATUS)
  public boolean signInStatus(@PathVariable String id) {
    logger.info("UserSignInStatus: id: {}", id);

    boolean status = statusService.checkSignInStatus(id);

    logger.info("UserSignInStatus: status: {}", status);
    return status;
  }
}
