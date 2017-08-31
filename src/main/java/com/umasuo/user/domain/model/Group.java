package com.umasuo.user.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * User group entity.
 */
@Getter
@Setter
@Entity
@Table(name = "groups")
@EntityListeners(AuditingEntityListener.class)
public class Group {

  /**
   * unique id
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  private Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  private Long lastModifiedAt;

  /**
   * The version.
   */
  @Version
  @Column(name = "version")
  private Integer version;

  /**
   * The name of group.
   */
  @Column(name = "name")
  private String name;

  /**
   * The parent group.
   */
  @Column(name = "parent_id")
  private String parent;

  /**
   * The children group.
   */
  @ElementCollection
  private List<String> childrenId;

  /**
   * Id for developer.
   */
  @Column(name = "developer_id")
  private String developerId;

  /**
   * group的ownerId.
   */
  private String ownerId;

  /**
   * The managers id.
   * One group can have multi managers, manager should be the user of this group.
   */
  @ElementCollection
  private List<String> managers;

  /**
   * The users id.
   * One group can have multi users.
   */
  @ElementCollection
  private List<String> users;
}
