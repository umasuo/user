package com.umasuo.user.application.rest;

import static com.umasuo.user.infrastructure.AuthorizationRouter.SCOPE_ROOT;
import static com.umasuo.user.infrastructure.AuthorizationRouter.SCOPE_WITH_ID;

import com.umasuo.user.application.dto.ScopeView;
import com.umasuo.user.application.dto.mapper.ScopeDraft;
import com.umasuo.user.application.service.ScopeApplication;
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
 * Scope controller.
 */
@RestController
@CrossOrigin
public class ScopeController {

  /**
   * LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ScopeController.class);

  /**
   * Scope application.
   */
  @Autowired
  private transient ScopeApplication scopeApplication;

  /**
   * Create a new Scope.
   *
   * @param draft draft
   * @return ScopeView
   */
  @PostMapping(SCOPE_ROOT)
  public ScopeView create(@RequestBody @Valid ScopeDraft draft) {
    LOG.info("Enter. scopeDraft: {}.", draft);

    ScopeView view = scopeApplication.create(draft);

    LOG.info("Exit. scopeView: {}.", view);
    return view;
  }

  /**
   * Get scope by id.
   *
   * @param id String
   * @return Scope view
   */
  @GetMapping(SCOPE_WITH_ID)
  public ScopeView getById(@PathVariable String id) {
    LOG.info("Enter. id: {}.", id);

    ScopeView view = scopeApplication.getById(id);

    LOG.info("Exit. scopeView: {}.", view);
    return view;
  }

  /**
   * Get all scopes
   *
   * @return list of scope view
   */
  @GetMapping(SCOPE_ROOT)
  public List<ScopeView> getAll() {
    LOG.info("Enter.");

    List<ScopeView> views = scopeApplication.getAll();

    LOG.info("Exit. scopeView size: {}.", views.size());
    return views;
  }


  /**
   * Update one scope.
   *
   * @param id            String
   * @param updateRequest UpdateRequest
   * @return ScopeView
   */
  @PutMapping(SCOPE_WITH_ID)
  public ScopeView update(@PathVariable String id,
                          @RequestBody @Valid UpdateRequest updateRequest) {
    LOG.info("Enter. id: {}. updateRequest: {}.", id, updateRequest);

    ScopeView view = scopeApplication.update(id, updateRequest.getVersion(), updateRequest
        .getActions());

    LOG.info("Exit. scopeView: {}.", view);
    return view;
  }
}
