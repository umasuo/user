package com.umasuo.user.application.service;

import com.umasuo.exception.ConflictException;
import com.umasuo.user.application.dto.RoleDraft;
import com.umasuo.user.application.dto.RoleView;
import com.umasuo.user.application.dto.mapper.RoleMapper;
import com.umasuo.user.domain.model.Role;
import com.umasuo.user.domain.model.Scope;
import com.umasuo.user.domain.service.RoleService;
import com.umasuo.user.domain.service.ScopeService;
import com.umasuo.user.infrastructure.update.RoleUpdaterService;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Role application.
 */
@Service
public class RoleApplication {
  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RoleApplication.class);

  /**
   * Scope service.
   */
  @Autowired
  private transient ScopeService scopeService;

  /**
   * Role service.
   */
  @Autowired
  private transient RoleService roleService;

  /**
   * Role updater service.
   */
  @Autowired
  private transient RoleUpdaterService roleUpdaterService;

  /**
   * Create Role from draft.
   *
   * @param draft RoleDraft
   * @return RoleView
   */
  public RoleView create(RoleDraft draft) {
    LOG.debug("Enter. draft: {}.", draft);

    Role role = RoleMapper.toEntity(draft);

    List<Scope> scopes = scopeService.getListById(draft.getScopes());
    role.setScopes(scopes);

    Role savedRole = roleService.save(role);

    RoleView roleView = RoleMapper.toModel(savedRole);

    LOG.debug("Exit. roleView: {}.", roleView);
    return roleView;
  }

  /**
   * Get RoleView by id.
   *
   * @param id String
   * @return RoleView
   */
  public RoleView getById(String id) {
    LOG.debug("Enter. id: {}.", id);

    Role role = roleService.getById(id);
    RoleView view = RoleMapper.toModel(role);

    LOG.debug("Enter. roleView: {}.", view);
    return view;
  }

  /**
   * Get all roleViews.
   *
   * @return List of roleViews
   */
  public List<RoleView> getAll() {
    LOG.debug("Enter.");

    List<Role> roles = roleService.getAll();
    List<RoleView> views = RoleMapper.toModel(roles);

    LOG.debug("Enter. roleView size: {}.", views.size());
    LOG.trace("RoleViewList: {}.", views);
    return views;
  }

  /**
   * Update a role
   *
   * @param id      String
   * @param version integer
   * @param actions action list
   * @return Role view
   */
  public RoleView update(String id, Integer version, List<UpdateAction> actions) {
    LOG.debug("Enter. id: {}, version: {}, actions: {}.", id, version, actions);

    Role valueInDb = roleService.getById(id);
    LOG.trace("Data in db: {}", valueInDb);
    checkVersion(version, valueInDb.getVersion());

    actions.stream().forEach(
        action -> roleUpdaterService.handle(valueInDb, action)
    );

    Role savedValue = roleService.save(valueInDb);
    RoleView view = RoleMapper.toModel(savedValue);

    LOG.debug("Exit. roleView: {}.", view);
    return view;
  }

  /**
   * Check the version.
   *
   * @param inputVersion Integer
   * @param existVersion Integer
   */
  private void checkVersion(Integer inputVersion, Integer existVersion) {
    if (!inputVersion.equals(existVersion)) {
      LOG.debug("Role version is not correct.");
      throw new ConflictException("Role version is not correct.");
    }
  }
}

