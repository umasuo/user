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
 * Platform user service.
 */
@Service
public class PlatformUserService {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeveloperUserService.class);

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
    LOGGER.debug("CreateUser: user: {}", user);

    Example<PlatformUser> userExample = Example.of(user);
    PlatformUser userInDb = repository.findOne(userExample);

    if (userInDb == null) {
      userInDb = repository.save(user);
    }
    LOGGER.debug("CreateUser: userInDb: {}", userInDb);
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
    LOGGER.debug("CreateUser: phone: {}", phone);
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
    LOGGER.debug("CreateUser: email: {}", email);
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
    LOGGER.debug("Enter. phone: {}", phone);

    PlatformUser user = this.repository.findOneByPhone(phone);

    LOGGER.debug("Exit. user: {}.", user);
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
    LOGGER.debug("GetUser: email: {}", email);

    PlatformUser user = this.repository.findOneByEmail(email);
    if (user == null) {
      throw new NotExistException("user not exist.");
    }
    return user;
  }

  /**
   * Get platform user with id list.
   *
   * @param platformUserIds
   * @return
   */
  public List<PlatformUser> getWithIds(List<String> platformUserIds) {
    LOGGER.debug("Enter. ids: {}.", platformUserIds);

    List<PlatformUser> users = repository.findAll(platformUserIds);

    LOGGER.debug("Exit. user size: {}.", users.size());
    return users;
  }

  /**
   * Get platform with id.
   *
   * @param userPlatformId
   * @return
   */
  public PlatformUser getWithId(String userPlatformId) {
    LOGGER.debug("Enter. userPlatformId: {}.", userPlatformId);

    PlatformUser user = repository.findOne(userPlatformId);
    if (user == null) {
      throw new NotExistException("Platform User not exist for id: " + userPlatformId);
    }

    LOGGER.debug("Exit. user: {}.", user);
    return user;
  }

  /**
   * save.
   *
   * @param user
   * @return
   */
  public PlatformUser save(PlatformUser user) {
    LOGGER.debug("Enter.");

    PlatformUser pUser = repository.save(user);

    LOGGER.debug("Exit.");
    return pUser;
  }

  /**
   * Count users.
   *
   * @return
   */
  public Long countUsers() {
    LOGGER.debug("Enter.");

    Long count = repository.count();

    LOGGER.debug("Exit. platform user count: {}.", count);

    return count;
  }
}
