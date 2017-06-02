package com.umasuo.user.application.dto.mapper;

import com.umasuo.user.infrastructure.enums.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Permission mapper.
 */
public final class PermissionMapper {

  /**
   * Private default constructor.
   */
  private PermissionMapper(){
    // do nothing
  }

  /**
   * Convert list of string to list of permission.
   *
   * @param permissions permission string list
   * @return List of permissions
   */
  public static List<Permission> toEntity(List<String> permissions) {

    List<Permission> permissionList = new ArrayList<>();
    permissions.stream().forEach(
        perStr -> {
          Permission permission = Permission.getPermission(perStr);
          if (permission != null) {
            permissionList.add(permission);
          }
        }
    );
    return permissionList;
  }

  /**
   * Convert permission list to string list.
   * @param permissions Permission list
   * @return String list
   */
  public static List<String> toModel(List<Permission> permissions){
    List<String> permissionList = new ArrayList<>();
    permissions.stream().forEach(
         permission -> {
          String perStr = permission.getValue();
          if (perStr != null) {
            permissionList.add(perStr);
          }
        }
    );
    return permissionList;
  }
}
