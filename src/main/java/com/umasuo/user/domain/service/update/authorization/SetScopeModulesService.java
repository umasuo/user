package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.SET_SCOPE_MODULES;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.SetScopeModules;
import com.umasuo.user.domain.model.Module;
import com.umasuo.user.domain.model.Scope;
import com.umasuo.user.domain.service.ModuleService;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Set scope modules.
 */
@Service(SET_SCOPE_MODULES)
public class SetScopeModulesService implements Updater<Scope, UpdateAction> {

  /**
   * Module service.
   */
  @Autowired
  private transient ModuleService moduleService;

  /**
   * Set scope's modules.
   *
   * @param scope        scope
   * @param updateAction Action
   */
  @Override
  public void handle(Scope scope, UpdateAction updateAction) {
    SetScopeModules setScopeModules = (SetScopeModules) updateAction;
    List<Module> modules = moduleService.getListById(setScopeModules.getModules());
    scope.setModules(modules);
    // TODO update the login admin's scope
  }
}
