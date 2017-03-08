package com.umasuo.user.domain.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by umasuo on 17/3/8.
 */
@Entity
@Table(name = "user_detail_info")
@Data
public class UserDetailInfo {

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

  private int age;

  private String signature;
}
