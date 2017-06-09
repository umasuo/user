package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.PermissionRequest;
import com.umasuo.user.application.dto.ResourceRequestView;
import com.umasuo.user.application.service.PermissionApplication;
import com.umasuo.user.application.service.ResourceRequestApplication;
import com.umasuo.user.infrastructure.enums.ReplyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * Created by Davis on 17/6/8.
 */
public class ResourceRequestController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResourceRequestController.class);

  /**
   * The Permission application.
   */
  @Autowired
  private PermissionApplication permissionApplication;

  /**
   * The Request application.
   */
  @Autowired
  private ResourceRequestApplication requestApplication;

  /**
   * Request permission.
   *
   * @param request the request
   */
  public void requestPermission(PermissionRequest request) {
    LOG.info("Enter. permissionRequest: {}.", request);

    permissionApplication.handleRequest(request);

    LOG.info("Exit.");
  }

  /**
   * Gets all request for applicant.
   *
   * @param userId the user id
   */
  public List<ResourceRequestView> getAllRequestForApplicantUser(@RequestHeader String userId) {
    LOG.info("Enter. applicantUserId: {}.", userId);

    List<ResourceRequestView> result = requestApplication.getAllRequestForApplicantUser(userId);

    LOG.info("Exit. applicant user request size: {}.", result.size());

    return result;
  }

  /**
   * Gets all request for acceptor.
   *
   * @param userId the user id
   */
  public List<ResourceRequestView> getAllRequestForAcceptorUser(@RequestHeader String userId) {
    LOG.info("Enter. acceptorUserId: {}.", userId);

    List<ResourceRequestView> result = requestApplication.getAllRequestForApplicantUser(userId);

    LOG.info("Exit. acceptor user request size: {}.", result.size());

    return result;
  }

  /**
   * Reply request.
   *  @param userId the user id
   * @param requestId the request id
   * @param reply the reply
   */
  public ResourceRequestView replyRequest(@RequestHeader String userId,
      String requestId, ReplyRequest reply) {
    LOG.info("Enter. userId: {}, requestId: {}, reply: {}.", userId, requestId, reply);

    ResourceRequestView result = requestApplication.replyRequest(userId, requestId, reply);

    LOG.info("Exit. apply result: {}.", result);

    return result;
  }

  /**
   * Feed back for applicant.
   *  @param userId the user id
   * @param requestId the request id
   */
  public List<ResourceRequestView> feedBackForApplicant(@RequestHeader String userId,
      List<String> requestId) {
    LOG.info("Enter. userId: {}, requestId: {}.", userId, requestId);

    List<ResourceRequestView> result = requestApplication.feedBackForApplicant(userId, requestId);

    LOG.info("Exit. feedBack size: {}.", result.size());

    return result;
  }
}
