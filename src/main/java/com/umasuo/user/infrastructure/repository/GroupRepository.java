package com.umasuo.user.infrastructure.repository;

import com.umasuo.user.domain.model.Group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for group entity.
 *
 * Created by Davis on 17/5/27.
 */
public interface GroupRepository extends JpaRepository<Group, String> {

  /**
   * Find by developer id.
   *
   * @param developerId the developer id
   * @return the list
   */
  List<Group> findByDeveloperId(String developerId);

  /**
   * Find by children id.
   *
   * @param childrenId the children id
   * @return the group
   */
  Group findByChildrenId(String childrenId);

  /**
   * Find by parent id.
   *
   * @param parentId the parent id
   * @return the list
   */
  List<Group> findByParent(String parentId);
}
