package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.SET_SCOPE_PERMISSIONS;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.SetScopePermissions;
import com.umasuo.user.application.dto.mapper.PermissionMapper;
import com.umasuo.user.domain.model.Scope;
import com.umasuo.user.infrastructure.enums.Permission;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Set scope permissions.
 */
@Service(SET_SCOPE_PERMISSIONS)
public class SetScopePermissionService implements Updater<Scope, UpdateAction> {

  /**
   * Set scope's permissions.
   *
   * @param scope        scope
   * @param updateAction Action
   */
  @Override
  public void handle(Scope scope, UpdateAction updateAction) {
    SetScopePermissions setScopePermissions = (SetScopePermissions) updateAction;
    List<Permission> permissions = PermissionMapper.toEntity(setScopePermissions.getPermissions());
    scope.setPermissions(permissions);
    // TODO update the login admin's scope
  }
}
