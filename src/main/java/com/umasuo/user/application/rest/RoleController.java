package com.umasuo.user.application.rest;

import static com.umasuo.user.infrastructure.AuthorizationRouter.ROLE_ROOT;
import static com.umasuo.user.infrastructure.AuthorizationRouter.ROLE_WITH_ID;

import com.umasuo.user.application.dto.RoleDraft;
import com.umasuo.user.application.dto.RoleView;
import com.umasuo.user.application.service.RoleApplication;
import com.umasuo.user.infrastructure.update.UpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Role controller.
 */
@RestController
@CrossOrigin
public class RoleController {

  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

  /**
   * Role application.
   */
  @Autowired
  private transient RoleApplication roleApplication;

  /**
   * Create a new Role.
   *
   * @param draft draft
   * @return RoleView
   */
  @PostMapping(ROLE_ROOT)
  public RoleView create(@RequestBody @Valid RoleDraft draft) {
    LOG.info("Enter. roleDraft: {}.", draft);

    RoleView view = roleApplication.create(draft);

    LOG.info("Exit. roleView: {}.", view);
    return view;
  }

  /**
   * Get Role by id.
   *
   * @param id String
   * @return Role view
   */
  @GetMapping(ROLE_WITH_ID)
  public RoleView getById(@PathVariable String id) {
    LOG.info("Enter. id: {}.", id);

    RoleView view = roleApplication.getById(id);

    LOG.info("Exit. roleView: {}.", view);
    return view;
  }

  /**
   * Get all roles
   *
   * @return list of role view
   */
  @GetMapping(ROLE_ROOT)
  public List<RoleView> getAll() {
    LOG.info("Enter.");

    List<RoleView> views = roleApplication.getAll();

    LOG.info("Exit. roleView size: {}.", views.size());
    return views;
  }


  /**
   * Update one role
   *
   * @param id            String
   * @param updateRequest UpdateRequest
   * @return RoleView
   */
  @PutMapping(ROLE_WITH_ID)
  public RoleView update(@PathVariable String id,
                          @RequestBody @Valid UpdateRequest updateRequest) {
    LOG.info("Enter. id: {}. updateRequest: {}.", id, updateRequest);

    RoleView view = roleApplication.update(id, updateRequest.getVersion(), updateRequest
        .getActions());

    LOG.info("Exit. roleView: {}.", view);
    return view;
  }
}
