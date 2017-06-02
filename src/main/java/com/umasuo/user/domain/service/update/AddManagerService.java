package com.umasuo.user.domain.service.update;

import static com.umasuo.user.infrastructure.util.ActionUtils.ADD_MANAGER;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.AddManager;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * AddManager service.
 *
 * Created by Davis on 17/6/2.
 */
@Service(ADD_MANAGER)
public class AddManagerService implements Updater<Group, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddManagerService.class);

  /**
   * Add manager.
   *
   * @param group the group
   * @param updateAction AddManager action
   */
  @Override
  public void handle(Group group, UpdateAction updateAction) {
    AddManager addManager = (AddManager) updateAction;
    String managerId = addManager.getManagerId();

    if (group.getOrganization().getUsers() == null ||
        group.getOrganization().getUsers().isEmpty()) {
      LOG.debug("Can not add manager when there is no user in the group.");
      throw new ParametersException("User is null");
    }
    if (! group.getOrganization().getUsers().contains(managerId)) {
      LOG.debug("Can not add a manager who is not user in the group.");
      throw new ParametersException("Manager not a user");
    }
    if (group.getOrganization().getManagers().contains(managerId)) {
      LOG.debug("Manager is exist.");
      throw new AlreadyExistException("Manager is exist");
    }
    group.getOrganization().getManagers().add(managerId);
  }
}
