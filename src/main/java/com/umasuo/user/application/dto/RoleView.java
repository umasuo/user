package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Role View.
 */
@Data
public class RoleView implements Serializable{

  /**
   * Auto generated.
   */
  private static final long serialVersionUID = -3232239596331705547L;

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
   * Role name.
   */
  private String roleName;

  /**
   * List of scopeViews.
   */
  private List<ScopeView> scopeViews;
}
