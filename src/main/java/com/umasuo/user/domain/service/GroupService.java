package com.umasuo.user.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.repository.GroupRepository;
import com.umasuo.user.infrastructure.update.GroupUpdaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

/**
 * Service for group.
 * Created by Davis on 17/5/27.
 */
@Service
public class GroupService {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(GroupService.class);

  /**
   * The Group repository.
   */
  @Autowired
  private GroupRepository groupRepository;

  /**
   * Update service.
   */
  private transient GroupUpdaterService updateService;

  /**
   * Create group.
   *
   * @param group the group draft
   * @return the group
   */
  public Group save(Group group) {
    logger.debug("Enter. group: {}.", group);

    Group savedGroup = groupRepository.save(group);

    logger.debug("Exit. savedGroup: {}.", savedGroup);
    return savedGroup;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   */
  public void delete(String groupId) {
    logger.debug("Enter. groupId: {}.", groupId);

    groupRepository.delete(groupId);
    removeFromParentGroup(groupId);

    logger.debug("Exit.");
  }

  /**
   * Find a group.
   *
   * @param groupId the group id
   * @return the group
   */
  public Group findOne(String groupId) {
    logger.debug("Enter. groupId: {}.", groupId);

    Group result = groupRepository.findOne(groupId);
    if (result == null) {
      logger.debug("Can not null group by id: {}.", groupId);
      throw new NotExistException("Group not exist");
    }

    logger.debug("Find group: {}.", result);
    logger.debug("Exit.");

    return result;
  }

  /**
   * Find parent group.
   *
   * @param groupId the group id
   * @return the group
   */
  public Group findParentGroup(String groupId) {
    logger.debug("Enter. groupId: {}.", groupId);

    Group result = groupRepository.findByChildrenId(groupId);

    logger.trace("Parent group: {}.", result);
    logger.debug("Exit.");
    return result;
  }

  /**
   * Gets sub group.
   *
   * @param groupId the group id
   * @return the sub group
   */
  public List<Group> getSubGroup(String groupId) {
    logger.debug("Enter. groupId: {}.", groupId);

    List<Group> result = groupRepository.findByParent(groupId);

    logger.trace("subGroup: {}.", result);
    logger.debug("Exit. subGroup count: {}.", result.size());

    return result;
  }

  /**
   * Find all group list.
   *
   * @param developerId the developer id
   * @return the list
   */
  public List<Group> findAllGroup(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Group> groups = groupRepository.findByDeveloperId(developerId);

    logger.debug("Exit. group count: {}.", groups.size());

    return groups;
  }

  /**
   * Remove group id from parent group.
   *
   * @param groupId the group id
   */
  private void removeFromParentGroup(String groupId) {
    Group parent = groupRepository.findByChildrenId(groupId);

    Predicate<String> predicate = string -> string.equals(groupId);
    parent.getChildrenId().removeIf(predicate);

    groupRepository.save(parent);
  }
}
