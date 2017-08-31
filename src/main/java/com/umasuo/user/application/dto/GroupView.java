package com.umasuo.user.application.dto;

import lombok.Data;

import java.util.List;

/**
 * View model for group.
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

  /**
   * owner id.
   */
  private String ownerId;

  /**
   * The id for managers.
   */
  private List<String> managers;

  /**
   * The id for users.
   */
  private List<String> users;

  /**
   * The parent id.
   */
  private String parentId;

  /**
   * The children id.
   */
  private List<String> childrenId;
}
