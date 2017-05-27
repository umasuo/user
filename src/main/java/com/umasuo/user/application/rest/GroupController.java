package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.DeleteRequest;
import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.GroupView;
import com.umasuo.user.application.service.GroupApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Rest controller for group.
 * Created by Davis on 17/5/27.
 */
@RestController
public class GroupController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(GroupController.class);

  /**
   * The Group application.
   */
  private GroupApplication groupApplication;

  /**
   * Create group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  public GroupView create(@RequestBody GroupDraft groupDraft) {
    LOG.info("Enter. groupDraft: {}.", groupDraft);

    GroupView result = groupApplication.create(groupDraft);

    LOG.debug("Exit.");
    return result;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   * @param request the request
   */
  public void delete(@PathVariable String groupId, @RequestBody @Valid DeleteRequest request) {
    LOG.info("Enter. groupId: {}, deleteRequest: {}.", groupId, request);

    groupApplication.delete(groupId, request.getVersion());

    LOG.info("Exit");
  }

  /**
   * Find one group.
   *
   * @param groupId the group id
   * @return the group view
   */
  public GroupView findOne(@PathVariable String groupId) {
    LOG.info("Enter. groupId: {}.", groupId);

    GroupView result = groupApplication.findOne(groupId);

    LOG.info("Exit.");

    return result;
  }

  /**
   * Find all group.
   *
   * @return the list
   */
  public List<GroupView> findAll() {
    LOG.info("Enter.");

    List<GroupView> result = groupApplication.findAll();

    LOG.info("Exit.");

    return result;
  }
}
