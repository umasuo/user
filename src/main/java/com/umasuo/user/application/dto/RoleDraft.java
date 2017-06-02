package com.umasuo.user.application.dto;

import lombok.Data;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Role View.
 */
@Data
public class RoleDraft {

  /**
   * Role name.
   */
  @NotNull
  private String roleName;

  /**
   * List of scope ids.
   */
  private List<String> scopes;
}
