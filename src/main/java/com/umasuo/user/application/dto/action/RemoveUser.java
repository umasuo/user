package com.umasuo.user.application.dto.action;

import static com.umasuo.user.infrastructure.util.ActionUtils.REMOVE_USER;

import com.umasuo.user.infrastructure.update.UpdateAction;

import lombok.Getter;
import lombok.Setter;

/**
 * Update action for remove user.
 *
 * Created by Davis on 17/6/1.
 */
@Getter
@Setter
public class RemoveUser implements UpdateAction {

  /**
   * The user id.
   */
  private String userId;

  /**
   * Get action name.
   *
   * @return removeUser
   */
  @Override
  public String getActionName() {
    return REMOVE_USER;
  }
}
