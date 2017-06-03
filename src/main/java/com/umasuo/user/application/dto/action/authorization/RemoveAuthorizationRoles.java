package com.umasuo.user.application.dto.action.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.REMOVE_AUTHORIZATION_ROLES;

import com.umasuo.user.infrastructure.update.UpdateAction;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.validation.constraints.Size;

/**
 * Created by Davis on 17/6/3.
 */
@Getter
@Setter
public class RemoveAuthorizationRoles implements UpdateAction {

  /**
   * The roles id.
   */
  @Size(min = 1)
  private List<String> roleId;

  /**
   * Get action name.
   *
   * @return removeAuthorizationRoles
   */
  @Override
  public String getActionName() {
    return REMOVE_AUTHORIZATION_ROLES;
  }
}
