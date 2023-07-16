package model.user;

import java.io.Serializable;

public class Role implements Serializable {
    public static final String ROLE_NEW = "New";
    public static final String ROLE_VERIFIED = "Verified";
    public static final String ROLE_TRUSTED = "Trusted";
    public static final String ROLE_ACTIVE = "Active";
    public static final String ROLE_EXEMPLARY = "Exemplary";
    public static final String ROLE_ADMIN = "Admin";

    private int id;
    private String role;
    private int point = 5;

    public Role(int id, String role, int point) {
        this.id = id;
        this.role = role;
        this.point = point;
    }

    public Role() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", point=" + point +
                '}';
    }
}
