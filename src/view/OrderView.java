package view;

import config.Config;
import config.InputMethods;
import controller.*;
import model.cart.CartItem;
import model.cart.Order;
import model.product.Product;
import model.store.Notification;
import model.store.Store;
import model.user.Auction;
import model.user.User;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static view.UserView.userController;


public class OrderView {
    private static OrderController orderController = new OrderController();
    private static CartController cartController = new CartController();
    private static NotificationController notificationController = new NotificationController();
    private static StoreController storeController = new StoreController();
    private static User currentUser = userController.getUserLogin();

    private static ProductController productController = new ProductController();

    public static void OrderManager() {
        while (true) {
            System.out.println("*********************************************** ORDER MANAGER ************************************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
            System.out.format("+------+-----------------+------+-------------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 1, "List Order", 2, "Statistics by name",3,"High Deals" ,4, "Home");
            System.out.format("+------+-----------------+------+-------------------+------+-----------------+------+-----------------+%n");
            System.out.println("********************************************************************************************************");
            System.out.println("Please choose a service...");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    showOrder();
                    break;
                case 2:
                    statistics();
                    break;
                case 3:
                    highest();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Please enter 1 to 4!!!");
            }
        }
    }
    public static void statistics(){
        System.out.println("Enter the Username ");
        String username = InputMethods.getString();
        int count = 0;
        for (Order order: orderController.findAll()) {
            if (order.getBuyer().getUserName().equals(username)) {
                count++;
            }
        }
        System.out.println("Order number of " +username + " is " + count );
    }


    private static void showOrder() {
         new OrderController().findAll();

        System.out.println("****************************************************** LIST ORDER ********************************************************************");
        String alignFormat = "| %-4d | %-15s | $%-13s | %-15s | %-15s | $%-13s | %-35s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+---------------+--------------+----------------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  |      PRICE      |     SELLER      |     BUYER     |    TOTAL     |          DATE              |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+---------------+--------------+----------------------------+%n");
        for (Order order : orderController.findAll()) {
            System.out.format(alignFormat,
                    order.getId(),
                    order.getCartItem().getProduct().getProductName(),
                    order.getCartItem().getProduct().getPrice(),
                    order.getCartItem().getProduct().getUser().getUserName(),
                    order.getBuyer().getUserName(),
                    order.getTotal(),
                    order.getDate());
        }
        System.out.format("+------+-----------------+-----------------+-----------------+----------------+------------+--------------------------+%n");
        System.out.println("*************************************************************************************************************************************");
    }

    public static void orderUpdate() {
        System.out.println("Enter the ID of the order you want to confirm");
        int id = InputMethods.getInteger();
        Order orderConfirm = orderController.findById(id);
        if (orderConfirm == null) {
            System.out.println("Order not found");
            return;
        }

        System.out.println("Enter consignee");
        String receiver = InputMethods.getString();

        System.out.println("Enter your delivery address");
        String address = InputMethods.getString();

        String phone;
        boolean validPhoneNumber = false;
        while (!validPhoneNumber) {
            System.out.println("Enter the recipient's phone number");
            phone = InputMethods.getString();
            if (Config.setValidatePhoneNumber(phone)) {
                validPhoneNumber = true;
                orderConfirm.setPhoneNumber(phone);
            } else {
                System.out.println("Invalid phone number format. Phone number must have 10 digits and start with 0");
            }
        }

        orderConfirm.setAddress(address);
        orderConfirm.setReceiver(receiver);
        orderController.save(orderConfirm);

        System.out.println("Successfully updated order information");
    }


    public static void highest() {
        new OrderController().findAll();
        System.out.println("****************************************************** HIGHEST DEAL ********************************************************************");
        String alignFormat = "| %-4d | %-15s | $%-13s | %-15s | %-15s | $%-13s | %-26s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+---------------+------------+----------------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  |      PRICE      |     SELLER      |     BUYER     |    TOTAL   |          DATE              |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+---------------+------------+----------------------------+%n");
        for (Order order : orderController.findAll()) {
            if (order.getCartItem().getProduct().getPrice() > 5000)
            System.out.format(alignFormat,
                    order.getId(),
                    order.getCartItem().getProduct().getProductName(),
                    order.getCartItem().getProduct().getPrice(),
                    order.getCartItem().getProduct().getUser().getUserName(),
                    order.getBuyer().getUserName(),
                    order.getTotal(),
                    order.getDate());
        }
        System.out.format("+------+-----------------+-----------------+-----------------+----------------+------------+--------------------------+%n");
        System.out.println("*************************************************************************************************************************************");
    }




    public void saveOrder(long remainingTime ) {
        if (remainingTime <= 0) {
            checkSuccessfulBidders();
        }
    }

    public void checkSuccessfulBidders() {
        List<CartItem> cartItemsToDelete = new ArrayList<>();

        for (CartItem cartItem : new CartController().findAll()) {
            if (cartItem.getProduct().getRemainingTime() <= 0 && cartItem.isStatus()) {
                Product product = cartItem.getProduct();
                User successfulBidder = null;
                boolean flag = false;
                for (Auction auction : cartItem.getAuctions()) {
                    if (auction.getPrice() == cartItem.getAuctionPrice()) {
                        successfulBidder = auction.getUser();
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    double rate = storeController.checkRate(successfulBidder);
                    successfulBidder.setBalance(successfulBidder.getBalance() - rate);
                    userController.save(successfulBidder);
                    boolean status = true;
                    Order newOrder = new Order(orderController.newId(), successfulBidder, cartItem, cartItem.getAuctionPrice(), new Date(), status);
                    orderController.save(newOrder);
                    productController.save(product);
                    Store store = new Config<Store>().readToFile(Config.PATH_STORE);
                    store.setRevenue(storeController.checkRate(successfulBidder) * cartItem.getAuctionPrice() / 100);
                    storeController.editStore(store);
                    successfulNotice(successfulBidder.getUserName(),product.getProductName());
                } else {
                    User sellUser = product.getUser();
                    sellUser.setBalance(sellUser.getBalance() + product.getPrice());
                    userController.save(sellUser);
                    returnNotice(sellUser.getUserName(), product.getProductName());
                }
                for (Auction auction : cartItem.getAuctions()) {
                    if (auction.getUser() != successfulBidder) {
                        double refund = auction.getUser().getAuctionPrice();
                        auction.getUser().setBalance(auction.getUser().getBalance() + refund);
                        auction.getUser().setAuctionPrice(0);
                        userController.save(auction.getUser());
                        unsuccessfulNotice(auction.getUser().getUserName(), product.getProductName());
                    }
                }
                cartItem.setStatus(false);
                new CartController().save(cartItem);
                cartItemsToDelete.add(cartItem);
            }
        }

        for (CartItem cartItem : cartItemsToDelete) {
            cartController.deleteById(cartItem.getId());
        }
    }




    public void successfulNotice(String name, String productName) {
        String title = "Successful Bid";
        String content = "Congratulations! Your bid has been successful." + productName;
        String sender = "Auction system";
        Date date = new Date();
        boolean status = true;
        String type = "Bid";
        String receiver = name;
        int newId = notificationController.newId();
        Notification newNotification = new Notification(newId, title, content, sender, receiver, date, status, type);
        notificationController.save(newNotification);
    }

    public void unsuccessfulNotice(String name, String productName) {
        String title = "Unsuccessful Bid";
        String content = "Unfortunately, your bid was not successful. " + productName;
        String sender = "Auction system";
        Date date = new Date();
        boolean status = true;
        String type = "Bid";
        String receiver = name;
        int newId = notificationController.newId();
        Notification newNotification = new Notification(newId, title, content, sender, receiver, date, status, type);
        notificationController.save(newNotification);
    }

    public void returnNotice(String name, String productName){
        String title = "Return Bid";
        String content = "Sorry, your product has not been priced yet " + productName;
        String sender = "Auction system";
        Date date = new Date();
        boolean status = true;
        String type = "Return";
        String receiver = name;
        int newId = notificationController.newId();
        Notification newNotification = new Notification(newId, title, content, sender, receiver, date, status, type);
        notificationController.save(newNotification);
    }


}
