package com.umasuo.user.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.user.application.dto.AuthorizationDraft;
import com.umasuo.user.application.dto.AuthorizationView;
import com.umasuo.user.domain.model.Authorization;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/3.
 */
public class AuthorizationMapper {

  /**
   * To entity authorization.
   *
   * @param draft the draft
   * @return the authorization
   */
  public static Authorization toEntity(AuthorizationDraft draft) {
    Authorization entity = new Authorization();

    entity.setDeveloperId(draft.getDeveloperId());
    entity.setOwnerId(draft.getOwnerId());
    entity.setRoles(draft.getRoleId());

    return entity;
  }

  /**
   * To model authorization view.
   *
   * @param entity the entity
   * @return the authorization view
   */
  public static AuthorizationView toModel(Authorization entity) {
    // TODO: 17/6/3
    return null;
  }


  /**
   * Convert list of Authorization to list of AuthorizationView.
   *
   * @param entities the entities
   * @return the list
   */
  public static List<AuthorizationView> toModel(List<Authorization> entities) {
    List<AuthorizationView> models = Lists.newArrayList();

    Consumer<Authorization> consumer = authorization -> models.add(toModel(authorization));

    entities.stream().forEach(consumer);

    return models;
  }
}
