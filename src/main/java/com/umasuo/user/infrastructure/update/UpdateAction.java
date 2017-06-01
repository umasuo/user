package com.umasuo.user.infrastructure.update;

import static com.umasuo.user.infrastructure.util.ActionUtils.ADD_MANAGER;
import static com.umasuo.user.infrastructure.util.ActionUtils.ADD_USER;
import static com.umasuo.user.infrastructure.util.ActionUtils.REMOVE_MANAGER;
import static com.umasuo.user.infrastructure.util.ActionUtils.REMOVE_USER;
import static com.umasuo.user.infrastructure.util.ActionUtils.SET_GROUP_NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.umasuo.user.application.dto.action.AddManager;
import com.umasuo.user.application.dto.action.AddUser;
import com.umasuo.user.application.dto.action.RemoveManager;
import com.umasuo.user.application.dto.action.RemoveUser;
import com.umasuo.user.application.dto.action.SetGroupName;

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
})
public interface UpdateAction {

  /**
   * Get action name.
   *
   * @return String
   */
  String getActionName();
}
