package com.umasuo.user.application.dto.mapper;

import com.umasuo.user.application.dto.ModuleView;
import com.umasuo.user.domain.model.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Module mapper, used to convert view to model or reverse.
 */
public final class ModuleMapper {

  /**
   * Private empty constructor.
   */
  private ModuleMapper() {
    // do nothing.
  }

  /**
   * Convert module draft to module.
   *
   * @return Module
   */
  public static Module toEntity(ModuleDraft draft) {
    Module module = new Module();
    module.setModuleName(draft.getModuleName());
    module.setPath(draft.getPath());
    return module;
  }

  /**
   * Convert module to view.
   *
   * @param module Module
   * @return ModuleView
   */
  public static ModuleView toModel(Module module) {
    ModuleView view = new ModuleView();
    view.setId(module.getId());
    view.setCreatedAt(module.getCreatedAt());
    view.setLastModifiedAt(module.getLastModifiedAt());
    view.setVersion(module.getVersion());
    view.setModuleName(module.getModuleName());
    view.setPath(module.getPath());
    return view;
  }

  /**
   * Convert a list of module view to view list.
   *
   * @param moduleList Module list
   * @return ModuleView list
   */
  public static List<ModuleView> toModel(List<Module> moduleList) {
    List<ModuleView> viewList = new ArrayList<>();
    moduleList.stream().forEach(
        module -> viewList.add(toModel(module))
    );
    return viewList;
  }
}
