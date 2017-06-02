package com.umasuo.user.application.dto.action.authorization;

import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.util.AuthorizationActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Set role name
 */
@Data
public class SetRoleName implements UpdateAction {

  /**
   * Role name.
   */
  @NotNull
  private String scopeName;

  /**
   * Get action name.
   *
   * @return String
   */
  @Override
  public String getActionName() {
    return AuthorizationActionUtils.SET_ROLE_NAME;
  }
}
