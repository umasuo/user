package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.UserOperationData;
import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.application.service.UserApplication;
import com.umasuo.user.infrastructure.Router;
import com.umasuo.user.infrastructure.update.UpdateRequest;
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
 * Created by Davis on 17/7/7.
 */
@CrossOrigin
@RestController
public class UserController {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private transient UserApplication userApplication;

  @GetMapping(value = Router.USER_ROOT)
  public List<UserOperationData> getUsers(@RequestHeader String developerId,
                                          @RequestParam(required = false) String userId,
                                          @RequestParam(required = false) String phone) {
    logger.info("Enter. developerId: {}, userId: {}, userPhone: {}.", developerId, userId, phone);

    List<UserOperationData> result = userApplication.getUsers(developerId, userId, phone);

    logger.info("Exit. user size: {}.", result.size());

    return result;
  }

  /**
   * 用户更新个人数据.
   *
   * @return
   */
  @PutMapping(Router.USER_WITH_ID)
  public UserView updateUserInfo(@PathVariable String id, @RequestBody UserView userView) {
    logger.info("Enter. userId: {}, userView: {}.", id, userView);
    
    UserView updated = userApplication.update(id, userView);

    return updated;
  }
}
