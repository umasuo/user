package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.DeveloperUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * User info repository.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<DeveloperUser, String> {

  /**
   * Get total count report.
   *
   * @param endTime
   * @return
   */
  @Query("select new map(d.developerId as developerId, count(d) as totalCount) from DeveloperUser" +
    " d where d.createdAt < ?1 group by d.developerId")
  List<Map> getTotalCountReport(long endTime);

  /**
   * Get total count report.
   *
   * @param developerId
   * @return
   */
  @Query("select new map(d.developerId as developerId, count(d) as totalCount) from DeveloperUser" +
    " d group by d.developerId having d.developerId = ?1")
  Map getTotalCountReport(String developerId);

  /**
   * Get increase report.
   *
   * @param startTime
   * @param endTime
   * @return
   */
  @Query("select new map(d.developerId as developerId, count(d) as increaseCount) from " +
    "DeveloperUser d where d.createdAt >= ?1 and d.createdAt < ?2 group by d.developerId")
  List<Map> getIncreaseReport(long startTime, long endTime);

  /**
   * Get increase report.
   *
   * @param developerId
   * @param startTime
   * @param endTime
   * @return
   */
  @Query("select new map(d.developerId as developerId, count(d) as increaseCount) from " +
    "DeveloperUser d where d.createdAt >= ?2 and d.createdAt < ?3 group by d.developerId having" +
    " d.developerId = ?1")
  Map getIncreaseReport(String developerId, long startTime, long endTime);
}
