package service.userService;

import config.Config;
import config.InputMethods;
import model.user.User;
import service.IGenericService;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IGenericService<User, Integer> {

    List<User> users = new config.Config<User>().readFromFile(config.Config.PATH_USER);

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public void save(User user) {
        if (findById(user.getId()) == null) {
            // add
            users.add(user);
        } else {
            // update
            users.set(users.indexOf(findById(user.getId())), user);
        }
        new Config<User>().writeFromFile(Config.PATH_USER, users);
    }

    @Override
    public User findById(Integer id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(users.get(i));
            }
        }
    }

    public int newID() {
        int max = 0;
        for (User user : users) {
            if (user.getId() > max)
                max = user.getId();
        }
        return max + 1;
    }

    public boolean existedByUsername(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean existedByEmail(String email) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkLogin(String username, String password) {
        List<User> userLogin = new Config<User>().readFromFile(Config.PATH_USER_LOGIN);
        boolean usernameExists = false;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                usernameExists = true;

                if (user.getPassword().equals(password)) {
                    if (user.isStatus()) {
                        userLogin.add(user);
                        new Config<User>().writeFromFile(Config.PATH_USER_LOGIN, userLogin);
                        System.out.println("Logged in successfully");
                        return true;  // Đăng nhập thành công
                    } else {
                        System.out.println("Account has been locked");
                        return false;  // Tài khoản đã bị khóa
                    }
                } else {
                    System.out.println("Wrong password");
                    return false;  // Sai mật khẩu
                }
            }
        }

        if (!usernameExists) {
            System.out.println("Username does not exist");
        }

        return false;  // Tên đăng nhập không tồn tại
    }


    public User getCurentUser() {
        if (new Config<User>().readFromFile(Config.PATH_USER_LOGIN).size() != 0) {
            return new Config<User>().readFromFile(Config.PATH_USER_LOGIN).get(0);
        }
        return null;
    }

    public void getLogout() {
        if (new Config<User>().readFromFile(Config.PATH_USER_LOGIN).size() != 0) {
            PrintWriter pw = null;
            try {
                pw = new PrintWriter("C:\\Users\\Admin\\IdeaProjects\\MD3_TechnologyShop\\src\\database\\userLogin.txt");
                pw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public List<User> searchByName(String name) {
        List<User> listUserSearch = new ArrayList<>();
        for (User user : users) {
            if (user.getUserName().toLowerCase().contains(name.toLowerCase())) {
                listUserSearch.add(user);
            }

        }
        return listUserSearch;
    }

    public void editStatusById(int id) {
        User user = findById(id);
        boolean newStatus = !user.isStatus();
        user.setStatus(newStatus);

        String statusMessage = newStatus ? "Account unlocked" + user.getName() : "Account locked " + user.getName();
        System.out.println("User is now " + statusMessage);

        new Config<User>().writeFromFile(Config.PATH_USER, users);
    }

    public boolean changePassword(int id, String password) {
        for (User user : users) {
            if (user.getName().equals(password) && user.getId() == id) {
                return true;
            }
        }
        return  false;
    }

    public boolean forgotPassword(String userName, String email) {
        for (User user : users) {
            if (user.getUsername().equals(userName) && user.getEmail().equals(email)) {
                if (!user.isStatus()) {
                    System.out.println("Account has been locked");
                    return false;
                }

                System.out.println("Please enter a new password");
                String newPassword = InputMethods.getString();
                user.setPassword(newPassword);
                System.out.println("Password changed successfully.");
                return true;
            }
        }

        System.out.println("Invalid username or email.");
        return false;
    }

}
