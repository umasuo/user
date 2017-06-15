package com.umasuo.user.domain.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PlatformUser basic info on the platform. a user is unique in the platform, not for developer.
 */
@Entity
@Table(name = "platform_user")
@Data
public class PlatformUser {
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
   * version used for update date check.
   */
  private Integer version;

  /**
   * user's email, this should be unique.
   */
  @Column(unique = true, nullable = true)
  private String email;

  /**
   * user's mobile phone.
   */
  @Column(unique = true, nullable = true)
  private String phone;

  /**
   * user's externalId.
   * if the user is sign up with another OAUTH2 provider, this will be the.
   * this will be used only then they bind to the platform account.
   */
  @Column(unique = true, nullable = true)
  private String externalId;
}
