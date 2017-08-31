package com.umasuo.user.application.service;

import com.umasuo.user.application.dto.ReportView;
import com.umasuo.user.domain.service.DeveloperUserService;
import com.umasuo.user.infrastructure.util.RedisUtils;
import com.umasuo.user.infrastructure.util.ReportUtils;
import com.umasuo.user.infrastructure.validator.TimeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Report application.
 */
@Service
public class ReportApplication {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportApplication.class);

  /**
   * The Service.
   */
  @Autowired
  private transient DeveloperUserService service;
  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Gets period report.
   *
   * @param startTime the start time
   * @param endTime   the end time
   * @return the period report
   */
  public List<ReportView> getPeriodReport(long startTime, long endTime) {

    LOGGER.debug("Enter. startTime: {}, endTime: {}.", startTime, endTime);

    TimeValidator.validate(startTime, endTime);

    // 获取总量的数据，总量数据是只截止到当前这个小时为止总攻有多少用户注册.
    List<Map> totalReport = service.getTotalCountReport(endTime);

    //获取这个小时新增注册用户的数据
    List<Map> increaseReport = service.getIncreaseReport(startTime, endTime);

    //合并两个数据
    List<ReportView> result = ReportUtils.mergeReport(totalReport, increaseReport);

    //获取当前这个小时的登录了的用户数，每天以最后一小时的为准
    getOnlineCount(result);

    LOGGER.debug("Exit. report size: {}.", result.size());

    return result;
  }

  /**
   * Get online count.
   *
   * @param report
   */
  public void getOnlineCount(List<ReportView> report) {
    report.stream().forEach(
      reportView -> {
        String key = "*" + String.format(RedisUtils.USER_KEY_FORMAT, reportView.getDeveloperId(), "*");

//          reportView.setActiveNumber(redisTemplate.keys(key).size());
        reportView.setActiveNumber(redisTemplate.getConnectionFactory().getConnection()
          .keys(key.getBytes()).size());
      }
    );
  }

  /**
   * Gets developer report by time.
   *
   * @param startTime   the start time
   * @param developerId the developer id
   * @return the developer report by time
   */
  public ReportView getDeveloperReportByTime(String developerId, long startTime, long endTime) {
    LOGGER.debug("Enter. startTime: {}, developerId: {}.", startTime, developerId);

    TimeValidator.validate(startTime);

    Map totalReport = service.getTotalCountReport(developerId);

    Map registerReport = service.getIncreaseReport(developerId, startTime, endTime);

    ReportView result = null;
    if (totalReport == null || totalReport.isEmpty()) {
      LOGGER.debug("Can not find any user in developer: {} from time: {}.", developerId, startTime);
    } else {
      result = ReportUtils.build(totalReport);
      if (registerReport != null && !registerReport.isEmpty()) {
        result = ReportUtils.mergeRegisterReport(result, registerReport);
      }
    }

    // TODO: 17/6/16 get online number

    LOGGER.debug("Exit. report: {}.", result);

    return result;
  }
}
