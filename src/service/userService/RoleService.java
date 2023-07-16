package service.userService;


import model.user.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleService {
    private List<Role> roles;

    public RoleService() {
        roles = new ArrayList<>();

    }

    public List<Role> findAll() {
        return roles;
    }

    public void updateRoleByPoint(Role role) {
        int point = role.getPoint();
        if (point >= 10) {
            role.setRole(Role.ROLE_VERIFIED);
        } else if (point >= 20) {
            role.setRole(Role.ROLE_TRUSTED);
        } else if (point >= 30) {
            role.setRole(Role.ROLE_ACTIVE);
        } else if (point >= 40) {
            role.setRole(Role.ROLE_EXEMPLARY);
        }
    }
}
