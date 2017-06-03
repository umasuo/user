package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.Authorization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Authorization repository.
 *
 * Created by Davis on 17/6/3.
 */
public interface AuthorizationRepository extends JpaRepository<Authorization, String>{

  /**
   * Find by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  List<Authorization> findByDeveloperId(String developerId);
}
