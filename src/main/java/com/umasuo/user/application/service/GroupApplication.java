package com.umasuo.user.application.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.GroupView;
import com.umasuo.user.application.dto.mapper.GroupMapper;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.domain.service.GroupService;
import com.umasuo.user.infrastructure.validator.VersionValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Group application.
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
   * Create group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  public GroupView create(GroupDraft groupDraft) {
    LOG.info("Enter. groupDraft: {}.", groupDraft);

    Group createGroup = groupService.create(groupDraft);

    GroupView result = GroupMapper.toModel(createGroup);

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

    VersionValidator.validate(group, version);

    groupService.delete(groupId);

    LOG.info("Exit");
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

    return null;
  }

  /**
   * Find all group.
   *
   * @return the list
   */
  public List<GroupView> findAll() {
    LOG.info("Enter.");

    // TODO: 17/5/27

    LOG.info("Exit.");

    return null;
  }
}
