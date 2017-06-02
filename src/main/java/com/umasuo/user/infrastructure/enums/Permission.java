package com.umasuo.user.infrastructure.enums;

/**
 * Permissions for each module.
 */
public enum Permission {
  /**
   * Permission for save a resource.
   */
  CREATE("CREATE"),

  /**
   * Permission for read a resource.
   */
  READ("READ"),

  /**
   * Permission for update a resource.
   */
  UPDATE("UPDATE"),

  /**
   * Permission for delete a resource.
   */
  DELETE("DELETE");

  /**
   * Value.
   */
  private String value;

  /**
   * Constructor.
   *
   * @param value String
   */
  Permission(String value) {
    this.value = value;
  }

  /**
   * Get string value.
   *
   * @return String
   */
  public String getValue() {
    return value;
  }

  /**
   * Get Permission by string.
   *
   * @param value string
   * @return Permission
   */
  public static Permission getPermission(String value) {
    Permission result = null;
    switch (value) {
      case "CREATE":
      case "POST":
        result = CREATE;
        break;
      case "READ":
      case "GET":
        result = READ;
        break;
      case "UPDATE":
      case "PUT":
        result = UPDATE;
        break;
      case "DELETE":
        result = DELETE;
        break;
      default:
        break;
    }
    return result;
  }
}
