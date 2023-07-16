package view;


import config.Config;
import config.InputMethods;
import controller.StoreController;
import controller.UserController;
import model.store.Store;
import model.user.Role;
import model.user.User;

import static view.UserView.recharge;


public class Navbar {
    private static StoreController storeController = new StoreController();

   private static UserController userController = new UserController();
   public static Role userRole = new Role();


    public Navbar() {
        while (true) {
            User currentUser = userController.getUserLogin();
            if (currentUser != null) {
                if (currentUser.getRole().getRole().equals("Admin")) {
                    System.out.println("                                                            <WELCOME " + currentUser.getUserName() + " TO TL AUCTION FLOOR>");
                    System.out.println("                                   ******************************TL AUCTION FLOOR ********************************");
                    String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
                    System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
                    System.out.format(alignFormat, 1, "UserManager", 2, " ProductManager", 3, "OrderManager", 4, "Notification", 5, "StoreManager", 6, "Logout");
                    System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
                    System.out.println("                                    ****************************************************************************");
                    System.out.println("Please choose a service...");
                    int choose = InputMethods.getInteger();
                    switch (choose) {
                        case 1:
                            UserView.UserManager();
                            break;
                        case 2:
                            ProductView.ProductManager();
                            break;
                        case 3:
                            OrderView.OrderManager();
                            break;
                        case 4:
                            NotificationView.NotificationManager();
                            break;
                        case 5:
                            StoreView.StoreManager();
                            break;
                        case 6:
                            userController.getLogout();
                            new Navbar();
                            break;
                        default:
                            System.out.println("Please enter 1 to 6");
                    }

                } else {
                    System.out.println("                                                            <Welcome " + currentUser.getName() + " to TL AUCTION FLOOR>");
                    System.out.println("                                   ******************************TL AUCTION FLOOR ********************************");
                    String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
                    System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
                    System.out.format(alignFormat, 1, "Profile", 2, "Auction", 3, "Feature", 4, "Message", 5, "Wallet", 6, "Logout");
                    System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
                    System.out.println("                                    ****************************************************************************");
                    System.out.println("Please choose a service...");

                    int choose = InputMethods.getInteger();
                    switch (choose) {
                        case 1:
                            UserView.profile();
                            break;
                        case 2:
                            ProductView.auction();
                            break;
                        case 3:
                          FeatureView.feature();
                            break;
                        case 4:
                            NotificationView.messageUser();
                            break;
                        case 5:
                            FeatureView.accountBalance();
                            break;
                        case 6:
                            userController.getLogout();
                            new Navbar();
                            break;
                        default:
                            System.err.println("Please enter 1 to 12");

                    }
                }
            } else {
                System.out.println("***************************************** STORE ***************************************************");
                String alignFormat = "| %-4d | %-13s | %-3d | %-13s | %-4d | %-17s | %-4d | %-15s |%n";
                System.out.format("+------+---------------+-----+---------------+------+-------------------+------+------------------+%n");
                System.out.format(alignFormat, 1, "Register", 2, "Login", 3, "Forgot password", 4, "Featured products");
                System.out.format("+------+---------------+-----+---------------+------+-------------------+------+------------------+%n");
                System.out.println("****************************************************************************************************");
                System.out.println("Please choose a service...");
                int chosce = InputMethods.getInteger();
                switch (chosce) {
                    case 1:
                        register();
                        userController = new  UserController();
                        break;
                    case 2:
                        login();
                        new Navbar();
                        break;
                    case 3:
                        forgotPassword();
                        break;
                    case 4:
                        OrderView.highest();
                        break;
                    default:
                        System.out.println("Please enter 1 to 4");
                }
            }
        }
    }

    public void login() {
        System.out.println("Enter the username:");
        String userName = InputMethods.getString();
        System.out.println("Enter the password:");
        String password = InputMethods.getString();
        userController.checkLogin(userName, password);
        new UserController();
    }

    public static void register() {
        int id = userController.newID();
        System.out.println("Enter the Name:");
        String name = InputMethods.getString();

        String userName;
        System.out.println("Enter the username");
        while (true) {
            userName = InputMethods.getString();
            if (Config.setValidateUserName(userName)) {
                break;
            }
            System.out.println("Enter another username");
        }

        String email;
        System.out.println("Enter the email");
        while (true) {
            email = InputMethods.getString();
            if (Config.setValidateEmail(email)) {
                break;
            }
            System.out.println("Enter another email.");
        }

        System.out.println("Enter the password");
        String password = InputMethods.getString();

        boolean status = true;
        Role role = new Role();
        role.setRole(Role.ROLE_NEW);
        User newUser = new User(id, name, userName, email, password, status, role);
        userController.save(newUser);
        System.out.println("Successful registration");
    }

    public static void changePassword() {
        User currentUser = userController.getUserLogin();
        System.out.println("Enter old password");
        String password;
        while (true){
          password = InputMethods.getString();
          if( userController.changePassword(currentUser.getId(),password)){
              break;
          }
            System.out.println("Incorrect password");
        };
        System.out.println("Enter new password");
        String newPassword = InputMethods.getString();
        currentUser.setPassword(newPassword);
        userController.save(currentUser);
        System.out.println("Change password successfully");
    }

    public void  forgotPassword() {
        System.out.println("Enter username");
        String username = InputMethods.getString();
        System.out.println("Enter new email");
        String email = InputMethods.getString();
       userController.forgotPassword(username, email);
    }


    public static void main(String[] args) {
        Role role = new Role();
        role.setRole(Role.ROLE_ADMIN);
        User admin = new User(1,"admin" , "admin123", "admin123@gmail.com","admin123",0,"0999999999","VN",true,0,role );
        userController.save(admin);
        new Navbar();
    }
}
