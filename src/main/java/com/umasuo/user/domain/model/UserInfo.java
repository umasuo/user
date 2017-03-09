package com.umasuo.user.domain.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by umasuo on 17/3/8.
 * TODO should we give a numerical id?
 */
@Entity
@Table(name = "user_info")
@Data
public class UserInfo {
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
  protected ZonedDateTime createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected ZonedDateTime lastModifiedAt;

  /**
   * version used for update date check.
   */
  private Integer version;

  /**
   * uid that saved in {@code User}
   */
  @Column(unique = true, nullable = false)
  private String uid;

  /**
   * user's externalId.
   * if the user is sign up with another OAUTH2 provider, this will be the.
   * this will be used only when the user only use it's own third party account.
   */
  @Column(unique = true, nullable = true)
  private String externalId;

  /**
   * which developer this user belong to.
   */
  @Column(nullable = false)
  private String developerId;

  /**
   * Which device this user from.
   */
  private String deviceDefinitionId;

  /**
   * user's password.
   */
  private String password;

  /**
   * user detail info.
   */
  @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
  private UserDetailInfo userDetail;

  @Override
  public String toString() {
    return "UserInfo{"
        + "id='" + id + '\''
        + ", createdAt=" + createdAt
        + ", lastModifiedAt=" + lastModifiedAt
        + ", version=" + version
        + ", uid='" + uid + '\''
        + ", externalId='" + externalId + '\''
        + ", developerId='" + developerId + '\''
        + ", deviceDefinitionId='" + deviceDefinitionId + '\''
        + ", userDetail=" + userDetail
        + '}';
  }
}
