package com.umasuo.user.domain.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

/**
 * Service for group.
 */
@Service
public class GroupService {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

  /**
   * The Group repository.
   */
  @Autowired
  private transient GroupRepository groupRepository;

  /**
   * Create group.
   *
   * @param group the group draft
   * @return the group
   */
  public Group save(Group group) {
    LOGGER.debug("Enter. group: {}.", group);

    Group savedGroup = groupRepository.save(group);

    LOGGER.debug("Exit. savedGroup: {}.", savedGroup);
    return savedGroup;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   */
  public void delete(String groupId) {
    LOGGER.debug("Enter. groupId: {}.", groupId);

    groupRepository.delete(groupId);
    removeFromParentGroup(groupId);

    LOGGER.debug("Exit.");
  }

  /**
   * Find a group.
   *
   * @param groupId the group id
   * @return the group
   */
  public Group findOne(String groupId) {
    LOGGER.debug("Enter. groupId: {}.", groupId);

    Group result = groupRepository.findOne(groupId);
    if (result == null) {
      LOGGER.debug("Can not null group by id: {}.", groupId);
      throw new NotExistException("Group not exist");
    }

    LOGGER.debug("Find group: {}.", result);
    LOGGER.debug("Exit.");

    return result;
  }

  /**
   * Find parent group.
   *
   * @param groupId the group id
   * @return the group
   */
  public Group findParentGroup(String groupId) {
    LOGGER.debug("Enter. groupId: {}.", groupId);

    Group result = groupRepository.findByChildrenId(groupId);

    LOGGER.trace("Parent group: {}.", result);
    LOGGER.debug("Exit.");
    return result;
  }

  /**
   * Gets sub group.
   *
   * @param groupId the group id
   * @return the sub group
   */
  public List<Group> getSubGroup(String groupId) {
    LOGGER.debug("Enter. groupId: {}.", groupId);

    List<Group> result = groupRepository.findByParent(groupId);

    LOGGER.trace("subGroup: {}.", result);
    LOGGER.debug("Exit. subGroup count: {}.", result.size());

    return result;
  }

  /**
   * Find all group list.
   *
   * @param developerId the developer id
   * @return the list
   */
  public List<Group> findAllGroup(String developerId) {
    LOGGER.debug("Enter. developerId: {}.", developerId);

    List<Group> groups = groupRepository.findByDeveloperId(developerId);

    LOGGER.debug("Exit. group count: {}.", groups.size());

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
