package com.umasuo.user.application.dto.action;

import static com.umasuo.user.infrastructure.util.ActionUtils.SET_GROUP_NAME;

import com.umasuo.user.infrastructure.update.UpdateAction;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Update action for set group name.
 * Created by Davis on 17/6/1.
 */
@Getter
@Setter
public class SetGroupName implements UpdateAction {

  /**
   * The name.
   */
  @NotNull(message = "Group name can not be null")
  private String name;

  /**
   * Get action name.
   *
   * @return setGroupName
   */
  @Override
  public String getActionName() {
    return SET_GROUP_NAME;
  }
}
