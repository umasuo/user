package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
