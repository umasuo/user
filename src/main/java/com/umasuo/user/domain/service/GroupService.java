package com.umasuo.user.domain.service;

import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.mapper.GroupMapper;
import com.umasuo.user.domain.model.Group;
import com.umasuo.user.infrastructure.repository.GroupRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
   * Save group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  public Group save(GroupDraft groupDraft) {
    LOG.debug("Enter. groupDraft: {}.", groupDraft);
    Group parent = groupRepository.findOne(groupDraft.getParentId());
    Assert.notNull(parent, "Parent Can not be null");

    Group group = GroupMapper.toEntity(groupDraft, parent);
    Group savedGroup = groupRepository.save(group);

    LOG.debug("Exit. groupId: {}.", savedGroup.getId());

    return savedGroup;
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
}
