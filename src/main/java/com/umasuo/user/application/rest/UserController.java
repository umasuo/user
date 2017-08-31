package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.ResetPassword;
import com.umasuo.user.application.dto.SignInResult;
import com.umasuo.user.application.dto.UserOperationData;
import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.application.service.UserApplication;
import com.umasuo.user.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * User controller.
 */
@CrossOrigin
@RestController
public class UserController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  /**
   * User application.
   */
  @Autowired
  private transient UserApplication userApplication;

  /**
   * Get users.
   *
   * @param developerId
   * @param userId
   * @param phone
   * @return
   */
  @GetMapping(value = Router.USER_ROOT)
  public List<UserOperationData> getUsers(@RequestHeader String developerId,
                                          @RequestParam(required = false) String userId,
                                          @RequestParam(required = false) String phone) {
    LOGGER.info("Enter. developerId: {}, userId: {}, userPhone: {}.", developerId, userId, phone);

    List<UserOperationData> result = userApplication.getUsers(developerId, userId, phone);

    LOGGER.info("Exit. user size: {}.", result.size());

    return result;
  }

  /**
   * 用户更新个人数据.
   *
   * @return
   */
  @PutMapping(Router.USER_WITH_ID)
  public UserView updateUserInfo(@PathVariable String id, @RequestBody UserView userView) {
    LOGGER.info("Enter. userId: {}, userView: {}.", id, userView);

    UserView updated = userApplication.update(id, userView);

    LOGGER.info("Exit. userView: {}.", updated);
    return updated;
  }


  /**
   * 重置密码
   *
   * @param resetPassword
   * @return
   */
  @PutMapping(Router.USER_RESET_PASSWORD)
  public SignInResult resetPassword(ResetPassword resetPassword) {
    LOGGER.info("Enter. resetPassword: {}", resetPassword);

    SignInResult signInResult = userApplication.resetPassword(resetPassword);

    LOGGER.info("Exit. signInResult: {}", signInResult);
    return signInResult;
  }

  /**
   * Count users.
   *
   * @return
   */
  @GetMapping(Router.USER_COUNT)
  public Long countUsers() {
    LOGGER.info("Enter.");

    Long result = userApplication.countUsers();

    LOGGER.info("Exit. user count: {}.", result);

    return result;
  }
}
