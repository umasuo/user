package com.umasuo.user.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.infrastructure.repository.UserInfoRepository;
import com.umasuo.user.infrastructure.util.PasswordUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * Created by umasuo on 17/3/8.
 */
@Service
public class DeveloperUserService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(DeveloperUserService.class);

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
    logger.debug("CreateDeveloperUser: userInfo: {}", user);
    Assert.notNull(user);
    Assert.notNull(user.getPUid());
    Assert.notNull(user.getDeveloperId());
    Assert.notNull(user.getPassword());

    Example<DeveloperUser> example = Example.of(user);
    DeveloperUser userInDb = repository.findOne(example);
    if (userInDb != null) {
      throw new AlreadyExistException("The user already exit.");
    }

    String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
    user.setPassword(hashedPassword);
    userInDb = repository.save(user);
    //TODO maybe we should set an numerical id?
    logger.debug("CreateDeveloperUser: userInDb: {}", userInDb);
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
    logger.debug("Enter. userId: {}, developerId: {}", platformUserId, developerId);

    DeveloperUser user = new DeveloperUser();
    user.setPUid(platformUserId);
    user.setDeveloperId(developerId);
    Example<DeveloperUser> example = Example.of(user);
    DeveloperUser userInDb = repository.findOne(example);

    logger.debug("Exit. user: {}.", userInDb);
    return userInDb;
  }

  /**
   * Gets user by id.
   *
   * @param userId the user id
   * @return the user by id
   */
  public DeveloperUser getUserById(String userId) {
    logger.debug("Enter. userId: {}.", userId);
    Assert.notNull(userId, "User id can not be null");

    DeveloperUser user = repository.findOne(userId);

    logger.debug("Exit.");
    return user;
  }

  /**
   * Gets all report.
   *
   * @return the all report
   */
  public List<HashMap> getAllReport() {
    logger.debug("Enter.");

    List<HashMap> result = repository.getReport();

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }


  /**
   * Gets registered report.
   *
   * @param startTime the start time
   * @param endTime   the end time
   * @return the registered report
   */
  public List<HashMap> getRegisteredReport(long startTime, long endTime) {
    logger.debug("Enter.");

    List<HashMap> result = repository.getRegisterReport(startTime, endTime);

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * Gets developer all report.
   *
   * @param developerId the developer id
   * @return the developer all report
   */
  public HashMap getDeveloperAllReport(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    HashMap result = repository.getDeveloperReport(developerId);

    logger.debug("Exit. result: {}.", result);

    return result;
  }


  /**
   * Gets developer registered report.
   *
   * @param developerId the developer id
   * @param startTime   the start time
   * @return the developer registered report
   */
  public HashMap getDeveloperRegisteredReport(String developerId, long startTime) {
    logger.debug("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    HashMap result = repository.getDeveloperRegisterReport(developerId, startTime);

    logger.debug("Exit. result: {}.", result);

    return result;
  }

}
