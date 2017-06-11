package com.umasuo.user.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.user.application.dto.PermissionRequest;
import com.umasuo.user.application.dto.ResourceRequestView;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.ResourceRequest;
import com.umasuo.user.infrastructure.enums.ReplyRequest;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/9.
 */
public final class ResourceRequestMapper {

  /**
   * Instantiates a new Resource request mapper.
   */
  private ResourceRequestMapper() {
  }

  /**
   * Build resource request.
   *
   * @param request the request
   * @param user the user
   * @return the resource request
   */
  public static ResourceRequest build(PermissionRequest request, DeveloperUser user) {
    ResourceRequest result = new ResourceRequest();

    result.setAcceptorId(request.getAcceptorId());
    result.setApplicantId(request.getApplicantId());
    result.setApplicantViewed(false);
    result.setReplyRequest(ReplyRequest.UNVIEWED);
    result.setApplicantUserId(request.getUserId());
    result.setAcceptorId(user.getId());

    return result;
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<ResourceRequestView> toModel(List<ResourceRequest> entities) {
    List<ResourceRequestView> models = Lists.newArrayList();

    Consumer<ResourceRequest> consumer = request -> models.add(toModel(request));

    entities.stream().forEach(consumer);

    return models;
  }

  /**
   * To model resource request view.
   *
   * @param entity the entity
   * @return the resource request view
   */
  public static ResourceRequestView toModel(ResourceRequest entity) {
    ResourceRequestView model = new ResourceRequestView();

    model.setId(entity.getId());
    model.setVersion(entity.getVersion());
    model.setApplicantViewed(entity.getApplicantViewed());
    model.setApplicantUserId(entity.getApplicantUserId());
    model.setApplicantId(entity.getApplicantId());
    model.setAcceptorId(entity.getAcceptorId());
    model.setAcceptorUserId(entity.getAcceptorUserId());
    model.setDeviceId(entity.getDeviceId());
    model.setReplyRequest(entity.getReplyRequest());
    model.setReferences(entity.getReferences());

    return model;
  }
}
