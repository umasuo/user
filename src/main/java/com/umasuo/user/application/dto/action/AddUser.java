package com.umasuo.user.application.dto.action;

import static com.umasuo.user.infrastructure.util.ActionUtils.ADD_USER;

import com.umasuo.user.infrastructure.update.UpdateAction;

import lombok.Getter;
import lombok.Setter;

/**
 * Update action for add user.
 *
 * Created by Davis on 17/6/1.
 */
@Getter
@Setter
public class AddUser implements UpdateAction {

  /**
   * The user id.
   */
  private String userId;

  /**
   * Get action name.
   *
   * @return addUser
   */
  @Override
  public String getActionName() {
    return ADD_USER;
  }
}
