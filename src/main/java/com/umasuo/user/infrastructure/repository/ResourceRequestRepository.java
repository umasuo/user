package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.ResourceRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Davis on 17/6/9.
 */
public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, String>{

  /**
   * Find by applicant id.
   *
   * @param applicantUserId the applicant id
   * @return the list
   */
  List<ResourceRequest> findByApplicantUserId(String applicantUserId);

  /**
   * Find by acceptor id.
   *
   * @param acceptorUserId the acceptor id
   * @return the list
   */
  List<ResourceRequest> findByAcceptorUserId(String acceptorUserId);
}
