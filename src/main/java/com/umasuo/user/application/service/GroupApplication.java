package com.umasuo.user.application.service;

import com.umasuo.exception.ConflictException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.GroupView;
import com.umasuo.user.application.dto.mapper.GroupMapper;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.domain.service.GroupService;
import com.umasuo.user.infrastructure.update.GroupUpdaterService;
import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.validator.VersionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Group application.
 * <p>
 * Created by Davis on 17/5/27.
 */
@Service
public class GroupApplication {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(GroupApplication.class);

  /**
   * The Group service.
   */
  @Autowired
  private GroupService groupService;

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
  @Transactional
  public GroupView create(GroupDraft groupDraft, String userId, String developerId) {
    logger.debug("Enter. groupDraft: {}.", groupDraft);

    Group createdGroup = GroupMapper.toEntity(groupDraft, userId, developerId);
    Group savedGroup = groupService.save(createdGroup);

    //改parent的子类
    String parentId = groupDraft.getParentId();
    if (parentId != null) {
      Group parentGroup = groupService.findParentGroup(parentId);
      if (parentGroup == null) {
        throw new NotExistException("Parent group not exist.");
      }
      parentGroup.getChildrenId().add(savedGroup.getId());
      groupService.save(parentGroup);
    }

    GroupView result = GroupMapper.toModel(savedGroup);

    logger.debug("Exit. groupView: {}.", result);
    return result;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   * @param version the version
   */
  public void delete(String groupId, Integer version) {
    logger.debug("Enter. groupId: {}, version: {}.", groupId, version);

    Group group = groupService.findOne(groupId);

    VersionValidator.validate(group.getVersion(), version);

    if (group.getUsers() != null &&
        !group.getUsers().isEmpty()) {
      logger.debug("Can not delete group when there is {} users.",
          group.getUsers().size());
      throw new ConflictException("Users is not null");
    }

    if (group.getChildrenId() != null && !group.getChildrenId().isEmpty()) {
      logger.debug("Can not delete group when there is {} sub groups.",
          group.getChildrenId().size());
      throw new ConflictException("Sub groups is not null");
    }

    groupService.delete(groupId);

    logger.debug("Exit");
  }

  /**
   * Update group.
   *
   * @param id      the id
   * @param version the update request
   * @param actions the update action
   * @return the group
   */
  public GroupView updateGroup(String id, Integer version, List<UpdateAction> actions) {
    logger.debug("Enter. groupId: {}, version: {}, actions: {}.", id, version, actions);

    Group group = groupService.findOne(id);
    VersionValidator.validate(group.getVersion(), version);

    Group updatedGroup = updateEntity(actions, group);

    GroupView result = GroupMapper.toModel(updatedGroup);

    logger.trace("Updated category: {}.", result);
    logger.debug("Exit. CategoryId: {}.", id);
    return result;
  }

  /**
   * Update group entity.
   *
   * @param actions update actions
   * @param entity  Group entity
   * @return updated group entity.
   */
  @Transactional
  public Group updateEntity(List<UpdateAction> actions, Group entity) {
    actions.parallelStream().forEach(action -> updateService.handle(entity, action));

    return groupService.save(entity);
  }

  /**
   * Find one group.
   *
   * @param groupId the group id
   * @return the group view
   */
  public GroupView findOne(String groupId) {
    logger.debug("Enter. groupId: {}.", groupId);

    Group group = groupService.findOne(groupId);

    GroupView result = GroupMapper.toModel(group);

    logger.debug("Exit. groupView: {}.", result);

    return result;
  }

  /**
   * Find all group by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  public List<GroupView> findAll(String developerId) {
    logger.debug("Enter. developerId: {}.", developerId);

    List<Group> groups = groupService.findAllGroup(developerId);

    List<GroupView> result = GroupMapper.toModel(groups);

    logger.debug("Exit.");

    return result;
  }
}
