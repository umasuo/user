package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.SET_ROLE_SCOPES;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.SetRoleScopes;
import com.umasuo.user.domain.model.Role;
import com.umasuo.user.domain.model.Scope;
import com.umasuo.user.domain.service.ScopeService;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Update service for SetRoleScopes.
 *
 * Created by Davis on 17/6/3.
 */
@Service(SET_ROLE_SCOPES)
public class SetRoleScopesService implements Updater<Role, UpdateAction>{

  /**
   * Scope service.
   */
  @Autowired
  private transient ScopeService scopeService;

  /**
   * Set role's scopes.
   *
   * @param role         role
   * @param updateAction Action
   */
  @Override
  public void handle(Role role, UpdateAction updateAction) {
    SetRoleScopes setRoleScopes = (SetRoleScopes) updateAction;
    List<Scope> scopes = scopeService.getListById(setRoleScopes.getScopes());
    role.setScopes(scopes);
    // TODO update the login admin's scope
  }
}
