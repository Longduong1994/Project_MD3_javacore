package view;

import config.Config;
import config.InputMethods;
import controller.UserController;
import model.user.Role;
import model.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class UserView {
    static UserController userController = new UserController();
private static User currentUser = userController.getUserLogin();
    public static void UserManager() {
        while (true){
            System.out.println("********************************************* USER MANAGER ********************************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
            System.out.format("+------+-----------------+------+-------------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 1, "Show Users", 2, "Lock/UnLock Users", 3, "Search User", 4, "Home");
            System.out.format("+------+-----------------+------+-------------------+------+-----------------+------+-----------------+%n");
            System.out.println("*******************************************************************************************************");
            System.out.println("Please choose a service...");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    getUsers();
                    break;
                case 2:
                    editStatusUser();
                    break;
                case 3:
                    FeatureView.searchUserByName();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Please enter 1 to 4!!!");
            }
        }
    }

    public static void profile() {
       while (true) {
           System.out.println("*********************************************** USER *************************************************");
           String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
           System.out.format("+------+-----------------+------+------------------+------+-----------------+------+-----------------+%n");
           System.out.format(alignFormat, 1, "My Information", 2, "Edit Information", 3, "Change Password", 4, "Home");
           System.out.format("+------+-----------------+------+------------------+------+-----------------+------+-----------------+%n");
           System.out.println("******************************************************************************************************");
           System.out.println("Please choose a service...");
           int choose = InputMethods.getInteger();
           switch (choose) {
               case 1:
                   myInfor();
                   break;
               case 2:
                   updateUser();
                   break;
               case 3:
                   Navbar.changePassword();
                   break;
               case 4:
                   return;
               default:
                   System.out.println("Please enter 1 to 4!!!");
           }
       }
    }
    public static void getUsers() {
        System.out.println("************************************************** SHOW LIST USER *************************************");
        String alignFormat = "| %-4d | %-15s | %-20s | %-10s | %-20s | %-15s |%n";
        System.out.format("+------+-----------------+----------------------+------------+---------------------+------------------+%n");
        System.out.format("|  ID  |    USER NAME    |        EMAIL         |   STATUS   |        ROLE         |     BALANCE      |%n");
        System.out.format("+------+-----------------+----------------------+------------+---------------------+------------------+%n");
        for (User user : userController.findAll()) {
            System.out.format(alignFormat,
                    user.getId(), user.getName(), user.getEmail(),
                    (user.isStatus() ? "UNLOCKED" : "LOCKED"),
                    user.getRole().getRole(),
                    user.getBalance());
        }
        System.out.println("*******************************************************************************************************");
    }


    public static void editStatusUser(){
        System.out.println("Enter the id");
        int id = InputMethods.getInteger();
        userController.editStatusById(id);
    }

    public static void updateUser() {
        User currentUser = userController.getUserLogin();
        if (currentUser == null) {
            System.out.println("You are not logged in.");
            return;
        }

        System.out.println("Enter the address:");
        String address = InputMethods.getString();
        System.out.println("Enter the phone number:");
        String phoneNumber;
        while (true) {
            phoneNumber = InputMethods.getString();
            if (Config.setValidateUserName(phoneNumber)) {
                break;
            }
            System.out.println("Enter another username");
        }

        currentUser.setAddress(address);
        currentUser.setPhoneNumber(phoneNumber);
        userController.save(currentUser);
        new Config<User>().writeFromFile(Config.PATH_USER_LOGIN, new ArrayList<>(Arrays.asList(currentUser)));
        System.out.println("Update successful.");
    }


    public static void recharge() {
        User currentUser = userController.getUserLogin();
                System.out.println("Enter the balance");
                double balance = InputMethods.getDouble();
                double currentBalance = currentUser.getBalance();
                double updatedBalance = currentBalance + balance;
                currentUser.setBalance(updatedBalance);
                userController.save(currentUser);
                new Config<User>().writeFromFile(Config.PATH_USER_LOGIN, new ArrayList<>(Arrays.asList(currentUser)));
                System.out.println("Successful recharge");
    }



    public static void myInfor() {
        User currentUser = userController.getUserLogin();
        System.out.println("************************************* MY INFORMATION *************************************");
        String alignFormat = "| %-4d | %-15s | %-20s | %-10s | %-10s | %-25s | %-15s | $%-14.2f | %-10s |%n";
        System.out.format("+------+-----------------+----------------------+------------+------------+---------------------------+-----------------+-----------------+------------+%n");
        System.out.format("|  ID  |    USER NAME    |        EMAIL         |   STATUS   |    ROLE    |         ADDRESS           |   PHONE NUMBER  |      BALANCE    |    STAR    |%n");
        System.out.format("+------+-----------------+----------------------+------------+------------+---------------------------+-----------------+-----------------+------------+%n");
        for (User user: userController.findAll()) {
            if (user.getUserName().equals(currentUser.getUserName())){
                System.out.format(alignFormat,
                        currentUser.getId(), currentUser.getName(), currentUser.getEmail(),
                        (currentUser.isStatus() ? "LOCKED" : "UNLOCKED"), currentUser.getRole().getRole(),
                        (currentUser.getAddress() != null ? currentUser.getAddress() : "Not updated"),
                        (currentUser.getPhoneNumber() != null ? currentUser.getPhoneNumber() : "Not updated"),
                        currentUser.getBalance(), currentUser.getRole().getPoint());
                System.out.format("+------+-----------------+----------------------+------------+------------+---------------------------+-----------------+-----------------+------------+%n");
            }
        }
    }

}
