package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.LoginStatus;
import com.umasuo.user.application.service.StatusService;
import com.umasuo.user.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
   * 用户登录状态检查
   * @param id 用户ID
   * @param developerId 用户所属开发者ID
   * @param token token
   * @return LoginStatus
   */
  @GetMapping(value = Router.USER_SIGN_IN_STATUS)
  public LoginStatus signInStatus(@PathVariable @Valid @NotNull String id,
                                  @RequestHeader @Valid @NotNull String developerId,
                                  @RequestHeader @Valid @NotNull String token) {
    logger.info("Enter. id: {}.", id);

    LoginStatus status = statusService.checkSignInStatus(id, developerId, token);

    logger.info("Exit. status: {}.", status);
    return status;
  }
}
