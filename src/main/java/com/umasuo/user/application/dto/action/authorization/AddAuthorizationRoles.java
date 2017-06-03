package com.umasuo.user.application.dto.action.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.ADD_AUTHORIZATION_ROLES;

import com.umasuo.user.infrastructure.update.UpdateAction;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.validation.constraints.Size;

/**
 * Add roles to authorization.
 *
 * Created by Davis on 17/6/3.
 */
@Getter
@Setter
public class AddAuthorizationRoles implements UpdateAction{


  /**
   * The Role id.
   */
  @Size(min = 1)
  private List<String> roleId;

  /**
   * Get action name.
   *
   * @return addAuthorizationRoles
   */
  @Override
  public String getActionName() {
    return ADD_AUTHORIZATION_ROLES;
  }
}
