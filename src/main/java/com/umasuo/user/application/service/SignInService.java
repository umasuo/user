package com.umasuo.user.application.service;

import com.umasuo.exception.PasswordErrorException;
import com.umasuo.user.application.dto.SignIn;
import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.application.dto.mapper.UserViewMapper;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.domain.service.PlatformUserService;
import com.umasuo.user.infrastructure.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by umasuo on 17/3/9.
 */
@Service
public class SignInService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignInService.class);

  /**
   * platform user service.
   */
  @Autowired
  private transient PlatformUserService platformUserService;

  /**
   * developer user service.
   */
  @Autowired
  private transient DeveloperUserService developerUserService;

  /**
   * sign in.
   *
   * @param signIn
   * @return
   */
  public UserView signIn(SignIn signIn) {
    logger.debug("SignUp: {}", signIn);
    Assert.notNull(signIn);

    PlatformUser pUser = platformUserService.getWithPhone(signIn.getPhone());
    DeveloperUser dUser = developerUserService.getUserInfo(pUser.getId(), signIn.getDeveloperId());

    boolean isPwdRight = PasswordUtil.checkPassword(signIn.getPassword(), dUser.getPassword());
    if (!isPwdRight) {
      throw new PasswordErrorException("Password or phone is not correct.");
    }
    
    return UserViewMapper.toUserView(pUser, dUser);
  }
}
