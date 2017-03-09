package com.umasuo.user.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.UserInfo;
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
public class UserInfoService {

  /**
   * logger.
   */
  private final static Logger logger = LoggerFactory.getLogger(UserInfoService.class);

  /**
   * user info repository.
   */
  @Autowired
  private transient UserInfoRepository repository;

  /**
   * create user info.
   *
   * @param userInfo
   */
  public UserInfo createUserInfo(UserInfo userInfo) {
    logger.debug("CreateUserInfo: userInfo: {}", userInfo);
    Assert.notNull(userInfo);
    Assert.notNull(userInfo.getUid());
    Assert.notNull(userInfo.getDeveloperId());
    Assert.notNull(userInfo.getPassword());

    Example<UserInfo> example = Example.of(userInfo);
    UserInfo userInDb = repository.findOne(example);
    if (userInDb != null) {
      throw new AlreadyExistException("The user already exit.");
    }

    String hashedPassword = PasswordUtil.hashPassword(userInfo.getPassword());
    userInfo.setPassword(hashedPassword);
    userInDb = repository.save(userInfo);

    logger.debug("CreateUserInfo: userInDb: {}", userInDb);
    return userInDb;
  }

  /**
   * get user info with user id, developer id.
   *
   * @param userId
   * @param developerId
   * @return
   */
  public UserInfo getUserInfo(String userId, String developerId) {
    logger.debug("GetUserInfo: userId: {}, developerId: {}", userId, developerId);
    Assert.notNull(userId);
    Assert.notNull(developerId);

    UserInfo userInfo = new UserInfo();
    userInfo.setUid(userId);
    userInfo.setDeveloperId(developerId);
    Example<UserInfo> example = Example.of(userInfo);
    UserInfo userInDb = repository.findOne(example);
    if (userInDb == null) {
      throw new NotExistException("User not exist.");
    }
    return userInDb;
  }
}
