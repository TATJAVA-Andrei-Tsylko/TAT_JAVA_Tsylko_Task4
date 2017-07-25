package com.epam.tsylko.andrei.entity;


public enum Role {
    ADMIN("admin"),
    USER("user"),
    SUPER_ADMIN("superAdmin");

    private String role;

    private Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Integer getId() {
        return ordinal() +1 ;
    }

    public static Role getById(Integer id) {
        if (id >= 0 && id <= 2) {
            return Role.values()[id];
        } else {
            return null;
        }
    }

    public static Role getByName(String name) {
        switch (name) {
            case "ADMIN":
                return ADMIN;
            case "SUPER_ADMIN":
                return SUPER_ADMIN;
            default:
                return USER;
        }
    }
}
