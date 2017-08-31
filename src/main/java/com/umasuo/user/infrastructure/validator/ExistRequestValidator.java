package com.umasuo.user.infrastructure.validator;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.ResourceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Exist request validator.
 */
public final class ExistRequestValidator {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ExistRequestValidator.class);

  /**
   * Instantiates a new Exist request validator.
   */
  private ExistRequestValidator() {
  }

  /**
   * Check if the request exist.
   *
   * @param requestId the ResourceRequest id list
   * @param requests the ResourceRequest found from database by the id list
   */
  public static void validate(List<String> requestId, List<ResourceRequest> requests) {
    // 检查要反馈的请求是否存在
    List<String> existRequestId =
        requests.stream().map(ResourceRequest::getId).collect(Collectors.toList());

    requestId.removeAll(existRequestId);
    if (requestId.isEmpty()) {
      LOGGER.debug("Request: {} not exist.", requestId);
      throw new NotExistException("Request not exist: " + requestId);
    }
  }
}
