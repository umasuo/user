package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.UserOperationData;
import com.umasuo.user.application.service.UserApplication;
import com.umasuo.user.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/7/7.
 */
@CrossOrigin
@RestController
public class UserController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private transient UserApplication userApplication;

  @GetMapping(value = Router.USER_ROOT)
  public List<UserOperationData> getUsers(@RequestHeader String developerId,
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String phone) {
    LOG.info("Enter. developerId: {}, userId: {}, userPhone: {}.", developerId, userId, phone);

    List<UserOperationData> result = userApplication.getUsers(developerId, userId, phone);

    LOG.info("Exit. user size: {}.", result.size());

    return result;
  }
}
