package com.umasuo.user.domain.service.update.group;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.group.AddManager;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.update.UpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.ADD_MANAGER;

/**
 * AddManager service.
 */
@Service(ADD_MANAGER)
public class AddManagerService implements Updater<Group, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AddManagerService.class);

  /**
   * Add manager.
   *
   * @param group        the group
   * @param updateAction AddManager action
   */
  @Override
  public void handle(Group group, UpdateAction updateAction) {

    if (group.getUsers() == null || group.getUsers().isEmpty()) {
      LOGGER.debug("Can not add manager when there is no user in the group.");
      throw new ParametersException("User is null");
    }


    AddManager addManager = (AddManager) updateAction;
    String managerId = addManager.getManagerId();
    if (!group.getUsers().contains(managerId)) {
      LOGGER.debug("Can not add a manager who is not user in the group.");
      throw new ParametersException("Manager not a user");
    }
    if (group.getManagers().contains(managerId)) {
      LOGGER.debug("Manager is exist.");
      throw new AlreadyExistException("Manager is exist");
    }
    group.getManagers().add(managerId);
  }
}
