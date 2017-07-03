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
  private Integer increaseNumber;

  /**
   * The Online number.
   */
  private Integer activeNumber;

  /**
   * The Total number.
   */
  private Integer totalNumber;
}
