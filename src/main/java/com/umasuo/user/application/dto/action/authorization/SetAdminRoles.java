package com.umasuo.user.application.dto.action.authorization;

import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.util.AuthorizationActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Set admin roles.
 */
@Data
public class SetAdminRoles implements UpdateAction {

  /**
   * List of roles.
   */
  @NotNull
  private List<String> roles;

  /**
   * Get action name.
   *
   * @return String
   */
  @Override
  public String getActionName() {
    return AuthorizationActionUtils.SET_ADMIN_ROLES;
  }
}
