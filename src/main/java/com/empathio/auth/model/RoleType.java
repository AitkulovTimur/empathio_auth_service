package com.empathio.auth.model;

public enum RoleType {
    ROLE_DEFAULT("default"),
    ROLE_EMPLOYEE("employee"),
    ROLE_TEAM_MANAGER("team_manager"),
    ROLE_ADMIN("admin"),
    ROLE_SUPER_ADMIN("super_admin");

    public final String roleName;
    RoleType(String roleName) {
        this.roleName = roleName;
    }
}
