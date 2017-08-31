package com.umasuo.user.domain.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User detail info..
 */
@Entity
@Table(name = "user_detail_info")
@Data
public class UserDetailInfo {

  /**
   * User detail info.
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * user's name.
   */
  private String name;

  /**
   * user's icon.
   */
  private String icon;

  /**
   * Age.
   */
  private int age;

  /**
   * signature.
   */
  private String signature;

  /**
   * Country.
   */
  private String country;
}
