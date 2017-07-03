package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.DeveloperUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<DeveloperUser, String> {

  @Query("select new map(d.developerId as developerId, count(d) as totalCount) from DeveloperUser" +
      " d where d.createdAt < ?1 group by d.developerId")
  List<HashMap> getTotalCountReport(long endTime);

  @Query("select new map(d.developerId as developerId, count(d) as totalCount) from DeveloperUser" +
      " d group by d.developerId having d.developerId = ?1")
  HashMap getTotalCountReport(String developerId);

  @Query("select new map(d.developerId as developerId, count(d) as increaseCount) from " +
      "DeveloperUser d where d.createdAt >= ?1 and d.createdAt < ?2 group by d.developerId")
  List<HashMap> getIncreaseReport(long startTime, long endTime);

  @Query("select new map(d.developerId as developerId, count(d) as increaseCount) from " +
      "DeveloperUser d where d.createdAt >= ?2 and d.createdAt < ?3 group by d.developerId having" +
      " d.developerId = ?1")
  HashMap getIncreaseReport(String developerId, long startTime, long endTime);
}
