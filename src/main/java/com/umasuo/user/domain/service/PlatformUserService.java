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

import java.util.List;

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
    Assert.notNull(phone, "Phone can not be null");
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
    Assert.notNull(email, "Email can not be null");
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
    Assert.notNull(phone, "Phone can not be null");
    logger.debug("Enter. phone: {}", phone);

    PlatformUser user = this.repository.findOneByPhone(phone);

    logger.debug("Exit. user: {}.", user);
    return user;
  }

  /**
   * get an user with phone number.
   *
   * @param email
   * @return
   */
  public PlatformUser getWithEmail(String email) {
    Assert.notNull(email, "Email can not be null");
    logger.debug("GetUser: email: {}", email);

    PlatformUser user = this.repository.findOneByEmail(email);
    if (user == null) {
      throw new NotExistException("user not exist.");
    }
    return user;
  }

  public List<PlatformUser> getWithIds(List<String> platformUserIds) {
    logger.debug("Enter. ids: {}.", platformUserIds);

    List<PlatformUser> users = repository.findAll(platformUserIds);

    logger.debug("Exit. user size: {}.", users.size());
    return users;
  }

  public PlatformUser getWithId(String userPlatformId) {
    logger.debug("Enter. userPlatformId: {}.", userPlatformId);

    PlatformUser user = repository.findOne(userPlatformId);
    if (user == null) {
      throw new NotExistException("Platform User not exist for id: " + userPlatformId);
    }

    logger.debug("Exit. user: {}.", user);
    return user;
  }

  /**
   * save.
   *
   * @param user
   * @return
   */
  public PlatformUser save(PlatformUser user) {
    logger.debug("Enter.");

    PlatformUser pUser = repository.save(user);

    logger.debug("Exit.");
    return pUser;
  }
}
