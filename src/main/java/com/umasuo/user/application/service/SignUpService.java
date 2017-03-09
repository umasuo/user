package com.umasuo.user.application.service;

import com.umasuo.user.application.dto.SignIn;
import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.application.dto.mapper.SignInMapper;
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
 * PlatformUser sign up service.
 * TODO we should used transaction here.
 */
@Service
public class SignUpService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(SignUpService.class);

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


  public UserView signUp(SignIn signUp) {
    logger.debug("SignUp: {}", signUp);
    Assert.notNull(signUp);
    //create platform user.
    PlatformUser platformUser = SignInMapper.toPlatformUser(signUp);
    PlatformUser pUser = platformUserService.createUser(platformUser);

    //create developer user.
    DeveloperUser developerUser = SignInMapper.toDeveloperUser(signUp);
    developerUser.setPUid(pUser.getId());
    String hashedPassword = PasswordUtil.hashPassword(developerUser.getPassword());
    developerUser.setPassword(hashedPassword);
    DeveloperUser dUser = developerUserService.createUser(developerUser);

    return null;
  }
}
