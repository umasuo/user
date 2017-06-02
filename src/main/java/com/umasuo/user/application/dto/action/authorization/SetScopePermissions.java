package com.umasuo.user.application.dto.action.authorization;

import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.util.AuthorizationActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Set scope permissions
 */
@Data
public class SetScopePermissions implements UpdateAction {

  /**
   * List of permission.
   */
  @NotNull
  private List<String> permissions;

  /**
   * Get action name.
   *
   * @return String
   */
  @Override
  public String getActionName() {
    return AuthorizationActionUtils.SET_SCOPE_PERMISSIONS;
  }
}
