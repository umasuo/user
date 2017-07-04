package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.GroupView;
import com.umasuo.user.application.service.GroupApplication;
import com.umasuo.user.infrastructure.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import static com.umasuo.user.infrastructure.Router.DEVELOPER_ID;
import static com.umasuo.user.infrastructure.Router.GROUP;
import static com.umasuo.user.infrastructure.Router.GROUP_ID;
import static com.umasuo.user.infrastructure.Router.GROUP_WITH_ID;

/**
 * Rest controller for group.
 * Created by Davis on 17/5/27.
 */
@RestController
public class GroupController {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(GroupController.class);

  /**
   * The Group application.
   */
  @Autowired
  private GroupApplication groupApplication;

  /**
   * Create group.
   *
   * @param groupDraft the group draft
   * @return the group
   */
  @PostMapping(GROUP)
  public GroupView create(@RequestBody @Valid GroupDraft groupDraft,
                          @RequestHeader String userId,
                          @RequestHeader String developerId) {
    logger.info("Enter. groupDraft: {}, userId: {}, developerId: {}.", groupDraft, userId,
        developerId);

    GroupView result = groupApplication.create(groupDraft, userId, developerId);

    logger.info("Exit. groupView: {}.", result);
    return result;
  }

  /**
   * Delete.
   *
   * @param groupId the group id
   * @param version the version
   */
  @DeleteMapping(GROUP_WITH_ID)
  public void delete(@PathVariable(GROUP_ID) String groupId,
                     @RequestParam("version") Integer version,
                     @RequestHeader String developerId) {
    logger.info("Enter. groupId: {}, version: {}, developerId: {}.", groupId, version, developerId);

    //todo developer id 没用，而且这个接口是给user用的，userID没有
    groupApplication.delete(groupId, version);

    logger.info("Exit");
  }


  /**
   * Update group.
   * todo user? developer? device?
   *
   * @param groupId       the group id
   * @param updateRequest the update request
   * @return the GroupView
   */
  @PutMapping(GROUP_WITH_ID)
  public GroupView update(@PathVariable(GROUP_ID) String groupId,
                          @RequestBody @Valid UpdateRequest updateRequest) {
    logger.info("Enter. groupId: {}, updateRequest: {}.", groupId, updateRequest);

    GroupView result = groupApplication
        .updateGroup(groupId, updateRequest.getVersion(), updateRequest.getActions());

    logger.info("Exit. groupView: {}.", result);
    return result;
  }

  /**
   * Find one group.
   *
   * @param groupId the group id
   * @return the group view
   */
  @GetMapping(GROUP_WITH_ID)
  public GroupView findOne(@PathVariable(GROUP_ID) String groupId) {
    logger.info("Enter. groupId: {}.", groupId);

    GroupView result = groupApplication.findOne(groupId);

    logger.info("Exit.");

    return result;
  }

  /**
   * Find all group.
   * todo user? developer? device?
   *
   * @return the list
   */
  @GetMapping(GROUP)
  public List<GroupView> findAll(@RequestParam(DEVELOPER_ID) String developerId) {
    logger.info("Enter. developerId: {}.", developerId);

    List<GroupView> result = groupApplication.findAll(developerId);

    logger.info("Exit.");

    return result;
  }
}
