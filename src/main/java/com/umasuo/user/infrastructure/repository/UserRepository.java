package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface UserRepository extends JpaRepository<PlatformUser, String>{

  PlatformUser findOneByEmail(String email);

  PlatformUser findOneByPhone(String phone);
}
