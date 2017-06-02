package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.Scope;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Scope repository.
 */
@Repository
public interface ScopeRepository extends JpaRepository<Scope, String> {
}
