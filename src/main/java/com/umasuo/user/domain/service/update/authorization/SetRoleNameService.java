package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.SET_ROLE_NAME;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.SetRoleName;
import com.umasuo.user.domain.model.Role;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.springframework.stereotype.Service;

/**
 * Update service for SetRoleName.
 *
 * Created by Davis on 17/6/3.
 */
@Service(SET_ROLE_NAME)
public class SetRoleNameService implements Updater<Role, UpdateAction> {

  /**
   * Set role name.
   *
   * @param role the Role
   * @param updateAction SetRoleName action
   */
  @Override
  public void handle(Role role, UpdateAction updateAction) {
    SetRoleName setRoleName = (SetRoleName) updateAction;
    String roleName = setRoleName.getRoleName();
    role.setRoleName(roleName);
  }
}
