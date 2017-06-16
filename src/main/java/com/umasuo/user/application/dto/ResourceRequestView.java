package com.umasuo.user.application.dto;

import com.umasuo.user.infrastructure.enums.ReplyRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 */
@Setter
@Getter
@ToString
public class ResourceRequestView {
  /**
   * The request id.
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
   * Version used for update date check.
   */
  private Integer version;

  /**
   * The Acceptor id.
   */
  private String acceptorId;

  /**
   * The Acceptor user id.
   */
  private String acceptorUserId;

  /**
   * The Reply request.
   */
  private ReplyRequest replyRequest;

  /**
   * The Applicant id.
   */
  private String applicantId;

  /**
   * The Applicant user id.
   */
  private String applicantUserId;

  /**
   * The Applicant viewed.
   */
  private Boolean applicantViewed;

  /**
   * The Device id.
   */
  private String deviceId;

  /**
   * The References.
   */
  private List<Reference> references;
}
