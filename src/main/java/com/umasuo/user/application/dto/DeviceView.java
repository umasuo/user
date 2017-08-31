package com.umasuo.user.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Device view.
 */
@Getter
@Setter
@ToString
public class DeviceView {
  /**
   * Device id.
   */
  private String id;

  /**
   * The Created at.
   */
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * device definition id.
   */
  private String deviceDefineId;

  /**
   * 开发者自定义的设备ID.
   */
  private String customizedId;

  /**
   * Owner id.
   */
  private String ownerId;

  /**
   * Developer id.
   */
  private String developerId;
}
