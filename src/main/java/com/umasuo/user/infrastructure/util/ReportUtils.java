package com.umasuo.user.infrastructure.util;

import com.google.common.collect.Lists;
import com.umasuo.user.application.dto.ReportView;

import java.util.List;
import java.util.Map;

/**
 * Report utils.
 */
public final class ReportUtils {

  /**
   * Private default constructor.
   */
  private ReportUtils() {
  }


  /**
   * Build report view.
   *
   * @param totalReport the total report
   * @return the report view
   */
  public static ReportView build(Map totalReport) {
    ReportView result = new ReportView();

    result.setDeveloperId(totalReport.get("developerId").toString());
    result.setTotalNumber(Integer.parseInt(totalReport.get("totalCount").toString()));

    return result;
  }

  /**
   * Merge for developer report view.
   *
   * @param report          the ReportView
   * @param registerReports the register reports
   * @return the report view
   */
  public static ReportView mergeRegisterReport(ReportView report, Map registerReports) {

    report.setIncreaseNumber(Integer.parseInt(registerReports.get("registerCount").toString()));

    return report;
  }

  /**
   * Merge report.
   *
   * @param totalReports    the total reports
   * @param registerReports the register reports
   * @return the list
   */
  public static List<ReportView> mergeReport(List<Map> totalReports,
                                             List<Map> registerReports) {
    List<ReportView> result = Lists.newArrayList();

    totalReports.forEach(map -> handleTotalReport(result, map));

    registerReports.forEach(map -> handleRegisterReport(result, map));

    return result;
  }

  /**
   * Handle register report.
   *
   * @param result
   * @param map
   */
  private static void handleRegisterReport(List<ReportView> result, Map map) {

    result.stream().forEach(
      ReportView -> {
        if (ReportView.getDeveloperId().equals(map.get("developerId").toString())) {
          ReportView.setIncreaseNumber(Integer.parseInt(map.get("increaseCount").toString()));
        }
      });
  }

  /**
   * Handle total report.
   *
   * @param result
   * @param map
   */
  private static void handleTotalReport(List<ReportView> result, Map map) {
    ReportView reportView = new ReportView();

    reportView.setDeveloperId(map.get("developerId").toString());
    reportView.setTotalNumber(Integer.parseInt(map.get("totalCount").toString()));

    result.add(reportView);
  }
}
