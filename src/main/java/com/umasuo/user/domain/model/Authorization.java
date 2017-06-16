package com.umasuo.user.domain.model;

import lombok.Data;
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
 * Created by Davis on 17/6/2.
 */
@Data
@Entity
@Table(name = "authorizations")
@EntityListeners(AuditingEntityListener.class)
public class Authorization {


  /**
   * Uuid.
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
  private Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  private Long lastModifiedAt;

  /**
   * Version.
   */
  @Version
  private Integer version;

  /**
   * Group id or user id.
   */
  @Column(unique = true)
  private String ownerId;

  /**
   * Developer id.
   */
  @Column(name = "developer_id")
  private String developerId;

  /**
   * List of roles id.
   */
  @ElementCollection
  private List<String> roles;
}
