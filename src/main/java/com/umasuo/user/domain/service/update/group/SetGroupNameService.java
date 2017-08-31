package com.umasuo.user.domain.service.update.group;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.group.SetGroupName;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.update.UpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.SET_GROUP_NAME;

/**
 * SetGroupName service.
 */
@Service(SET_GROUP_NAME)
public class SetGroupNameService implements Updater<Group, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SetGroupNameService.class);

  /**
   * Set group name.
   *
   * @param group        the group
   * @param updateAction SetGroupName action
   */
  @Override
  public void handle(Group group, UpdateAction updateAction) {

    SetGroupName setGroupName = (SetGroupName) updateAction;
    LOGGER.debug("action: {}.", updateAction);

    group.setName(setGroupName.getName());
  }
}
