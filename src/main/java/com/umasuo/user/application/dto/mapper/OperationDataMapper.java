package com.umasuo.user.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.user.application.dto.UserOperationData;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Operation data.
 */
public final class OperationDataMapper {

  /**
   * Private default constructor..
   */
  private OperationDataMapper() {
  }

  /**
   * Merge platform & developer user.
   *
   * @param platformUsers
   * @param developerUsers
   * @return
   */
  public static List<UserOperationData> merge(List<PlatformUser> platformUsers,
                                              List<DeveloperUser> developerUsers) {
    List<UserOperationData> result = Lists.newArrayList();

    Consumer<DeveloperUser> consumer =
      developerUser -> result.add(merge(developerUser, platformUsers));

    developerUsers.stream().forEach(consumer);

    return result;
  }

  /**
   * Merge developer user & platform user.
   *
   * @param developerUser
   * @param platformUsers
   * @return
   */
  public static UserOperationData merge(DeveloperUser developerUser,
                                        List<PlatformUser> platformUsers) {
    final AtomicReference<UserOperationData> reference = new AtomicReference<>();

    Consumer<PlatformUser> consumer = platformUser -> {
      if (developerUser.getPUid().equals(platformUser.getId())) {
        reference.set(UserOperationData.build(platformUser, developerUser));
      }
    };
    platformUsers.stream().forEach(consumer);

    return reference.get();
  }

  /**
   * Filter.
   *
   * @param platformUser
   * @param developerUsers
   * @return
   */
  public static List<UserOperationData> filter(PlatformUser platformUser,
                                               List<DeveloperUser> developerUsers) {
    List<UserOperationData> result = Lists.newArrayList();
    if (platformUser != null) {
      DeveloperUser filterUser = developerUsers.stream()
        .filter(user -> user.getPUid().equals(platformUser.getId())).findAny().orElse(null);

      if (filterUser != null) {
        UserOperationData userData = UserOperationData.build(platformUser, filterUser);
        result.add(userData);
      }
    }
    return result;
  }

}
