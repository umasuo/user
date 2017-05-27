package com.umasuo.user.application.dto.mapper;

import com.umasuo.user.application.dto.GroupDraft;
import com.umasuo.user.domain.model.Group;

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
   * @param parent the parent
   * @return the group
   */
  public static Group toEntity(GroupDraft draft, Group parent) {
    Group entity = new Group();

    entity.setName(draft.getName());
    entity.setParent(parent);

    return entity;
  }
}
