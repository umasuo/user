package com.umasuo.user.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.infrastructure.repository.UserInfoRepository;
import com.umasuo.user.infrastructure.util.PasswordUtil;
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
   * @param user
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
   * @param userId
   * @param developerId
   * @return
   */
  public DeveloperUser getUserInfo(String userId, String developerId) {
    logger.debug("GetUserInfo: userId: {}, developerId: {}", userId, developerId);
    Assert.notNull(userId, "User id can not be null");
    Assert.notNull(developerId, "Developer id can not be null");

    DeveloperUser user = new DeveloperUser();
    user.setPUid(userId);
    user.setDeveloperId(developerId);
    Example<DeveloperUser> example = Example.of(user);
    DeveloperUser userInDb = repository.findOne(example);

    return userInDb;
  }
}
