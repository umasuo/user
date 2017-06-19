package com.umasuo.user.application.dto;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by umasuo on 17/6/19.
 */
@Data
@ToString
public class SignIn implements Serializable {

  /**
   * The serialVersionUID.
   */
  private static final long serialVersionUID = -1226855896214404115L;

  /**
   * user's email. unique on this platform.
   */
  @Email
  private String email;

  /**
   * user's mobile phone. unique on this platform.
   */
  @NotNull
  private String phone;

  /**
   * developer id.
   */
  @NotNull
  private String developerId;

  /**
   * Which device this user from.
   */
  private String deviceDefinitionId;

  /**
   * user's externalId.
   */
  private String externalId;

  /**
   * ^                 # start-of-string
   * (?=.*[0-9])       # a digit must occur at least once
   * (?=.*[a-z])       # a lower case letter must occur at least once
   * (?=\S+$)          # no whitespace allowed in the entire string
   * .{8,}             # anything, at least eight places though
   * $                 # end-of-string
   */
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$")
  @NotNull
  private String password;
}
