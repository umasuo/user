package com.umasuo.user.infrastructure.update;

import com.umasuo.model.Updater;
import com.umasuo.user.domain.model.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * The type Authorization updater service.
 *
 * Created by Davis on 17/6/3.
 */
@Service
public class AuthorizationUpdaterService implements Updater<Authorization, UpdateAction>{

  /**
   * ApplicationContext for get update services.
   */
  @Autowired
  private transient ApplicationContext context;

  /**
   * Put the value in action to entity.
   *
   * @param entity E
   * @param action UpdateAction
   */
  @Override
  public void handle(Authorization entity, UpdateAction action) {
    Updater updater = getUpdateService(action);
    updater.handle(entity, action);
  }

  /**
   * Get mapper.
   *
   * @param action UpdateAction
   * @return ZoneUpdateMapper
   */
  private Updater getUpdateService(UpdateAction action) {
    return (Updater) context.getBean(action.getActionName());
  }
}
