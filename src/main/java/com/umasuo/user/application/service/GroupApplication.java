package com.umasuo.user.application.service;

import com.umasuo.exception.ConflictException;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;
import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.GroupView;
import com.umasuo.user.application.dto.mapper.GroupMapper;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.domain.service.GroupService;
import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.update.UpdaterService;
import com.umasuo.user.infrastructure.validator.VersionValidator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Group application.
 *
 * Created by Davis on 17/5/27.
 */
@Service
public class GroupApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(GroupApplication.class);

  /**
   * The Group service.
   */
  @Autowired
  private GroupService groupService;

  /**
   * Update service.
   */
  private transient UpdaterService updateService;

  /**
   * Create group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  @Transactional
  public GroupView create(GroupDraft groupDraft) {
    LOG.info("Enter. groupDraft: {}.", groupDraft);

    List<Group> groups = groupService.findAllGroup(groupDraft.getDeveloperId());

    Group createdGroup = null;

    if (groups == null || groups.isEmpty()) {
      if (StringUtils.isNotBlank(groupDraft.getParentId())) {
        LOG.debug("Basic group is not exist.");
        throw new ParametersException("Basic group is not exist");
      }
      createdGroup = groupService.createBasic(groupDraft);
    } else {
      createdGroup = groupService.create(groupDraft);
    }

    GroupView result = GroupMapper.toModel(createdGroup);

    LOG.debug("Exit.");

    return result;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   * @param version the version
   */
  public void delete(String groupId, Integer version) {
    LOG.info("Enter. groupId: {}, version: {}.", groupId, version);

    Group group = groupService.findOne(groupId);

    if (group == null) {
      LOG.debug("Can not null group by id: {}.", groupId);
      throw new NotExistException("Group not exist");
    }

    VersionValidator.validate(group, version);

    if (group.getOrganization().getUsers() != null &&
        !group.getOrganization().getUsers().isEmpty()) {
      LOG.debug("Can not delete group when there is {} users.",
          group.getOrganization().getUsers().size());
      throw new ConflictException("Users is not null");
    }

    if (group.getChildrenId() != null && !group.getChildrenId().isEmpty()) {
      LOG.debug("Can not delete group when there is {} sub groups.",
          group.getChildrenId().size());
      throw new ConflictException("Sub groups is not null");
    }

    groupService.delete(groupId);

    LOG.info("Exit");
  }

  /**
   * Update group.
   *
   * @param id the id
   * @param version the update request
   * @param actions the update action
   * @return the group
   */
  public GroupView updateGroup(String id, Integer version, List<UpdateAction> actions) {
    LOG.debug("Enter. groupId: {}, version: {}, actions: {}.", id, version, actions);

    Group group = groupService.findOne(id);
    VersionValidator.validate(group, version);

    Group updatedGroup = updateCategoryEntity(actions, group);

    GroupView result = GroupMapper.toModel(updatedGroup);

    LOG.trace("Updated category: {}.", result);
    LOG.debug("Exit. CategoryId: {}.", id);
    return result;
  }

  /**
   * Update group entity.
   *
   * @param actions update actions
   * @param entity Group entity
   * @return updated group entity.
   */
  @Transactional
  public Group updateCategoryEntity(List<UpdateAction> actions, Group entity) {
    actions.parallelStream().forEach(action -> updateService.handle(entity, action));

    return groupService.saveGroupEntity(entity);
  }

  /**
   * Find one group.
   *
   * @param groupId the group id
   * @return the group view
   */
  public GroupView findOne(String groupId) {
    LOG.info("Enter. groupId: {}.", groupId);

    Group group = groupService.findOne(groupId);

    if (group == null) {
      LOG.debug("Can not find group: {}.", groupId);
      throw new NotExistException("Group not exist");
    }

    GroupView result = GroupMapper.toModel(group);

    LOG.info("Exit.");

    return result;
  }

  /**
   * Find all group by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  public List<GroupView> findAll(String developerId) {
    LOG.info("Enter. developerId: {}.", developerId);

    List<Group> groups = groupService.findAllGroup(developerId);

    List<GroupView> result = GroupMapper.toModel(groups);

    LOG.info("Exit.");

    return result;
  }
}
