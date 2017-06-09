package com.umasuo.user.application.dto.mapper;

import com.umasuo.user.application.dto.PermissionRequest;
import com.umasuo.user.application.dto.ResourceRequestView;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.ResourceRequest;
import com.umasuo.user.infrastructure.enums.ReplyRequest;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 */
public final class ResourceRequestMapper {

  /**
   * Instantiates a new Resource request mapper.
   */
  private ResourceRequestMapper() {
  }

  public static ResourceRequest build(PermissionRequest request, DeveloperUser user) {
    ResourceRequest result = new ResourceRequest();

    result.setAcceptorId(request.getAcceptorId());
    result.setApplicantId(request.getApplicantId());
    result.setApplicantViewed(false);
    result.setReplyRequest(ReplyRequest.UNVIEW);
    result.setApplicantUserId(request.getUserId());
    result.setAcceptorId(user.getId());

    return result;
  }

  public static List<ResourceRequestView> toModel(List<ResourceRequest> entities) {
    // TODO: 17/6/9
    return null;
  }

  public static ResourceRequestView toModel(ResourceRequest updatedRequest) {
    // TODO: 17/6/9
    return null;
  }
}
