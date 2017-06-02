package com.umasuo.user.application.rest;

import static com.umasuo.user.infrastructure.AuthorizationRouter.MODULE_ROOT;
import static com.umasuo.user.infrastructure.AuthorizationRouter.MODULE_WITH_ID;

import com.umasuo.user.application.dto.ModuleView;
import com.umasuo.user.application.dto.mapper.ModuleDraft;
import com.umasuo.user.application.service.ModuleApplication;
import com.umasuo.user.infrastructure.update.UpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Module controller.
 */
@RestController
@CrossOrigin
public class ModuleController {

  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ModuleController.class);

  /**
   * Module application.
   */
  @Autowired
  private transient ModuleApplication moduleApplication;

  /**
   * Create a new module.
   *
   * @param draft draft
   * @return Module
   */
  @PostMapping(MODULE_ROOT)
  public ModuleView create(@RequestBody @Valid ModuleDraft draft) {
    LOG.info("Enter. moduleDraft: {}.", draft);

    ModuleView view = moduleApplication.create(draft);

    LOG.info("Exit. moduleView: {}.", view);
    return view;
  }

  /**
   * Get all modules.
   *
   * @return List of module
   */
  @GetMapping(MODULE_ROOT)
  public List<ModuleView> getAllModule() {
    LOG.info("Enter.");

    List<ModuleView> viewList = moduleApplication.getAll();

    LOG.info("Enter. viewList size: {}.", viewList.size());
    LOG.trace("ViewList: {}.", viewList);
    return viewList;
  }

  /**
   * Get module by id.
   *
   * @param id id
   * @return
   */
  @GetMapping(MODULE_WITH_ID)
  public ModuleView getModuleById(@PathVariable String id) {
    LOG.info("Enter. id: {}.", id);

    ModuleView view = moduleApplication.getById(id);

    LOG.info("Exit. moduleView: {}.", view);
    return view;
  }

  /**
   * Update a module.
   *
   * @param id            String
   * @param updateRequest updateRequest
   * @return ModuleView
   */
  @PutMapping(MODULE_WITH_ID)
  public ModuleView updateModule(@PathVariable String id,
                                 @RequestBody @Valid UpdateRequest updateRequest) {
    LOG.info("Enter. id: {}. updateRequest: {}.", id, updateRequest);

    ModuleView view = moduleApplication.update(id, updateRequest.getVersion(), updateRequest
        .getActions());

    LOG.info("Exit. moduleView: {}.", view);
    return view;
  }
}
