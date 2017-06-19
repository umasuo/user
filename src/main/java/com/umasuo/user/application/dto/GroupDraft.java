package com.umasuo.user.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Draft for group.
 * Created by Davis on 17/5/27.
 */
@Getter
@Setter
@ToString
public class GroupDraft {

  /**
   * The Name.
   */
  @NotNull(message = "group name can not be null")
  @Size(min = 1, message = "group name can not be null")
  private String name;

  /**
   * The Parent id.
   */
  private String parentId;
}
