package com.umasuo.user.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Authorization draft.
 *
 * Created by Davis on 17/6/3.
 */
@Getter
@Setter
@ToString
public class AuthorizationDraft {

  /**
   * Group id or user id.
   */
  @NotNull(message = "owner id can not be null")
  private String ownerId;

  /**
   * Developer id.
   */
  @NotNull(message = "developer id can not be null")
  private String developerId;

  /**
   * The Role id.
   */
  private List<String> roleId;
}
