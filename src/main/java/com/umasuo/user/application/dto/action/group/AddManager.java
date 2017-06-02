package com.umasuo.user.application.dto.action.group;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.ADD_MANAGER;

import com.umasuo.user.infrastructure.update.UpdateAction;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Update action for add manager.
 *
 * Created by Davis on 17/6/1.
 */
@Getter
@Setter
public class AddManager implements UpdateAction {

  /**
   * The manager id.
   */
  @NotNull(message = "Manager id can not be null")
  private String managerId;

  /**
   * Get action name.
   *
   * @return addManager
   */
  @Override
  public String getActionName() {
    return ADD_MANAGER;
  }
}
