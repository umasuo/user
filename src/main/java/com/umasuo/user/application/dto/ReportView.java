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
  private int increaseNumber;

  /**
   * The Online number.
   */
  private int activeNumber;

  /**
   * The Total number.
   */
  private int totalNumber;
}
