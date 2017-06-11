package com.umasuo.user.infrastructure.validator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.umasuo.exception.AuthFailedException;
import com.umasuo.user.domain.model.ResourceRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/11.
 */
public final class FeedBackValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(FeedBackValidator.class);

  /**
   * Instantiates a new Feed back validator.
   */
  private FeedBackValidator() {
  }

  /**
   * Check if the acceptor user have authentication to feedback those request.
   *
   * @param userId user id
   * @param requests ResourceRequest found from database
   */
  public static void validateAcceptorFeedBackAuth(String userId, List<ResourceRequest> requests) {
    Map<String, String> userRequestMap = Maps.newHashMap();
    Consumer<ResourceRequest> consumer =
        request -> userRequestMap.put(request.getId(), request.getAcceptorUserId());
    requests.stream().forEach(consumer);

    validateFeedBackAuth(userId, userRequestMap);
  }

  /**
   * Check if the applicant user have authentication to feedback those request.
   *
   * @param userId user id
   * @param requests ResourceRequest found from database
   */
  public static void validateApplicantFeedBackAuth(String userId, List<ResourceRequest> requests) {
    Map<String, String> userRequestMap = Maps.newHashMap();
    Consumer<ResourceRequest> consumer =
        request -> userRequestMap.put(request.getId(), request.getApplicantUserId());
    requests.stream().forEach(consumer);

    validateFeedBackAuth(userId, userRequestMap);
  }

  /**
   * Check if the developer have the auth to feedback the request.
   *
   * @param userId the user id
   * @param userRequestMap the map
   */
  private static void validateFeedBackAuth(String userId, Map<String, String> userRequestMap) {
    List<String> notAuthUsers = Lists.newArrayList();
    Consumer<Entry<String, String>> entryConsumer = entry -> {
      if (!entry.getValue().equals(userId)) {
        notAuthUsers.add(entry.getKey());
      }
    };
    userRequestMap.entrySet().stream().forEach(entryConsumer);

    if (!notAuthUsers.isEmpty()) {
      LOG.debug("User: {} do not have auth to feedback request: {}.",
          userId, notAuthUsers);
      throw new AuthFailedException("User do not have authorization to feedback request");
    }
  }
}
