package com.umasuo.user.application.dto.mapper;

import com.umasuo.user.application.dto.SignIn;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;

/**
 * Created by umasuo on 17/3/9.
 */
public class SignInMapper {

  public static PlatformUser toPlatformUser(SignIn signIn) {
    PlatformUser platformUser = null;
    if (signIn != null) {
      platformUser = new PlatformUser();
      platformUser.setPhone(signIn.getPhone());
      platformUser.setEmail(signIn.getEmail());
      //TODO external id, for now, we do not support.
    }
    return platformUser;
  }

  public static DeveloperUser toDeveloperUser(SignIn signIn) {
    DeveloperUser developerUser = null;
    if (signIn != null) {
      developerUser = new DeveloperUser();
      developerUser.setPassword(signIn.getPassword());
      developerUser.setDeveloperId(signIn.getDeveloperId());
      developerUser.setDeviceDefinitionId(signIn.getDeviceDefinitionId());
    }
    return developerUser;
  }
}
