package com.umasuo.user.domain.service.update;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.REMOVE_MANAGER;

import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.group.RemoveManager;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * RemoveManager service.
 *
 * Created by Davis on 17/6/2.
 */
@Service(REMOVE_MANAGER)
public class RemoveManagerService implements Updater<Group, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveManagerService.class);

  /**
   * Remove manager from group.
   *
   * @param group the group
   * @param updateAction RemoveManager action
   */
  @Override
  public void handle(Group group, UpdateAction updateAction) {
    RemoveManager removeManager = (RemoveManager) updateAction;
    String managerId = removeManager.getManagerId();

    if (!group.getOrganization().getManagers().contains(managerId)) {
      LOG.debug("Can not remove not exist manager: {}.", managerId);
      throw new NotExistException("Manager not exist.");
    }

    Predicate<String> predicate = s -> s.equals(managerId);
    group.getOrganization().getManagers().removeIf(predicate);
  }
}
