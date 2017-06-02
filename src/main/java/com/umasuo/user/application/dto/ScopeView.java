package com.umasuo.user.application.dto;

import com.umasuo.user.infrastructure.enums.Permission;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Scope View.
 */
@Data
public class ScopeView implements Serializable{

  /**
   * Auto generated.
   */
  private static final long serialVersionUID = 5813425169221538791L;


  /**
   * Uuid.
   */
  private String id;

  /**
   * The Created at.
   */
  private ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  private ZonedDateTime lastModifiedAt;

  /**
   * Version.
   */
  private Integer version;

  /**
   * Scope name.
   */
  private String scopeName;

  /**
   * List of permissions for all modules contains in this scope.
   */
  private List<Permission> permissions;

  /**
   * List of moduleViews.
   */
  private List<ModuleView> moduleViews;
}
