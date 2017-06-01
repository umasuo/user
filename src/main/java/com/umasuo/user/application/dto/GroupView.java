package com.umasuo.user.application.dto;

import lombok.Data;

/**
 * View model for group.
 * Created by Davis on 17/5/27.
 */
@Data
public class GroupView {

  /**
   * The id for group.
   */
  private String id;

  /**
   * The name for group.
   */
  private String name;

  /**
   * The id for developer.
   */
  private String developerId;
}
