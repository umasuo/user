package com.umasuo.user.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.User;
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
public class UserService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(UserInfoService.class);

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
  public User createUser(User user) {
    Assert.notNull(user);
    Assert.notNull(user.getPhone());
    logger.debug("CreateUser: user: {}", user);

    Example<User> userExample = Example.of(user);
    User userInDb = repository.findOne(userExample);

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
  public User createUserWithPhone(String phone) {
    Assert.notNull(phone);
    logger.debug("CreateUser: phone: {}", phone);
    User user = new User();
    user.setPhone(phone);
    return createUser(user);
  }

  /**
   * create user with email.
   *
   * @param email
   * @return
   */
  public User createUserWithEmail(String email) {
    Assert.notNull(email);
    logger.debug("CreateUser: email: {}", email);
    User user = new User();
    user.setEmail(email);
    return createUser(user);
  }

  /**
   * get an user with phone number.
   *
   * @param phone
   * @return
   */
  public User getWithPhone(String phone) {
    Assert.notNull(phone);
    logger.debug("GetUser: phone: {}", phone);

    User user = this.repository.findOneByPhone(phone);
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
  public User getWithEmail(String email) {
    Assert.notNull(email);
    logger.debug("GetUser: email: {}", email);

    User user = this.repository.findOneByEmail(email);
    if (user == null) {
      throw new NotExistException("user not exist.");
    }
    return user;
  }

}
