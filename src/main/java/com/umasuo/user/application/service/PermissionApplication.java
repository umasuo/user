package com.umasuo.user.application.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.application.dto.PermissionRequest;
import com.umasuo.user.application.dto.mapper.ResourceRequestMapper;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.ResourceRequest;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.domain.service.ResourceRequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Davis on 17/6/9.
 */
@Service
public class PermissionApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PermissionApplication.class);

  /**
   * The Developer user service.
   */
  @Autowired
  private DeveloperUserService developerUserService;

  /**
   * The Request service.
   */
  @Autowired
  private ResourceRequestService requestService;


  /**
   * Handle request.
   *
   * @param request the request
   */
  public void handleRequest(PermissionRequest request) {

    // 1. developer user -> 获取platform user -> 获取developer user，校验user是否存在
    DeveloperUser developerUser =
        developerUserService.getUserById(request.getUserId());
    if (developerUser == null) {
      LOG.debug("Can not find user: {}.", request.getUserId());
      throw new NotExistException("User not exist");
    }
    if (! developerUser.getDeveloperId().equals(request.getApplicantId())) {
      LOG.debug("Applicant: {} do not have the user: {}.", request.getApplicantId(),
          request.getUserId());
      throw new NotExistException("Applicant do not have the user");
    }

    DeveloperUser user = developerUserService
        .getUserByPlatform(developerUser.getPUid(), request.getAcceptorId());

    if (user == null) {
      LOG.debug("Acceptor: {} do not have the user: {} from applicant: {}.",
          request.getAcceptorId(), request.getUserId(), request.getApplicantId());
      throw new NotExistException("Acceptor do not have the user");
    }

    // TODO: 17/6/9
    // 2. 获取user对应的资源id
    // 3. user是否有对应权限, 没有则跳过

    // 4. 记录请求：请求的开发者和user的id，受理者id，资源列表，处理结果，查看结果
    ResourceRequest resourceRequest = ResourceRequestMapper.build(request, user);
    requestService.save(resourceRequest);
  }
}
