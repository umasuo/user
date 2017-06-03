package com.umasuo.user.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.Authorization;
import com.umasuo.user.infrastructure.repository.AuthorizationRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Authorization service, used to manage user or group authentication info.
 */
@Service
public class AuthorizationService {

  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AuthorizationService.class);

  /**
   * Authorization repository.
   */
  @Autowired
  private transient AuthorizationRepository repository;

  /**
   * Create Authorization with sample.
   *
   * @param authorization Authorization
   * @return Authorization
   */
  public Authorization save(Authorization authorization) {
    LOG.debug("Enter. admin: {}.", authorization);

    try {
      Authorization savedAuthorization = repository.save(authorization);

      LOG.debug("Exit. admin: {}.", savedAuthorization);

      return savedAuthorization;
    } catch (DataIntegrityViolationException ex) {
      ConstraintViolationException ee = (ConstraintViolationException) ex.getCause();
      String state = ee.getSQLState();
      // 23505 means sql some unique column already exist
      if ("23505".equals(state)) {
        throw new AlreadyExistException("Authorization already exist.");
      } else {
        throw ex;
      }
    }
  }

  /**
   * Get Authorization by id.
   *
   * @param id String
   * @return Authorization
   */
  public Authorization getById(String id) {
    LOG.debug("Enter. id: {}.", id);

    Authorization admin = repository.findOne(id);
    if (admin == null) {
      throw new NotExistException("The Authorization not exist for id: " + id);
    }

    LOG.debug("Exit. admin: {}.", admin);

    return admin;
  }

  /**
   * Get all Authorization from database.
   *
   * @return List of Authorization
   */
  public List<Authorization> getAll(String developerId) {
    LOG.debug("Enter. developerId: {}.", developerId);

    List<Authorization> authorizations = repository.findByDeveloperId(developerId);

    LOG.trace("AdminList: {}.", authorizations);
    LOG.debug("Exit. authorization count: {}.", authorizations.size());

    return authorizations;
  }

}
