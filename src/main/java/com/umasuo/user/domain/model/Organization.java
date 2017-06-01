package com.umasuo.user.domain.model;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.List;

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
 * Organization entity, use to manager the relationship between user and group.
 *
 * Created by Davis on 17/6/1.
 */
@Data
@Entity
@Table(name = "organization")
@EntityListeners(AuditingEntityListener.class)
public class Organization {

  /**
   * unique id
   */
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;


  /**
   * The group id.
   * One group should only have one organization.
   */
  @OneToOne(mappedBy = "organization")
  private Group group;

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
