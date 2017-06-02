package com.umasuo.user.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.Role;
import com.umasuo.user.infrastructure.repository.RoleRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Role service, used to manage roles.
 */
@Service
public class RoleService {

  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RoleService.class);

  /**
   * Role repository.
   */
  @Autowired
  private transient RoleRepository roleRepository;

  /**
   * Create module with sample.
   *
   * @param role Role
   * @return Role
   */
  public Role save(Role role) {
    LOG.debug("Enter. role: {}.", role);
    try {

      Role savedRole = this.roleRepository.save(role);

      LOG.debug("Exit. role: {}.", savedRole);

      return savedRole;
    } catch (DataIntegrityViolationException ex) {
      ConstraintViolationException ee = (ConstraintViolationException) ex.getCause();
      String state = ee.getSQLState();
      // 23505 means sql some unique column already exist
      if ("23505".equals(state)) {
        throw new AlreadyExistException("Role already exist.");
      } else {
        throw ex;
      }
    }
  }


  /**
   * Get role by id.
   *
   * @param id String
   * @return Role
   */
  public Role getById(String id) {
    LOG.debug("Enter. id: {}.", id);

    Role role = roleRepository.findOne(id);
    if (role == null) {
      throw new NotExistException("Role not exist. id: " + id);
    }

    LOG.debug("Exit. role: {}.", role);

    return role;
  }

  /**
   * Get all roles from database.
   *
   * @return List of role
   */
  public List<Role> getAll() {
    LOG.debug("Enter.");

    List<Role> roles = roleRepository.findAll();

    LOG.debug("Exit. roleSize: {}.", roles.size());
    LOG.trace("RoleList: {}.", roles);

    return roles;
  }

  /**
   * Get roles by id list
   *
   * @param ids list of ids
   * @return list of role
   */
  public List<Role> getListById(List<String> ids) {
    LOG.debug("Enter. ids: {}.", ids);

    List<Role> roles = new ArrayList<>();
    ids.stream().forEach(
        id -> roles.add(getById(id))
    );

    LOG.debug("Exit. roleSize: {}.", roles.size());
    LOG.trace("RoleList: {}.", roles);
    return roles;
  }

}
