package com.umasuo.user.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.PlatformUser;
import com.umasuo.user.infrastructure.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by umasuo on 17/3/8.
 */
@Service
public class PlatformUserService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(DeveloperUserService.class);

  /**
   * repository.
   */
  @Autowired
  private transient UserRepository repository;

  /**
   * create user with sample.
   * if the user already exist, then return the user.
   *
   * @param user
   * @return
   */
  public PlatformUser createUser(PlatformUser user) {
    Assert.notNull(user);
    Assert.notNull(user.getPhone());
    logger.debug("CreateUser: user: {}", user);

    Example<PlatformUser> userExample = Example.of(user);
    PlatformUser userInDb = repository.findOne(userExample);

    if (userInDb == null) {
      userInDb = repository.save(user);
    }
    logger.debug("CreateUser: userInDb: {}", userInDb);
    return userInDb;
  }

  /**
   * create an user with phone number.
   *
   * @param phone
   * @return
   */
  public PlatformUser createUserWithPhone(String phone) {
    Assert.notNull(phone);
    logger.debug("CreateUser: phone: {}", phone);
    PlatformUser user = new PlatformUser();
    user.setPhone(phone);
    return createUser(user);
  }

  /**
   * create user with email.
   *
   * @param email
   * @return
   */
  public PlatformUser createUserWithEmail(String email) {
    Assert.notNull(email);
    logger.debug("CreateUser: email: {}", email);
    PlatformUser user = new PlatformUser();
    user.setEmail(email);
    return createUser(user);
  }

  /**
   * get an user with phone number.
   *
   * @param phone
   * @return
   */
  public PlatformUser getWithPhone(String phone) {
    Assert.notNull(phone);
    logger.debug("GetUser: phone: {}", phone);

    PlatformUser user = this.repository.findOneByPhone(phone);
    if (user == null) {
      throw new NotExistException("user not exist.");
    }
    return user;
  }

  /**
   * get an user with phone number.
   *
   * @param email
   * @return
   */
  public PlatformUser getWithEmail(String email) {
    Assert.notNull(email);
    logger.debug("GetUser: email: {}", email);

    PlatformUser user = this.repository.findOneByEmail(email);
    if (user == null) {
      throw new NotExistException("user not exist.");
    }
    return user;
  }

}
