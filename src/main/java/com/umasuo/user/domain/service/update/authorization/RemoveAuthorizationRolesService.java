package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.REMOVE_AUTHORIZATION_ROLES;

import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.RemoveAuthorizationRoles;
import com.umasuo.user.domain.model.Authorization;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Update service for RemoveAuthorizationRoles.
 *
 * Created by Davis on 17/6/3.
 */
@Service(REMOVE_AUTHORIZATION_ROLES)
public class RemoveAuthorizationRolesService implements Updater<Authorization, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveAuthorizationRolesService.class);

  /**
   * Remove roles from authorization.
   *
   * @param authorization the Authorization
   * @param updateAction RemoveAuthorizationRoles action
   */
  @Override
  public void handle(Authorization authorization, UpdateAction updateAction) {
    RemoveAuthorizationRoles addAuthorizationRoles = (RemoveAuthorizationRoles) updateAction;
    List<String> roleId = addAuthorizationRoles.getRoleId();
    if (!authorization.getRoles().containsAll(roleId)) {
      LOG.debug("Can not remove not exist role from authorization.");
      throw new NotExistException("Role not exist");
    }
    authorization.getRoles().removeAll(roleId);
  }
}
