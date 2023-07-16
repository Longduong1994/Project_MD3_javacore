package view;


import config.InputMethods;
import controller.NotificationController;
import controller.UserController;

import model.store.Notification;
import model.user.User;


import java.util.Date;
import java.util.List;

public class NotificationView {
    public static NotificationController notificationController = new NotificationController();
    public static UserController userController = new UserController();
    public static User currentUser = userController.getUserLogin();

    public static void NotificationManager() {
        while (true) {
            System.out.println("************************************************************************** Notification ***************************************************************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%-4d | %-15s |%n";
            System.out.format("+------+-----------------+------+---------------------+------+--------------------------+------+-----------------+------+----------------+------+----------------+%n");
            System.out.format(alignFormat, 1, "Message sent", 2, "Create notification", 3, "Delete old notifications", 4, "Show Feedback", 5, "Clear News",6, "Home ");
            System.out.format("+------+-----------------+------+---------------------+------+--------------------------+------+-----------------+------+----------------+------+----------------+%n");
            System.out.println("********************************************************************************************************************************************************************");
            System.out.println("Please choose a service...");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    showNotification();
                    break;
                case 2:
                    createdNotification();
                    break;
                case 3:
                    deleteByStatus();
                    break;
                case 4:
                    FeatureView.showFeedback();
                    break;
                case 5:
                    FeatureView.clearNews();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Please enter 1 to 6!!!");
            }
        }
    }

    public static void messageUser() {
        while (true) {
            System.out.println("************************************************************ MESSAGE ********************************************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%-4d | %-15s |%n";
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 1, "My Message", 2, "Send Message", 3, " Delete Message", 4, "News", 5, "Home");
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.println("*****************************************************************************************************************************");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    myMessage();
                    break;
                case 2:
                    createdNotification();
                    break;
                case 3:
                    deleteById();
                    break;
                case 4:
                    FeatureView.showNews();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Please enter 1 to 5!!!");
            }
        }
    }

    private static void showNotification() {
        List<Notification> notifications = notificationController.findAll();

        System.out.println("*************************************************** Notification **************************************************************");
        String alignFormat = "| %-4d | %-15s | %-16s | %-45s | %-35s | %-10s |%n";
        System.out.format("+------+-----------------+-----------------+----------------------------------------+--------------------------+------------+------------+%n");
        System.out.format("|  ID  |   RECEIVER      |      TITLE      |          CONTENT                       |        SENT DATE         |   TYPE     |   STATUS   |%n");
        System.out.format("+------+-----------------+-----------------+----------------------------------------+--------------------------+------------+------------+%n");
        for (Notification notification : notifications) {
            if (notification.getSender().equals("Auction system"))
                System.out.format(alignFormat,
                        notification.getId(), notification.getReceiver(), notification.getTitle(),
                        notification.getContent(), notification.getTimestamp(),
                        notification.getType(), notification.isStatus());
        }
        System.out.format("+------+-----------------+-----------------+----------------------------------------+--------------------------+------------+------------+%n");
        System.out.println("***********************************************************************************************************************");
    }

    public static void myMessage() {
        User currentUser = userController.getUserLogin();
        System.out.println("******************************************************************************* Message ***********************************************************************************");
        String alignFormat = "| %-4d | %-15s | %-15s | %-50s | %-25s | %-7s | %-7s | %-15s |%n";
        System.out.format("+------+-----------------+-----------------+----------------------------------------------------+------------------------------------+---------+---------+----------------+%n");
        System.out.format("|  ID  |   RECEIVER      |      TITLE      |     CONTENT                                        | SENT DATE                          |  TYPE   |  STATUS |   SENDER       |%n");
        System.out.format("+------+-----------------+-----------------+----------------------------------------------------+------------------------------------+---------+---------+----------------+%n");
        System.out.println("*****************************************************************************************************************************************************************************");
        if (notificationController.findAll().size() == 0) {
            System.out.println("No notification!!!");
        } else {
            for (Notification notification : new  NotificationController().findAll()) {
                if (currentUser.getUserName().equals(notification.getReceiver()) && notification.isStatus() == true && !notification.getTitle().equals("News")) {
                    System.out.format(alignFormat,
                            notification.getId(), notification.getReceiver(), notification.getTitle(),
                            notification.getContent(), notification.getTimestamp(),
                            notification.getType(), notification.isStatus(), notification.getSender());
                }
            }
        }
    }

    public static void createdNotification() {
        User currentUser = userController.getUserLogin();
        System.out.println(" Enter the Id of the person you want to send the notification to");
        int id = InputMethods.getInteger();
        String receiver = userController.findById(id).getUserName();
        int newId = notificationController.newId();
        System.out.println("Enter the title!!!");
        String title = InputMethods.getString();
        System.out.println("Enter the content ");
        String content = InputMethods.getString();
        String sender = currentUser.getUserName();
        Date date = new Date();
        boolean status = true;
        String type = "message";
        Notification newNotification = new Notification(newId, title, content, sender, receiver, date, status, type);
        notificationController.save(newNotification);
        System.out.println("Create success message");
    }

    public static void deleteByStatus() {
        System.out.println("Delete Message whose status is false");
        System.out.println("Enter number 1 to delete");
        int confirm = InputMethods.getInteger();

        if (confirm == 1) {
            notificationController.deleteByStatus();
            System.out.println("Products with false status have been deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public static void deleteById() {
        System.out.println("Enter Message ID to delete");
        int id = InputMethods.getInteger();
        notificationController.deleteById(id);
        System.out.println("Delete successfully");
    }




}
