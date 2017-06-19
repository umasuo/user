package com.umasuo.user.application.dto.mapper;

import com.umasuo.user.application.dto.QuickSignIn;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;

/**
 * Created by umasuo on 17/3/9.
 */
public final class SignInMapper {

  /**
   * Instantiates a new Sign in mapper.
   */
  private SignInMapper() {
  }

  /**
   * To platform user platform user.
   *
   * @param signIn the sign in
   * @return the platform user
   */
  public static PlatformUser toPlatformUser(QuickSignIn signIn) {
    PlatformUser platformUser = null;
    if (signIn != null) {
      platformUser = new PlatformUser();
      platformUser.setPhone(signIn.getPhone());
      //TODO external id, for now, we do not support.
    }
    return platformUser;
  }

  /**
   * To developer user developer user.
   *
   * @param signIn the sign in
   * @return the developer user
   */
  public static DeveloperUser toDeveloperUser(QuickSignIn signIn) {
    DeveloperUser developerUser = null;
    if (signIn != null) {
      developerUser = new DeveloperUser();
      developerUser.setDeveloperId(signIn.getDeveloperId());
    }
    return developerUser;
  }
}
