package com.umasuo.user.domain.service.update.authorization;

import static com.umasuo.user.infrastructure.util.AuthorizationActionUtils.UPDATE_MODULE;

import com.umasuo.model.Updater;
import com.umasuo.user.application.dto.action.authorization.UpdateModule;
import com.umasuo.user.domain.model.Module;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.springframework.stereotype.Service;

/**
 * Update module service.
 */
@Service(UPDATE_MODULE)
public class UpdateModuleService implements Updater<Module, UpdateAction> {

  /**
   * Update module.
   *
   * @param module       Module
   * @param updateAction UpdateAction
   */
  @Override
  public void handle(Module module, UpdateAction updateAction) {
    UpdateModule updateModule = (UpdateModule) updateAction;
    module.setPath(updateModule.getPath());
    module.setModuleName(updateModule.getModuleName());
    // TODO update the login admin's scope
  }
}
