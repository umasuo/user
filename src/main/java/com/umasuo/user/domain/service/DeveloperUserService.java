package com.umasuo.user.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.infrastructure.repository.UserInfoRepository;
import com.umasuo.user.infrastructure.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * Developer user service.
 */
@Service
public class DeveloperUserService {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeveloperUserService.class);

  /**
   * user info repository.
   */
  @Autowired
  private transient UserInfoRepository repository;

  /**
   * create user info.
   *
   * @param user the user
   * @return the developer user
   */
  public DeveloperUser createUser(DeveloperUser user) {
    LOGGER.debug("Enter. userInfo: {}", user);
    Assert.notNull(user);
    Assert.notNull(user.getPUid());
    Assert.notNull(user.getDeveloperId());

    Example<DeveloperUser> example = Example.of(user);
    DeveloperUser userInDb = repository.findOne(example);
    if (userInDb != null) {
      throw new AlreadyExistException("The user already exit.");
    }

    if (user.getPassword() != null) {
      String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
      user.setPassword(hashedPassword);
    }
    userInDb = repository.save(user);
    //TODO maybe we should set an numerical id?
    LOGGER.debug("Exit. userInDb: {}", userInDb);
    return userInDb;
  }

  /**
   * get user info with user id, developer id.
   *
   * @param platformUserId the platform user id
   * @param developerId    the developer id
   * @return user by platform
   */
  public DeveloperUser getUserByPlatform(String platformUserId, String developerId) {
    LOGGER.debug("Enter. userId: {}, developerId: {}", platformUserId, developerId);

    DeveloperUser user = new DeveloperUser();
    user.setPUid(platformUserId);
    user.setDeveloperId(developerId);
    Example<DeveloperUser> example = Example.of(user);
    DeveloperUser userInDb = repository.findOne(example);

    LOGGER.debug("Exit. user: {}.", userInDb);
    return userInDb;
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  public DeveloperUser getUserById(String userId) {
    LOGGER.debug("Enter. userId: {}.", userId);
    Assert.notNull(userId, "User id can not be null");

    DeveloperUser user = repository.findOne(userId);
    if (user == null) {
      LOGGER.debug("Can not find user: {}.", userId);
      throw new NotExistException("User not exist");
    }

    LOGGER.debug("Exit.");
    return user;
  }

  /**
   * Gets all report.
   *
   * @return the all report
   */
  public List<Map> getTotalCountReport(long endTime) {
    LOGGER.debug("Enter.");

    List<Map> result = repository.getTotalCountReport(endTime);

    LOGGER.debug("Exit. result size: {}.", result.size());

    return result;
  }


  /**
   * Gets registered report.
   *
   * @param startTime the start time
   * @param endTime   the end time
   * @return the registered report
   */
  public List<Map> getIncreaseReport(long startTime, long endTime) {
    LOGGER.debug("Enter.");

    List<Map> result = repository.getIncreaseReport(startTime, endTime);

    LOGGER.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * Gets developer all report.
   *
   * @param developerId the developer id
   * @return the developer all report
   */
  public Map getTotalCountReport(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    Map result = repository.getTotalCountReport(developerId);

    LOGGER.debug("Exit. result: {}.", result);

    return result;
  }


  /**
   * Gets developer registered report.
   *
   * @param developerId the developer id
   * @param startTime   the start time
   * @return the developer registered report
   */
  public Map getIncreaseReport(String developerId, long startTime, long endTime) {
    LOGGER.debug("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    Map result = repository.getIncreaseReport(developerId, startTime, endTime);

    LOGGER.debug("Exit. result: {}.", result);

    return result;
  }

  /**
   * Get users.
   * @param developerId
   * @param userId
   * @return
   */
  public List<DeveloperUser> getUsers(String developerId, String userId) {
    LOGGER.info("Enter. developerId: {}, developerUserId: {}.", developerId, userId);

    DeveloperUser sample = new DeveloperUser();
    sample.setDeveloperId(developerId);
    if (StringUtils.isNotBlank(userId)) {
      sample.setId(userId);
    }

    Example<DeveloperUser> example = Example.of(sample);

    List<DeveloperUser> users = repository.findAll(example);

    LOGGER.info("Exit. user size: {}.", users.size());

    return users;
  }

  /**
   * Save user.
   * @param user
   * @return
   */
  public DeveloperUser save(DeveloperUser user) {
    LOGGER.debug("Enter.");

    DeveloperUser dUser = repository.save(user);

    LOGGER.debug("Exit.");
    return dUser;
  }
}
