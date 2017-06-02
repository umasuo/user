package com.umasuo.user.application.dto.mapper;


import com.umasuo.user.application.dto.ScopeView;
import com.umasuo.user.domain.model.Scope;
import com.umasuo.user.infrastructure.enums.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Scope mapper.
 */
public final class ScopeMapper {

  /**
   * Constructor.
   */
  private ScopeMapper() {
    // do nothing
  }

  /**
   * Convert scope draft to entity.
   *
   * @param draft ScopeDraft
   */
  public static Scope toEntity(ScopeDraft draft) {
    Scope scope = new Scope();
    scope.setScopeName(draft.getScopeName());
    return scope;
  }

  /**
   * Convert entity to view.
   *
   * @param scope Scope
   * @return ScopeView
   */
  public static ScopeView toModel(Scope scope) {
    ScopeView view = new ScopeView();
    view.setId(scope.getId());
    view.setCreatedAt(scope.getCreatedAt());
    view.setLastModifiedAt(scope.getLastModifiedAt());
    view.setVersion(scope.getVersion());
    view.setScopeName(scope.getScopeName());
    view.setModuleViews(ModuleMapper.toModel(scope.getModules()));
    List<Permission> permissions = new ArrayList<>();
    permissions.addAll(scope.getPermissions());
    view.setPermissions(permissions);

    return view;
  }

  /**
   * Convert list of scope to list of scope view.
   *
   * @param scopes scope list
   * @return scope view list
   */
  public static List<ScopeView> toModel(List<Scope> scopes) {
    List<ScopeView> scopeViews = new ArrayList<>();
    scopes.stream().forEach(
        scope -> scopeViews.add(toModel(scope))
    );
    return scopeViews;
  }
}
