package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<PlatformUser, String> {

  /**
   * Find user by email.
   *
   * @param email
   * @return
   */
  PlatformUser findOneByEmail(String email);

  /**
   * Find user by phone.
   *
   * @param phone
   * @return
   */
  PlatformUser findOneByPhone(String phone);
}
