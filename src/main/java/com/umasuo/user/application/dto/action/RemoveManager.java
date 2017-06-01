package com.umasuo.user.application.dto.action;

import static com.umasuo.user.infrastructure.util.ActionUtils.REMOVE_MANAGER;

import com.umasuo.user.infrastructure.update.UpdateAction;

import lombok.Getter;
import lombok.Setter;

/**
 * Update action for remove manager.
 *
 * Created by Davis on 17/6/1.
 */
@Getter
@Setter
public class RemoveManager implements UpdateAction {

  /**
   * The manager id.
   */
  private String managerId;

  /**
   * Get action name.
   *
   * @return removeManager
   */
  @Override
  public String getActionName() {
    return REMOVE_MANAGER;
  }
}
