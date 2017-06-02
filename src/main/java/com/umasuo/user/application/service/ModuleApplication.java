package com.umasuo.user.application.service;


import com.umasuo.exception.ConflictException;
import com.umasuo.user.application.dto.ModuleView;
import com.umasuo.user.application.dto.mapper.ModuleDraft;
import com.umasuo.user.application.dto.mapper.ModuleMapper;
import com.umasuo.user.domain.model.Module;
import com.umasuo.user.domain.service.ModuleService;
import com.umasuo.user.infrastructure.update.ModuleUpdaterService;
import com.umasuo.user.infrastructure.update.UpdateAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Module application.
 */
@Service
public class ModuleApplication {
  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ModuleApplication.class);

  /**
   * Module service.
   */
  @Autowired
  private transient ModuleService moduleService;

  /**
   * Updater service.
   */
  @Autowired
  private transient ModuleUpdaterService moduleUpdaterService;

  /**
   * Create Module.
   *
   * @param draft ModuleDraft
   * @return ModuleView
   */
  public ModuleView create(ModuleDraft draft) {
    LOG.debug("Enter. moduleDraft: {}.", draft);

    Module module = ModuleMapper.toEntity(draft);
    Module savedModule = moduleService.save(module);
    ModuleView result = ModuleMapper.toModel(savedModule);

    LOG.debug("Exit. moduleView: {}.", result);
    return result;
  }

  /**
   * Get module by id.
   *
   * @param id String
   * @return ModuleView
   */
  public ModuleView getById(String id) {
    LOG.debug("Enter. id: {}.", id);

    Module module = moduleService.getById(id);
    ModuleView view = ModuleMapper.toModel(module);

    LOG.debug("Exit. view: {}.", view);
    return view;
  }

  /**
   * Get all module view.
   *
   * @return List of module view
   */
  public List<ModuleView> getAll() {
    LOG.debug("Enter.");

    List<Module> moduleList = moduleService.getAll();
    LOG.trace("model list: module: {}.", moduleList);

    List<ModuleView> viewList = ModuleMapper.toModel(moduleList);

    LOG.debug("Exit. moduleView size: {}.", viewList.size());
    LOG.trace("View list: moduleView: {}.", viewList);
    return viewList;
  }

  /**
   * Update module with with update actions.
   *
   * @param id      id
   * @param version current version
   * @param actions update actions
   * @return ModuleView
   */
  public ModuleView update(String id, Integer version, List<UpdateAction> actions) {
    LOG.debug("Enter. id: {}, version: {}, actions: {}.", id, version, actions);

    Module valueInDb = moduleService.getById(id);
    LOG.trace("Data in db: {}", valueInDb);
    checkVersion(version, valueInDb.getVersion());

    actions.stream().forEach(
        action -> moduleUpdaterService.handle(valueInDb, action)
    );

    Module savedValue = moduleService.save(valueInDb);
    ModuleView view = ModuleMapper.toModel(savedValue);

    LOG.debug("Exit. updatedView: {}.", view);
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
      LOG.debug("Module version is not correct.");
      throw new ConflictException("Module version is not correct.");
    }
  }
}

