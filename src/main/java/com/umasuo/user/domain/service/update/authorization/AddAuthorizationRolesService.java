package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.ADD_AUTHORIZATION_ROLES;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.AddAuthorizationRoles;
import com.umasuo.user.domain.model.Authorization;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Update service for AddAuthorizationRoles.
 *
 * Created by Davis on 17/6/3.
 */
@Service(ADD_AUTHORIZATION_ROLES)
public class AddAuthorizationRolesService implements Updater<Authorization, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddAuthorizationRolesService.class);

  /**
   * Add roles to authorization.
   *
   * @param authorization the Authorization
   * @param updateAction AddAuthorizationRoles action
   */
  @Override
  public void handle(Authorization authorization, UpdateAction updateAction) {
    AddAuthorizationRoles addAuthorizationRoles = (AddAuthorizationRoles) updateAction;
    List<String> roleId = addAuthorizationRoles.getRoleId();
    // TODO: 17/6/3 check if roles exist
    if (!Collections.disjoint(authorization.getRoles(), roleId)) {
      LOG.debug("Can not add the same role to authorization.");
      throw new AlreadyExistException("Role is exist in authorization");
    }
    authorization.getRoles().addAll(roleId);
  }
}
