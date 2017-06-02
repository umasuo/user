package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.Module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Module repository.
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, String> {
}
