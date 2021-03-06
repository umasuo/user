package com.umasuo.user.domain.service.update.group;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.group.AddUser;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.update.UpdateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umasuo.user.infrastructure.util.GroupActionUtils.ADD_USER;

/**
 * AddUser service.
 *
 * Created by Davis on 17/6/2.
 */
@Service(ADD_USER)
public class AddUserService implements Updater<Group, UpdateAction>{

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AddUserService.class);

  /**
   * Add user into group.
   *
   * @param group the group
   * @param updateAction AddUser action
   */
  @Override
  public void handle(Group group, UpdateAction updateAction) {
    AddUser addUser = (AddUser) updateAction;
    String userId = addUser.getUserId();

    if (group.getUsers().contains(userId)) {
      LOG.debug("User is exist.");
      throw new AlreadyExistException("User is exist");
    }
    group.getUsers().add(userId);
  }
}
