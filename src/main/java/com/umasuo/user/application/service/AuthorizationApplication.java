package com.umasuo.user.application.service;

import com.umasuo.user.application.dto.AuthorizationDraft;
import com.umasuo.user.application.dto.AuthorizationView;
import com.umasuo.user.application.dto.mapper.AuthorizationMapper;
import com.umasuo.user.domain.model.Authorization;
import com.umasuo.user.domain.service.AuthorizationService;
import com.umasuo.user.infrastructure.update.AuthorizationUpdaterService;
import com.umasuo.user.infrastructure.update.UpdateAction;
import com.umasuo.user.infrastructure.validator.VersionValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Admin application.
 */
@Service
public class AuthorizationApplication {

  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AuthorizationApplication.class);

  /**
   * Admin service.
   */
  @Autowired
  private transient AuthorizationService authorizationService;

  /**
   * The Updater service.
   */
  @Autowired
  private transient AuthorizationUpdaterService updaterService;

  /**
   * Create admin from draft.
   *
   * @param draft AdminDraft
   * @return AdminView authorization view
   */
  public AuthorizationView create(AuthorizationDraft draft) {
    LOG.debug("Enter. draft: {}.", draft);

    // TODO: 17/6/3 check if all role exist.

    Authorization authorization = AuthorizationMapper.toEntity(draft);

    Authorization savedAuthorization = authorizationService.save(authorization);

    AuthorizationView result = AuthorizationMapper.toModel(savedAuthorization);

    LOG.debug("Exit. authorizationView: {}.", result);
    return result;
  }

  /**
   * Update authorization view.
   *
   * @param authorizationId the authorization id
   * @param version the version
   * @param actions the actions
   * @return the authorization view
   */
  public AuthorizationView update(String authorizationId,
      Integer version, List<UpdateAction> actions) {
    LOG.debug("Enter. authorizationId: {}, version: {}, actions: {}.",
        authorizationId, version, actions);

    Authorization group = authorizationService.getById(authorizationId);
    VersionValidator.validate(group.getVersion(), version);

    Authorization updatedGroup = updateAuthorizationEntity(actions, group);

    AuthorizationView result = AuthorizationMapper.toModel(updatedGroup);

    LOG.trace("Updated authorization: {}.", result);
    LOG.debug("Exit. authorizationId: {}.", authorizationId);
    return result;
  }

  /**
   * Get AdminView by id.
   *
   * @param id String
   * @return AdminView by id
   */
  public AuthorizationView getById(String id) {
    LOG.debug("Enter. id: {}.", id);

    Authorization admin = authorizationService.getById(id);
    AuthorizationView result = AuthorizationMapper.toModel(admin);

    LOG.debug("Enter. adminView: {}.", result);
    return result;
  }

  /**
   * Get all admins.
   *
   * @param developerId the developer id
   * @return List of AuthorizationView
   */
  public List<AuthorizationView> getAll(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    List<Authorization> admins = authorizationService.getAll(developerId);

    List<AuthorizationView> views = AuthorizationMapper.toModel(admins);

    LOG.trace("AuthorizationViewList: {}.", views);
    LOG.debug("Enter. authorizationView size: {}.", views.size());
    return views;
  }

  /**
   * Update group entity.
   *
   * @param actions update actions
   * @param entity Group entity
   * @return updated group entity.
   */
  @Transactional
  private Authorization updateAuthorizationEntity(List<UpdateAction> actions,
      Authorization entity) {
    actions.parallelStream().forEach(action -> updaterService.handle(entity, action));

    return authorizationService.save(entity);
  }
}

