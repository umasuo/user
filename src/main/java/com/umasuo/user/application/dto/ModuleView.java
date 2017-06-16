package com.umasuo.user.application.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Module view.
 */
@Data
public class ModuleView implements Serializable {

  /**
   * Auto generated.
   */
  private static final long serialVersionUID = -1802486871386834646L;

  /**
   * UUID.
   */
  private String id;

  /**
   * The Created at.
   */
  private Long createdAt;

  /**
   * The Last modified at.
   */
  private Long lastModifiedAt;

  /**
   * Version.
   */
  private Integer version;

  /**
   * Module name.
   */
  private String moduleName;

  /**
   * Path of this module.
   */
  private String path;
}
