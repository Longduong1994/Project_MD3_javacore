package controller;

import model.user.Role;
import service.userService.RoleService;

import java.util.List;

public class RoleController {
    RoleService roleService = new RoleService();

    public List<Role> findAll(){
        return roleService.findAll();
    }
    public  void updateRoleByPoint(Role role){
        roleService.updateRoleByPoint(role);
    }

}
