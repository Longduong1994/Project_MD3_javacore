package controller;


import model.user.User;
import service.IGenericService;
import service.userService.RoleService;
import service.userService.UserService;


import java.util.List;


public class UserController implements IGenericService<User, Integer> {
    UserService userService = new UserService();
    RoleService roleService = new RoleService();


    @Override
    public List<User> findAll() {
        return userService.findAll();
    }

    @Override
    public void save(User user) {
        userService.save(user);
    }

    @Override
    public User findById(Integer id) {
        return userService.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        userService.deleteById(id);
    }

    public int newID(){
        return userService.newID();
    }

    public User getUserLogin(){
        return userService.getCurentUser();
    }
    public void getLogout() {
        userService.getLogout();
    }
    public void checkLogin(String username, String password) {
        userService.checkLogin(username, password);
    }
    public List<User>  searchByName(String name) {
       return userService.searchByName(name);
    }
    public void editStatusById(int id){
        userService.editStatusById(id);
    }

    public boolean changePassword(int id ,String newPassword){
     return userService.changePassword(id,newPassword);
    }

    public boolean forgotPassword(String userName ,String newPassword){
        return userService.forgotPassword(userName,newPassword);
    }
}