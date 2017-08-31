package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.ResourcePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Resource permission repository.
 */
public interface ResourcePermissionRepository extends JpaRepository<ResourcePermission, String> {

}
