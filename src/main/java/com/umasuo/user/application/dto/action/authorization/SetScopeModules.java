package com.umasuo.user.application.dto.action.authorization;

import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.util.AuthorizationActionUtils;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Set scope modules
 */
@Data
public class SetScopeModules implements UpdateAction {

  /**
   * List of module ids.
   */
  @NotNull
  private List<String> modules;

  /**
   * Get action name.
   *
   * @return String
   */
  @Override
  public String getActionName() {
    return AuthorizationActionUtils.SET_SCOPE_MODULES;
  }
}
