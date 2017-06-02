package com.umasuo.user.domain.service;

import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.user.domain.model.Module;
import com.umasuo.user.infrastructure.repository.ModuleRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Module service, used to manage modules.
 */
@Service
public class ModuleService {

  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ModuleService.class);

  /**
   * Module repository.
   */
  @Autowired
  private transient ModuleRepository moduleRepository;

  /**
   * Create module with sample.
   *
   * @param module Module
   * @return Module
   */
  public Module save(Module module) {
    LOG.debug("Enter. module: {}.", module);

    try {
      Module savedModule = this.moduleRepository.save(module);

      LOG.debug("Exit. module: {}.", savedModule);

      return savedModule;
    } catch (DataIntegrityViolationException ex) {
      ConstraintViolationException ee = (ConstraintViolationException) ex.getCause();
      String state = ee.getSQLState();
      // 23505 means sql some unique column already exist
      if ("23505".equals(state)) {
        throw new AlreadyExistException("Module already exist.");
      } else {
        throw ex;
      }
    }
  }

  /**
   * Get on module by id.
   *
   * @param id String
   * @return Module
   */
  public Module getById(String id) {
    LOG.debug("Enter. id: {}.", id);

    Module module = this.moduleRepository.findOne(id);
    if (module == null) {
      throw new NotExistException("The module not exist. id: " + id);
    }

    LOG.debug("Enter. module: {}.", module);
    return module;
  }

  /**
   * Get all modules from database.
   *
   * @return List of modules
   */
  public List<Module> getAll() {
    LOG.debug("Enter.");

    List<Module> moduleList = this.moduleRepository.findAll();

    LOG.debug("Exit. moduleSize: {}.", moduleList.size());
    LOG.trace("ModuleList: {}.", moduleList);
    return moduleList;
  }

  /**
   * Get modules by id list
   *
   * @param ids list of ids
   * @return list of modules
   */
  public List<Module> getListById(List<String> ids) {
    LOG.debug("Enter. ids: {}.", ids);

    List<Module> moduleList = new ArrayList<>();
    ids.stream().forEach(
        id -> moduleList.add(getById(id))
    );

    LOG.debug("Exit. moduleSize: {}.", moduleList.size());
    LOG.trace("ModuleList: {}.", moduleList);
    return moduleList;
  }

}
