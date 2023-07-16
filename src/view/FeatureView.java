package view;

import config.InputMethods;
import controller.*;
import model.product.Product;
import model.store.Notification;
import model.store.Store;
import model.user.User;

import java.util.Date;

import static view.UserView.recharge;

public class FeatureView {
    private static StoreController storeController = new StoreController();
    private static RoleController roleController = new RoleController();
    private static UserController userController = new UserController();
    private static ProductController productController = new ProductController();
    private static NotificationController notificationController = new NotificationController();
    private static User currentUser = userController.getUserLogin();

    public static void feature() {
        while (true) {
            System.out.println("**************************************************** FEATURE ****************************************************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 1, "SearchProduct", 2, "SearchUser", 3, "Give Start", 4, "Report", 5, "Home");
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+----------------+%n");
            System.out.println("*****************************************************************************************************************************");
            System.out.println("Please choose a service...");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchUserByName();
                    break;
                case 3:
                    giveStar();
                    break;
                case 4:
                    report();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Please enter 1 to 5!!!");
            }
        }
    }

    public static void accountBalance() {
        while (true) {
            System.out.println("******************************************************* WALLET *************************************************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 1, "Recharge", 2, "Withdraw Money", 3, "Service", 4, "Transfer", 5, "Home");
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.println("*****************************************************************************************************************************");
            System.out.println("Please choose a service...");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    recharge();
                    currentUser = userController.getUserLogin();
                    break;
                case 2:
                    withdrawMoney();
                    break;
                case 3:
                    userService();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Please enter 1 to 4!!!");
            }
        }
    }

    private static void transfer() {
        System.out.println("Enter User Id you want to transfer");
        int id = InputMethods.getInteger();
        User moneyReceiver = userController.findById(id);
        if(moneyReceiver.getUserName().equals(currentUser.getUserName())){
            System.out.println("You can't transfer money yourself");
            return;
        }
        System.out.println("Enter the amount you want to transfer");
        double amount;
        while (true) {
            amount = InputMethods.getDouble();
            double totalAmount = amount + storeController.checkServiceFee(currentUser);
            if (totalAmount > currentUser.getBalance()) {
                System.out.println("The amount is not enough to make the transaction");
                return;
            } else {
                break;
            }
        }
        moneyReceiver.setBalance(moneyReceiver.getBalance() + amount);
        userController.save(moneyReceiver);
        currentUser.setBalance(currentUser.getBalance() - amount - storeController.checkServiceFee(currentUser));
        userController.save(currentUser);
        receiptNoti(currentUser.getName(), amount, moneyReceiver.getUserName() );
        System.out.println("Money transfer successful");
    }

    public static void receiptNoti(String name , double amount,String userName ){
        currentUser = new UserController().getUserLogin();
       int id = new NotificationController().newId();
       String title = "receipt";
       String content = "You get " +amount + " $ from " + name;
       String type = "Notification";
       String sender = "Auction system";
       String receiver = userName;
       boolean status = true;
       Date date = new Date();
       Notification receiptNoti  = new Notification(id,title,content,sender,receiver,date,status,type);
       notificationController.save(receiptNoti);
    }


    public static void withdrawMoney() {
        User currentUser = userController.getUserLogin();
        Store store = new Store();
        System.out.println("Enter the amount you want to withdraw ");
        double amount = InputMethods.getDouble();
        if (currentUser.getBalance() < amount + storeController.checkServiceFee(currentUser)) {
            System.out.println("Insufficient account balance");
        } else {
            currentUser.setBalance(currentUser.getBalance() - (amount + storeController.checkServiceFee(currentUser)));
            userController.save(currentUser);
            store.setRevenue(store.getRevenue() + storeController.checkServiceFee(currentUser));
            storeController.editStore(store);
            System.out.println("You withdrew " + amount + " $!");
        }
    }

    public static void userService() {
        currentUser = userController.getUserLogin();
        System.out.println("**************************************ACCOUNT BALANCE *********************************");
        String alignFormat = "| %-4d | %-15s | %-20s | %-10s | %-20s | %n";
        System.out.format("+------+-----------------+----------------------+------------+---------------------+%n");
        System.out.format("|  ID  |    USER NAME    |     SERVICE FEE      |    RATE    |      BALANCE        |%n");
        System.out.format("+------+-----------------+----------------------+------------+---------------------+%n");
        for (User user : userController.findAll()) {
            if (user.getUserName().equals(currentUser.getUserName())) {
                System.out.format(alignFormat,
                        user.getId(), user.getUserName(), storeController.checkServiceFee(user),
                        storeController.checkRate(user),
                        currentUser.getBalance());
            }
        }
        System.out.println("****************************************************************************************");
    }


    public static void searchByName() {
        System.out.println("Enter the name you want to search!!!");
        String name = InputMethods.getString();
        System.out.println("************************************ SHOW LIST PRODUCT **************************************************************");
        String alignFormat = "| %-4d | %-15s | %-15s | %-15s | %-7s | %-15s | %-20s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+---------+-------------------------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  |      PRICE      |     STATUS      | DESCRIPTION |       SELLER       |   TIME REMAINING  |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+---------+-------------------------------------+%n");
        for (Product product : productController.searchByName(name)) {
            System.out.format(alignFormat,
                    product.getId(), product.getProductName(), product.getPrice(),
                    (product.isStatus() ? "auction" : "cancelled"),
                    product.getDescription(), product.getUser().getName(), product.getRemainingTime());
        }
        System.out.format("+------+-----------------+-----------------+-----------------+---------+---------------------------------------+%n");
        System.out.println("********************************************************************************************************************");
    }

    public static void giveStar() {
        System.out.println("Enter the User ID to give a star:");
        int id = InputMethods.getInteger();

        if (id == currentUser.getId()) {
            System.out.println("You cannot give yourself a star.");
            return;
        }

        User user = userController.findById(id);
        if (user != null) {
            user.getRole().setPoint(user.getRole().getPoint() + 1);
            userController.save(user);
            System.out.println("Give star successful.");
        } else {
            System.out.println("User not found.");
        }
        roleController.updateRoleByPoint(user.getRole());

    }

    public static void searchUserByName() {
        System.out.println("Enter the name you want to search:");
        String name = InputMethods.getString();
        System.out.println("*****************************************  USER  **********************************************");
        String alignFormat = "| %-4d | %-15s | %-20s | %-10s | %-10s | %-10s  |%n";
        System.out.format("+------+-----------------+----------------------+------------+-----------------------------------+%n");
        System.out.format("|  ID  |    USER NAME    |        EMAIL         |    STATUS    |      ROLE     |    STARS    |%n");
        System.out.format("+------+-----------------+----------------------+------------+---------------------------------+%n");
        for (User user : userController.searchByName(name)) {
            System.out.format(alignFormat,
                    user.getId(), user.getUserName(), user.getEmail(),
                    (user.isStatus() ? "LOCK" : "UNLOCK"),
                    user.getRole().getRole(), user.getRole().getPoint() + " *");
        }
        System.out.format("+------+-----------------+----------------------+------------+---------------------------------+%n");
        System.out.println("*************************************************************************************************");
    }

    public static void report() {
        System.out.println("Enter User Id you want to report");
        int id = InputMethods.getInteger();
        userController = new UserController();
        User reportUser = userController.findById(id);
        Notification reportNotification = new Notification();
        int newId = notificationController.newId();
        String title = "Feedback";
        String sender = currentUser.getUserName();
        String receiver = "admin123";
        boolean status = true;
        String type = "report";
        String reportPerson = reportUser.getUserName();
        System.out.println("Enter the information you want to report");
        String content = InputMethods.getString();
        reportNotification = new Notification(newId, title, content, sender, receiver, new Date(), status, type, reportPerson);
        notificationController.save(reportNotification);
        System.out.println("Report successfully");
    }

    public static void showFeedback() {
        currentUser = new UserController().getUserLogin();
        System.out.println("******************************************************************************* Message ***********************************************************************************");
        String alignFormat = "| %-4d | %-15s | %-15s | %-50s | %-25s | %-7s | %-7s | %-15s |%n";
        System.out.format("+------+-----------------+-----------------+----------------------------------------------------+------------------------------------+---------+---------+----------------+%n");
        System.out.format("|  ID  | REPORTED PERSON |      TITLE      |     CONTENT                                        | SENT DATE                          |  TYPE   |  STATUS |   SENDER       |%n");
        System.out.format("+------+-----------------+-----------------+----------------------------------------------------+------------------------------------+---------+---------+----------------+%n");
        System.out.println("*****************************************************************************************************************************************************************************");
        if (notificationController.findAll().size() == 0) {
            System.out.println("No feedback!!!");
        } else {
            for (Notification notification : notificationController.findAll()) {
                if (currentUser.getUserName().equals(notification.getReceiver())) {
                    System.out.format(alignFormat,
                            notification.getId(), notification.getReportedUser(), notification.getTitle(),
                            notification.getContent(), notification.getTimestamp(),
                            notification.getType(), notification.isStatus(), notification.getSender());
                }
            }
        }
    }


    public static void news() {
        currentUser = new UserController().getUserLogin();
        int id = notificationController.newId();
        String title = "News";
        String sender = currentUser.getUserName();
        String receiver = "all";
        boolean status = false;
        String type = "News";
        Date timestamp = new Date();



        System.out.println("Enter the content (Enter '0' to stop):");
        String content = InputMethods.getString();

        if (!content.equals("0")) {
            Notification notification = new Notification(id, title, content, sender, receiver, timestamp, status, type);
            notificationController.save(notification);
            showNews();
        }
    }

    public static void showNews() {
        System.out.println("********************************* NEWS *****************************************");
        String alignFormat = "|  %-10s | %-45s | %-35s  |%n";
        System.out.format("+-----------------+----------------------------------------------+----------------------------------+%n");
        System.out.format("|    USER NAME    |               CONTENT                        |               DATE               |%n");
        System.out.format("+-----------------+----------------------------------------------+----------------------------------+%n");
        for (Notification notification : new NotificationController().findAll()) {
            if (notification.getTitle().equals("News")) {
                System.out.format(alignFormat,
                        notification.getSender(), notification.getContent(),
                        notification.getTimestamp()
                );
            }
        }
        System.out.format("+-----------------+----------------------------------------------+----------------------------------+%n");
        System.out.println("******************************************************************************************************");
        news();
    }


    public static void clearNews(){
        notificationController.deleteNews();
        System.out.println("Message board has been deleted");
    }


}
