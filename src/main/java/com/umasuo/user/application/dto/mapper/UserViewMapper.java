package com.umasuo.user.application.dto.mapper;

import com.umasuo.user.application.dto.UserView;
import com.umasuo.user.domain.model.DeveloperUser;
import com.umasuo.user.domain.model.PlatformUser;

/**
 * Created by umasuo on 17/3/9.
 */
public final class UserViewMapper {

  /**
   * Instantiates a new User view mapper.
   */
  private UserViewMapper() {
  }

  /**
   * Get user view from platform and developer.
   *
   * @param pUser PlatformUser
   * @param dUser DeveloperUser
   * @return UserView user view
   */
  public static UserView toUserView(PlatformUser pUser, DeveloperUser dUser) {
    UserView view = null;
    if (pUser != null && dUser != null) {
      view = new UserView();
      view.setUserId(dUser.getId());
      view.setDeveloperId(dUser.getDeveloperId());
      view.setDeviceDefinitionId(dUser.getDeviceDefinitionId());
      view.setEmail(pUser.getEmail());
      view.setPhone(pUser.getPhone());
      view.setExternalId(pUser.getExternalId());
      if (dUser.getUserDetail() != null) {
        view.setAge(dUser.getUserDetail().getAge());
        view.setIcon(dUser.getUserDetail().getIcon());
        view.setName(dUser.getUserDetail().getName());
      }
    }
    return view;
  }
}
