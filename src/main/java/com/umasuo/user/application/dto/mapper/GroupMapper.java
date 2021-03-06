package com.umasuo.user.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.application.dto.GroupView;
import com.umasuo.user.domain.model.Group;

import java.util.List;
import java.util.function.Consumer;

/**
 * Mapper for group.
 * Created by Davis on 17/5/27.
 */
public final class GroupMapper {

  /**
   * Instantiates a new Group mapper.
   */
  private GroupMapper() {
  }

  /**
   * Convert to Group entity.
   *
   * @param draft the draft
   * @return the group
   */
  public static Group toEntity(GroupDraft draft, String userId, String developerId) {
    Group entity = new Group();

    entity.setParent(draft.getParentId());
    entity.setName(draft.getName());
    entity.setOwnerId(userId);
    entity.setDeveloperId(developerId);

    return entity;
  }

  /**
   * Convert to GroupView.
   *
   * @param entity the entity
   * @return the group view
   */
  public static GroupView toModel(Group entity) {

    GroupView model = new GroupView();

    model.setId(entity.getId());
    model.setDeveloperId(entity.getDeveloperId());
    model.setName(entity.getName());
    model.setOwnerId(entity.getOwnerId());
    model.setManagers(entity.getManagers());
    model.setUsers(entity.getUsers());
    model.setChildrenId(entity.getChildrenId());
    model.setParentId(entity.getParent());

    return model;
  }

  /**
   * To model list.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<GroupView> toModel(List<Group> entities) {

    List<GroupView> result = Lists.newArrayList();
    Consumer<Group> consumer = group -> result.add(toModel(group));
    entities.stream().forEach(consumer);

    return result;
  }
}
