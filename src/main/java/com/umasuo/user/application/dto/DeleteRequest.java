package com.umasuo.user.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Delete request.
 *
 * Created by Davis on 17/5/27.
 */
@Data
public class DeleteRequest {

  /**
   * version.
   */
  @NotNull
  private Integer version;
}
