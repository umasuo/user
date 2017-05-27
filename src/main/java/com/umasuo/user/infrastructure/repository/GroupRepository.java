package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.Group;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for group entity.
 *
 * Created by Davis on 17/5/27.
 */
public interface GroupRepository extends JpaRepository<Group, String> {

}
