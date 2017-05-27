package com.umasuo.user.infrastructure.validator;

import com.umasuo.exception.ConflictException;
import com.umasuo.user.domain.model.Group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Version validator.
 * Created by Davis on 17/5/27.
 */
public final class VersionValidator {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(VersionValidator.class);

  /**
   * Validate.
   *
   * @param entity the entity
   * @param version the version
   */
  public static void validate(Group entity, Integer version) {
    if (! version.equals(entity.getVersion())) {
      LOG.debug("Version not match, request version: {}, real version: {}.",
          version, entity.getVersion());
      throw new ConflictException("Version not match");
    }
  }
}
