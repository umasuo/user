package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.Organization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Organization repository class.
 *
 * Created by Davis on 17/6/1.
 */
public interface OrganizationRepository extends JpaRepository<Organization, String> {

}
