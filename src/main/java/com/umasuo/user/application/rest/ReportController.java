package com.umasuo.user.application.rest;

import com.umasuo.user.application.dto.ReportView;
import com.umasuo.user.application.service.ReportApplication;
import com.umasuo.user.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/6/16.
 */
@RestController
public class ReportController {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(ReportController.class);


  @Autowired
  private transient ReportApplication reportApplication;

  /**
   * get user report for developers all.
   * 内部接口.
   *
   * @param startTime the start time
   * @param endTime   the end time
   * @return the report by time
   */
  @GetMapping(value = Router.REPORT_ROOT, params = {"startTime", "endTime"})
  public List<ReportView> getPeriodReport(@RequestParam("startTime") long startTime,
                                          @RequestParam("endTime") long endTime) {
    logger.info("Enter. startTime: {}, endTime: {}.", startTime, endTime);

    List<ReportView> reports = reportApplication.getPeriodReport(startTime, endTime);

    logger.info("Exit. report size: {}.", reports.size());

    return reports;
  }

  /**
   * Gets one developer's report by time.
   *
   * @param developerId the developer id
   * @param startTime   the start time
   * @return the report by time
   */
  @GetMapping(value = Router.REPORT_ROOT, params = {"startTime"}, headers = {"developerId"})
  public ReportView getDeveloperReport(@RequestHeader String developerId,
                                       @RequestParam("startTime") long startTime,
                                       @RequestParam("endTime") long endTime) {
    logger.info("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    ReportView report = reportApplication.getDeveloperReportByTime(developerId, startTime, endTime);

    logger.info("Exit. report: {}.", report);

    return report;
  }
}
