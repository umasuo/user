package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String>,
    CrudRepository<UserInfo, String> {

  UserInfo findOneByUid(String uid);
}
