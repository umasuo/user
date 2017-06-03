package com.umasuo.user.application.rest;

import static com.umasuo.user.infrastructure.AuthorizationRouter.AUTHENTICATION_ROOT;
import static com.umasuo.user.infrastructure.AuthorizationRouter.AUTHORIZATION_WITH_ID;
import static com.umasuo.user.infrastructure.Router.DEVELOPER_ID;

import com.umasuo.user.application.dto.AuthorizationDraft;
import com.umasuo.user.application.dto.AuthorizationView;
import com.umasuo.user.application.service.AuthorizationApplication;
import com.umasuo.user.infrastructure.update.UpdateRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Authorization controller.
 *
 * Created by Davis on 17/6/3.
 */
@RestController
public class AuthorizationController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(AuthorizationController.class);

  /**
   * The Authorization application.
   */
  private AuthorizationApplication authorizationApplication;

  /**
   * Create authorization view.
   *
   * @param authorizationDraft the authorization draft
   * @return the authorization view
   */
  @PostMapping(AUTHENTICATION_ROOT)
  public AuthorizationView create(@RequestBody @Valid AuthorizationDraft authorizationDraft) {
    LOG.info("Enter. authorizationDraft: {}.", authorizationDraft);

    AuthorizationView result = authorizationApplication.create(authorizationDraft);

    LOG.info("Exit. result: {}.", result);

    return result;
  }

  /**
   * Update authorization view.
   *
   * @param id the authorization id
   * @param updateRequest the update request
   * @return the authorization view
   */
  @PutMapping(AUTHORIZATION_WITH_ID)
  public AuthorizationView update(@PathVariable("id") String id,
      @RequestBody @Valid UpdateRequest updateRequest) {
    LOG.info("Enter. groupId: {}, updateRequest: {}.", id, updateRequest);

    AuthorizationView result = authorizationApplication
        .update(id, updateRequest.getVersion(), updateRequest.getActions());

    LOG.trace("Updated group: {}.", result);
    LOG.info("Exit.");
    return result;
  }

  /**
   * Gets by id.
   *
   * @param id the authorization id
   * @return the by id
   */
  @GetMapping(AUTHORIZATION_WITH_ID)
  public AuthorizationView getById(@PathVariable("id") String id) {
    LOG.info("Enter. id: {}.", id);

    AuthorizationView result = authorizationApplication.getById(id);

    LOG.info("Exit. result: {}.", result);
    return result;
  }


  /**
   * Gets all authorization by developer id.
   *
   * @param developerId the developer id
   * @return the all
   */
  @GetMapping(AUTHENTICATION_ROOT)
  public List<AuthorizationView> getAll(@RequestParam(DEVELOPER_ID) String developerId) {
    LOG.info("Enter. developerId: {}.", developerId);

    List<AuthorizationView> result = authorizationApplication.getAll(developerId);

    LOG.info("Exit. result count: {}.", result.size());

    return result;
  }
}
