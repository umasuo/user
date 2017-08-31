package com.umasuo.user.application.dto;

import lombok.Data;

/**
 * Report view.
 */
@Data
public class ReportView {

  /**
   * The developer id.
   */
  private String developerId;

  /**
   * The RegisterInfo number.
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
