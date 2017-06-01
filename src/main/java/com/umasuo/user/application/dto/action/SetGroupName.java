package com.umasuo.user.application.dto.action;

import static com.umasuo.user.infrastructure.util.ActionUtils.SET_GROUP_NAME;

import com.umasuo.user.infrastructure.update.UpdateAction;

/**
 * Update action for set group name.
 * Created by Davis on 17/6/1.
 */
public class SetGroupName implements UpdateAction {

  /**
   * The name.
   */
  private String name;

  @Override
  public String getActionName() {
    return SET_GROUP_NAME;
  }
}
