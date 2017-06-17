package com.umasuo.user.application.service;

import com.umasuo.user.application.dto.ReportView;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.infrastructure.util.ReportUtils;
import com.umasuo.user.infrastructure.validator.TimeValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Davis on 17/6/16.
 */
@Service
public class ReportApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ReportApplication.class);

  /**
   * The Service.
   */
  @Autowired
  private transient DeveloperUserService service;

  /**
   * Gets period report.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @return the period report
   */
  public List<ReportView> getPeriodReport(long startTime, long endTime) {

    LOG.debug("Enter. startTime: {}, endTime: {}.", startTime, endTime);

    TimeValidator.validate(startTime, endTime);

    List<HashMap> totalReport = service.getAllReport();

    List<HashMap> registerReport = service.getRegisteredReport(startTime, endTime);

    List<ReportView> result = ReportUtils.mergeReport(totalReport, registerReport);

    // TODO: 17/6/16 get online number

    LOG.debug("Exit. report size: {}.", result.size());

    return result;
  }

  /**
   * Gets developer report by time.
   *
   * @param startTime the start time
   * @param developerId the developer id
   * @return the developer report by time
   */
  public ReportView getDeveloperReportByTime(long startTime, String developerId) {
    LOG.debug("Enter. startTime: {}, developerId: {}.", startTime, developerId);

    TimeValidator.validate(startTime);

    HashMap totalReport = service.getDeveloperAllReport(developerId);

    HashMap registerReport = service.getDeveloperRegisteredReport(developerId, startTime);

    ReportView result = null;
    if (totalReport == null || totalReport.isEmpty()) {
      LOG.debug("Can not find any user in developer: {} from time: {}.", developerId, startTime);
    } else {
      result = ReportUtils.build(totalReport);
      if (registerReport != null && !registerReport.isEmpty()) {
        result = ReportUtils.mergeRegisterReport(result, registerReport);
      }
    }

    // TODO: 17/6/16 get online number

    LOG.debug("Exit. report: {}.", result);

    return result;
  }
}
