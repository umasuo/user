package com.umasuo.user.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * User group entity.
 * Created by Davis on 17/5/27.
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
  private ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  private ZonedDateTime lastModifiedAt;

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
   * The organization.
   */
  @OneToOne(cascade= CascadeType.ALL)
  @JoinColumn(name = "organization_id")
  private Organization organization;
}
