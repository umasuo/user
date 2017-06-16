package com.umasuo.user.infrastructure.util;

import com.google.common.collect.Lists;
import com.umasuo.user.application.dto.ReportView;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/16.
 */
public final class ReportUtils {

  /**
   * Instantiates a new Report utils.
   */
  private ReportUtils() {
  }

  /**
   * Merge report.
   *
   * @param totalReports the total reports
   * @param registerReports the register reports
   * @return the list
   */
  public static List<ReportView> mergeReport(List<HashMap> totalReports,
      List<HashMap> registerReports) {
    List<ReportView> result = Lists.newArrayList();

    Consumer<HashMap> totalConsumer = map -> handleTotalReport(result, map);

    totalReports.forEach(totalConsumer);

    Consumer<HashMap> registerConsumer = map -> handleRegisterReport(result, map);

    registerReports.forEach(registerConsumer);

    return result;
  }

  private static void handleRegisterReport(List<ReportView> result, HashMap map) {
    Consumer<ReportView> consumer = ReportView -> {
      if (ReportView.getDeveloperId().equals(map.get("developerId").toString())) {
        ReportView.setRegisterNumber(Integer.valueOf(map.get("registerCount").toString()));
      }
    };

    result.stream().forEach(consumer);
  }

  private static void handleTotalReport(List<ReportView> result, HashMap map) {
    ReportView reportView = new ReportView();

    reportView.setDeveloperId(map.get("developerId").toString());
    reportView.setTotalNumber(Integer.valueOf(map.get("totalCount").toString()));

    // TODO: 17/6/16 
    reportView.setOnlineNumber(0);

    result.add(reportView);
  }
}
