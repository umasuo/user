package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.SET_SCOPE_NAME;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.SetScopeName;
import com.umasuo.user.domain.model.Scope;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.springframework.stereotype.Service;

/**
 * Set scope name.
 */
@Service(SET_SCOPE_NAME)
public class SetScopeNameService implements Updater<Scope, UpdateAction> {

  /**
   * Set scope's name.
   *
   * @param scope        scope
   * @param updateAction Action
   */
  @Override
  public void handle(Scope scope, UpdateAction updateAction) {
    SetScopeName setScopeName = (SetScopeName) updateAction;
    scope.setScopeName(setScopeName.getScopeName());
  }
}
