package com.umasuo.user.domain.service;

import com.google.common.collect.Lists;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.user.application.dto.UserView;
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
    logger.debug("Enter. userInfo: {}", user);
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
    logger.debug("Exit. userInDb: {}", userInDb);
    return userInDb;
  }

  /**
   * get user info with user id, developer id.
   *
   * @param platformUserId the platform user id
   * @param developerId the developer id
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
  public List<HashMap> getTotalCountReport(long endTime) {
    logger.debug("Enter.");

    List<HashMap> result = repository.getTotalCountReport(endTime);

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }


  /**
   * Gets registered report.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @return the registered report
   */
  public List<HashMap> getIncreaseReport(long startTime, long endTime) {
    logger.debug("Enter.");

    List<HashMap> result = repository.getIncreaseReport(startTime, endTime);

    logger.debug("Exit. result size: {}.", result.size());

    return result;
  }

  /**
   * Gets developer all report.
   *
   * @param developerId the developer id
   * @return the developer all report
   */
  public HashMap getTotalCountReport(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    HashMap result = repository.getTotalCountReport(developerId);

    logger.debug("Exit. result: {}.", result);

    return result;
  }


  /**
   * Gets developer registered report.
   *
   * @param developerId the developer id
   * @param startTime the start time
   * @return the developer registered report
   */
  public HashMap getIncreaseReport(String developerId, long startTime, long endTime) {
    logger.debug("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    HashMap result = repository.getIncreaseReport(developerId, startTime, endTime);

    logger.debug("Exit. result: {}.", result);

    return result;
  }

  public List<DeveloperUser> getUsers(String developerId, String userId) {
    logger.info("Enter. developerId: {}, developerUserId: {}.", developerId, userId);

    DeveloperUser sample = new DeveloperUser();
    sample.setDeveloperId(developerId);
    if (StringUtils.isNotBlank(userId)) {
      sample.setId(userId);
    }

    Example<DeveloperUser> example = Example.of(sample);

    List<DeveloperUser> users = repository.findAll(example);

    logger.info("Exit. user size: {}.", users.size());

    return users;
  }
}
