package com.umasuo.user.application.dto.mapper;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Scope draft used to save a new scope.
 */
@Data
public class ScopeDraft {

  /**
   * Scope name.
   */
  @NotNull
  private String scopeName;

  /**
   * List of permissions for all modules contains in this scope.
   */
  private List<String> permissions;

  /**
   * List of module ids.
   */
  private List<String> modules;
}
