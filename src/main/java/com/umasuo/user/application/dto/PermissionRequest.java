package com.umasuo.user.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Davis on 17/6/8.
 */
@Getter
@Setter
@ToString
public class PermissionRequest {
  /**
   * The user id from applicant.
   */
  private String userId;

  /**
   * The Applicant id.
   */
  private String applicantId;

  /**
   * The Acceptor id.
   */
  private String acceptorId;

  /**
   * The Device definition id.
   */
  private String deviceDefinitionId;

  /**
   * The References.
   */
  private List<Reference> references;
}
