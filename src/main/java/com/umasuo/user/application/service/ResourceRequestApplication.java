package com.umasuo.user.application.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.exception.AuthFailedException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.application.dto.ResourceRequestView;
import com.umasuo.user.application.dto.mapper.ResourcePermissionMapper;
import com.umasuo.user.application.dto.mapper.ResourceRequestMapper;
import com.umasuo.user.domain.model.ResourcePermission;
import com.umasuo.user.domain.model.ResourceRequest;
import com.umasuo.user.domain.service.ResourcePermissionService;
import com.umasuo.user.domain.service.ResourceRequestService;
import com.umasuo.user.infrastructure.enums.ReplyRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * ResourceRequest application.
 *
 * Created by Davis on 17/6/9.
 */
@Service
public class ResourceRequestApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ResourceRequestApplication.class);

  /**
   * The Resource request service.
   */
  @Autowired
  private ResourceRequestService requestService;

  /**
   * The Permission service.
   */
  @Autowired
  private ResourcePermissionService permissionService;

  /**
   * Gets all request for applicant user.
   *
   * @param applicantUserId the applicant user id
   * @return the all request for applicant user
   */
  public List<ResourceRequestView> getAllRequestForApplicantUser(String applicantUserId) {
    LOG.debug("Enter. applicantUserId: {}.", applicantUserId);

    List<ResourceRequest> requests = requestService.getAllRequestForApplicantUser(applicantUserId);
    List<ResourceRequestView> result = ResourceRequestMapper.toModel(requests);

    LOG.debug("Exit. applicant user request size: {}.", result.size());

    return result;
  }

  /**
   * Gets all request for acceptor user.
   *
   * @param acceptorUserId the acceptor user id
   * @return the all request for acceptor user
   */
  public List<ResourceRequestView> getAllRequestForAcceptor(String acceptorUserId) {
    LOG.debug("Enter. acceptorUserId: {}.", acceptorUserId);

    List<ResourceRequest> requests = requestService.getAllRequestForAcceptorUser(acceptorUserId);
    List<ResourceRequestView> result = ResourceRequestMapper.toModel(requests);

    LOG.debug("Exit. acceptor user request size: {}.", result.size());

    return result;
  }

  /**
   * Reply request.
   *
   * @param userId the developer id
   * @param requestId the request id
   * @param reply the reply
   * @return the resource request view
   */
  public ResourceRequestView replyRequest(String userId, String requestId, ReplyRequest reply) {
    LOG.debug("Enter. userId: {}, requestId: {}, reply: {}.", userId, requestId, reply);

    ResourceRequest request = requestService.findOne(requestId);
    if (request == null) {
      LOG.debug("Can not find this resource request: {}.", requestId);
      throw new NotExistException("ResourceRequest not exist");
    }

    if (!request.getAcceptorUserId().equals(userId)) {
      LOG.debug("User: {} can not handle this request: {}.", userId, requestId);
      throw new AuthFailedException("User do not have authorization to handle request");
    }

    handlerReply(request, reply);

    ResourceRequest updatedRequest = requestService.save(request);
    ResourceRequestView result = ResourceRequestMapper.toModel(updatedRequest);

    LOG.debug("Exit. reply result: {}.", result);

    return result;
  }

  /**
   * Handle reply.
   *
   * @param request the ResourceRequest
   * @param reply the ReplyRequest
   */
  private void handlerReply(ResourceRequest request, ReplyRequest reply) {
    request.setReplyRequest(reply);
    switch (reply) {
      case AGREE:
        ResourcePermission permission = ResourcePermissionMapper.build(request);
        permissionService.save(permission);
        break;
      case DISAGREE:
        // TODO: 17/6/9 取消权限，需要把permission删除，并把对应的user权限也删除
        break;
      default:
        break;
    }
  }

  /**
   * Feed back for applicant user.
   *
   * @param userId the user id
   * @param requestId the request id
   * @return the list
   */
  public List<ResourceRequestView> feedBackForApplicant(String userId, List<String> requestId) {
    LOG.debug("Enter. developerId: {}, requestId: {}.", userId, requestId);

    List<ResourceRequest> requests = requestService.findAll(requestId);
    validateNotExitRequest(requestId, requests);
    validateFeedBackAuth(userId, requests);

    Consumer<ResourceRequest> feedBackConsumer = request -> request.setApplicantViewed(true);
    requests.stream().forEach(feedBackConsumer);
    List<ResourceRequest> feedBackedRequests = requestService.saveAll(requests);

    List<ResourceRequestView> result = ResourceRequestMapper.toModel(feedBackedRequests);

    LOG.debug("Exit. feedback size: {}.", result.size());

    return result;
  }

  /**
   * Check if the user have authentication to feedback those request.
   *
   * @param userId user id
   * @param requests ResourceRequest found from database
   */
  private void validateFeedBackAuth(String userId, List<ResourceRequest> requests) {
    // 检查user是否有权限反馈
    Map<String, String> developerRequestMap = Maps.newHashMap();
    Consumer<ResourceRequest> consumer =
        request -> developerRequestMap.put(request.getId(), request.getApplicantUserId());
    requests.stream().forEach(consumer);

    List<String> notAuthUsers = Lists.newArrayList();
    Consumer<Entry<String, String>> entryConsumer = entry -> {
      if (!entry.getValue().equals(userId)) {
        notAuthUsers.add(entry.getKey());
      }
    };
    developerRequestMap.entrySet().stream().forEach(entryConsumer);

    if (notAuthUsers.size() > 0) {
      LOG.debug("User: {} do not have auth to feedback request: {}.", userId,
          notAuthUsers);
      throw new AuthFailedException("User do not have authorization to feedback request");
    }
  }

  /**
   * Check if the request exist.
   *
   * @param requestId the ResourceRequest id list
   * @param requests the ResourceRequest found from database by the id list
   */
  private void validateNotExitRequest(List<String> requestId, List<ResourceRequest> requests) {
    // 检查要反馈的请求是否存在
    List<String> existRequestId =
        requests.stream().map(ResourceRequest::getId).collect(Collectors.toList());

    requestId.removeAll(existRequestId);
    if (requestId.size() > 0) {
      LOG.debug("Request: {} not exist.", requestId);
      throw new NotExistException("Request not exist: " + requestId);
    }
  }
}
