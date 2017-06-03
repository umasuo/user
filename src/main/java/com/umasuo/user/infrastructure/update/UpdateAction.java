package com.umasuo.user.infrastructure.update;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.ADD_MANAGER;
import static com.umasuo.user.infrastructure.util.GroupActionUtils.ADD_USER;
import static com.umasuo.user.infrastructure.util.GroupActionUtils.REMOVE_MANAGER;
import static com.umasuo.user.infrastructure.util.GroupActionUtils.REMOVE_USER;
import static com.umasuo.user.infrastructure.util.GroupActionUtils.SET_GROUP_NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umasuo.user.application.dto.action.authorization.AddAuthorizationRoles;
import com.umasuo.user.application.dto.action.authorization.RemoveAuthorizationRoles;
import com.umasuo.user.application.dto.action.authorization.SetRoleName;
import com.umasuo.user.application.dto.action.authorization.SetRoleScopes;
import com.umasuo.user.application.dto.action.authorization.SetScopeModules;
import com.umasuo.user.application.dto.action.authorization.SetScopeName;
import com.umasuo.user.application.dto.action.authorization.SetScopePermissions;
import com.umasuo.user.application.dto.action.authorization.UpdateModule;
import com.umasuo.user.application.dto.action.group.AddManager;
import com.umasuo.user.application.dto.action.group.AddUser;
import com.umasuo.user.application.dto.action.group.RemoveManager;
import com.umasuo.user.application.dto.action.group.RemoveUser;
import com.umasuo.user.application.dto.action.group.SetGroupName;
import com.umasuo.user.infrastructure.util.AuthorizationActionUtils;

/**
 * Configurations for common update actions that will be used in more thant one service
 * and this action also extends other action configure in each service.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "action")
@JsonSubTypes({
    @JsonSubTypes.Type(value = SetGroupName.class, name = SET_GROUP_NAME),
    @JsonSubTypes.Type(value = AddUser.class, name = ADD_USER),
    @JsonSubTypes.Type(value = RemoveUser.class, name = REMOVE_USER),
    @JsonSubTypes.Type(value = AddManager.class, name = ADD_MANAGER),
    @JsonSubTypes.Type(value = RemoveManager.class, name = REMOVE_MANAGER),
    @JsonSubTypes.Type(value = UpdateModule.class, name = AuthorizationActionUtils.UPDATE_MODULE),
    @JsonSubTypes.Type(value = SetScopePermissions.class, name = AuthorizationActionUtils
        .SET_SCOPE_PERMISSIONS),
    @JsonSubTypes.Type(value = SetScopeName.class, name = AuthorizationActionUtils.SET_SCOPE_NAME),
    @JsonSubTypes.Type(value = SetScopeModules.class, name = AuthorizationActionUtils.SET_SCOPE_MODULES),
    @JsonSubTypes.Type(value = SetRoleScopes.class, name = AuthorizationActionUtils.SET_ROLE_SCOPES),
    @JsonSubTypes.Type(value = SetRoleName.class, name = AuthorizationActionUtils.SET_ROLE_NAME),
    @JsonSubTypes.Type(value = AddAuthorizationRoles.class, name = AuthorizationActionUtils.ADD_AUTHORIZATION_ROLES),
    @JsonSubTypes.Type(value = RemoveAuthorizationRoles.class, name = AuthorizationActionUtils.REMOVE_AUTHORIZATION_ROLES)
})
public interface UpdateAction {

  /**
   * Get action name.
   *
   * @return String
   */
  String getActionName();
}
