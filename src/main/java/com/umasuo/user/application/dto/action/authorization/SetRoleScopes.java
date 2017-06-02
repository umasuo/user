package com.umasuo.user.application.dto.action.authorization;

import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.util.AuthorizationActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Set role scopes.
 */
@Data
public class SetRoleScopes implements UpdateAction {

  /**
   * List of scopes.
   */
  @NotNull
  private List<String> scopes;

  /**
   * Get action name.
   *
   * @return String
   */
  @Override
  public String getActionName() {
    return AuthorizationActionUtils.SET_ROLE_SCOPES;
  }
}
