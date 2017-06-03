package com.umasuo.user.domain.service.update.group;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.SET_GROUP_NAME;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.group.SetGroupName;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * SetGroupName service.
 *
 * Created by Davis on 17/6/2.
 */
@Service(SET_GROUP_NAME)
public class SetGroupNameService implements Updater<Group, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(SetGroupNameService.class);

  /**
   * Set group name.
   *
   * @param group the group
   * @param updateAction SetGroupName action
   */
  @Override
  public void handle(Group group, UpdateAction updateAction) {
    SetGroupName setGroupName = (SetGroupName) updateAction;
    group.setName(setGroupName.getName());
  }
}
