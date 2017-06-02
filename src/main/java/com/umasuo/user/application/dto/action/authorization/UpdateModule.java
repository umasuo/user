package com.umasuo.user.application.dto.action.authorization;

import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.util.AuthorizationActionUtils;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Update a module.
 */
@Data
public class UpdateModule implements UpdateAction {

  /**
   * Module name.
   */
  @NotNull
  private String moduleName;

  /**
   * Path of this module.
   */
  @NotNull
  private String path;

  /**
   * Get action name.
   *
   * @return String
   */
  @Override
  public String getActionName() {
    return AuthorizationActionUtils.UPDATE_MODULE;
  }
}
