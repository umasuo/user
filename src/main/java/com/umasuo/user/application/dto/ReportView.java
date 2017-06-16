package com.umasuo.user.application.dto;

import lombok.Data;

/**
 * Created by Davis on 17/6/16.
 */
@Data
public class ReportView {

  /**
   * The developer id.
   */
  private String developerId;

  /**
   * The Register number.
   */
  private Integer registerNumber;

  /**
   * The Online number.
   */
  private Integer onlineNumber;

  /**
   * The Total number.
   */
  private Integer totalNumber;
}
