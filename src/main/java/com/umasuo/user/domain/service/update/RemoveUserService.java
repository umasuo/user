package com.umasuo.user.domain.service.update;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.REMOVE_USER;

import com.umasuo.exception.NotExistException;
import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.group.RemoveUser;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * RemoveUser service.
 *
 * Created by Davis on 17/6/2.
 */
@Service(REMOVE_USER)
public class RemoveUserService implements Updater<Group, UpdateAction> {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RemoveUserService.class);

  /**
   * Remove user from group.
   * If user is a manager, should remove manager together.
   *
   * @param group the group
   * @param updateAction RemoveUser action
   */
  @Override
  public void handle(Group group, UpdateAction updateAction) {
    RemoveUser removeUser = (RemoveUser) updateAction;
    String userId = removeUser.getUserId();

    if (!group.getOrganization().getUsers().contains(userId)) {
      LOG.debug("User: {} not exist in group: {}.", userId, group.getId());
      throw new NotExistException("User not exist");
    }

    Predicate<String> predicate = s -> s.equals(userId);
    group.getOrganization().getUsers().removeIf(predicate);

    if (group.getOrganization().getManagers().contains(userId)) {
      group.getOrganization().getManagers().removeIf(predicate);
    }
  }
}
