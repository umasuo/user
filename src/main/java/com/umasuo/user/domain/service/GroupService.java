package com.umasuo.user.domain.service;

import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.mapper.GroupMapper;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.repository.GroupRepository;
import com.umasuo.user.infrastructure.update.GroupUpdaterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
  private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

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
   * @param groupDraft the group draft
   * @return the group
   */
  public Group create(GroupDraft groupDraft) {
    LOG.debug("Enter. groupDraft: {}.", groupDraft);

    Group parent = groupRepository.findOne(groupDraft.getParentId());
    Assert.notNull(parent, "Parent Can not be null");

    Group savedGroup = saveGroup(groupDraft);

    parent.getChildrenId().add(savedGroup.getId());
    groupRepository.save(parent);

    LOG.debug("Exit. groupId: {}.", savedGroup.getId());

    return savedGroup;
  }

  /**
   * Create basic group.
   *
   * @param groupDraft the GroupDraft.
   * @return the group
   */
  public Group createBasic(GroupDraft groupDraft) {
    LOG.debug("Enter. groupDraft: {}.", groupDraft);

    Group savedGroup = saveGroup(groupDraft);

    LOG.debug("Exit. groupId: {}.", savedGroup.getId());

    return savedGroup;
  }

  /**
   * Save group entity.
   *
   * @param group the group entity
   * @return saved group entity
   */
  public Group saveGroupEntity(Group group) {
    return groupRepository.save(group);
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   */
  public void delete(String groupId) {
    LOG.debug("Enter. groupId: {}.", groupId);

    groupRepository.delete(groupId);
    removeFromParentGroup(groupId);

    LOG.debug("Exit.");
  }

  /**
   * Find a group.
   *
   * @param groupId the group id
   * @return the group
   */
  public Group findOne(String groupId) {
    LOG.debug("Enter. groupId: {}.", groupId);

    Group result = groupRepository.findOne(groupId);

    LOG.debug("Find group: {}.", result);
    LOG.debug("Exit.");

    return result;
  }

  /**
   * Find parent group.
   *
   * @param groupId the group id
   * @return the group
   */
  public Group findParentGroup(String groupId) {
    LOG.debug("Enter. groupId: {}.", groupId);

    Group result = groupRepository.findByChildrenId(groupId);

    LOG.trace("Parent group: {}.", result);
    LOG.debug("Exit.");
    return result;
  }

  /**
   * Gets sub group.
   *
   * @param groupId the group id
   * @return the sub group
   */
  public List<Group> getSubGroup(String groupId) {
    LOG.debug("Enter. groupId: {}.", groupId);

    List<Group> result = groupRepository.findByParent(groupId);

    LOG.trace("subGroup: {}.", result);
    LOG.debug("Exit. subGroup count: {}.", result.size());

    return result;
  }

  /**
   * Find all group list.
   *
   * @param developerId the developer id
   * @return the list
   */
  public List<Group> findAllGroup(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    List<Group> groups = groupRepository.findByDeveloperId(developerId);

    LOG.debug("Exit. group count: {}.", groups.size());

    return groups;
  }

  /**
   * Save group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  private Group saveGroup(GroupDraft groupDraft) {
    LOG.debug("Enter. groupDraft: {}.", groupDraft);

    Group group = GroupMapper.toEntity(groupDraft);
    Group result = saveGroupEntity(group);

    LOG.debug("Exit. groupId: {}.", result.getId());

    return result;
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
