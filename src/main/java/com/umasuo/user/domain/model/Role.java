package com.umasuo.user.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Roles in the system.
 */
@Entity
@Table(name = "role")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Role {

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
  @Column
  private Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column
  private Long lastModifiedAt;

  /**
   * Version.
   */
  @Column
  @Version
  private Integer version;

  /**
   * Role name.
   */
  @Column
  private String roleName;

  /**
   * List of scopes.
   */
  @OneToMany(cascade = CascadeType.ALL)
  private List<Scope> scopes;
}
