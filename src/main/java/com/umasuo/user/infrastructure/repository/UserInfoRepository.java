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

  @Query("select new map(d.developerId as developerId, count(d) as totalCount) from DeveloperUser d group by d.developerId")
  List<HashMap> getReport();

  @Query("select new map(d.developerId as developerId, count(d) as totalCount) from DeveloperUser d group by d.developerId having d.developerId = ?1")
  List<HashMap> getDeveloperReport(String developerId);

  @Query("select new map(d.developerId as developerId, count(d) as registerCount) from DeveloperUser d where d.createdAt >= ?1 and d.createdAt < ?2 group by d.developerId")
  List<HashMap> getRegisterReport(long startTime, long endTime);

  @Query("select new map(d.developerId as developerId, count(d) as registerCount) from DeveloperUser d where d.createdAt >= ?2 group by d.developerId having d.developerId = ?1")
  List<HashMap> getDeveloperRegisterReport(String developerId, long startTime);
}
