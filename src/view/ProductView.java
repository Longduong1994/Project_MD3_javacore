package view;

import config.Config;
import config.InputMethods;
import controller.*;
import model.cart.CartItem;
import model.cart.Order;
import model.product.Product;
import model.store.Store;
import model.user.User;

import java.util.Date;
import java.util.List;


public class ProductView {
    private static OrderController orderController = new OrderController();
    private static ProductController productController = new ProductController();
    private static UserController userController = new UserController();
    private static StoreController storeController = new StoreController();
    private static CartController cartController = new CartController();

    private static User currentUser = userController.getUserLogin();

    public static void ProductManager() {
        while (true) {
            System.out.println("                                    ******************************** PRODUCT ***********************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s  |%n";
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 1, "ShowAll", 2, "Lock Auction", 3, "Cancel Auction", 4, "Search Product", 5, "Home");
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.println("                                    ****************************************************************************");
            System.out.println("Please choose a service...");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    showProduct();
                    break;
                case 2:
                    editStatusProduct();
                    break;
                case 3:
                    deleteProductByStatus();
                    break;
                case 4:
                    FeatureView.searchByName();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Please re-enter your selection!!!");

            }
        }
    }

    public static void auction() {
        while (true) {
            System.out.println("                                   ******************************TL AUCTION FLOOR ********************************");
            String alignFormat = "| %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s | %-4d | %-15s |%n";
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 1, "Auction floor", 2, " Sell ", 3, "My Product", 4, "Cancel sale", 5, "Auction", 6, "Cancel auction ");
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.format(alignFormat, 7, "My Auction", 8, "Auction details", 9, "History Auction", 10, "Update Order", 11,"Confirm Order" , 12, "Home");
            System.out.format("+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+------+-----------------+%n");
            System.out.println("                                    ****************************************************************************");
            System.out.println("Please choose a service...");

            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    showProduct();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    showMyProduct();
                    break;
                case 4:
                    editStatusProduct();
                    break;
                case 5:
                    CartView.auction();
                    break;
                case 6:
                    CartView.cancelAuction();
                    break;
                case 7:
                    CartView.showMyAuction();
                    break;
                case 8:
                    showProductAuction();
                    break;
                case 9:
                    historyOrder();
                    break;
                case 10:
                    OrderView.orderUpdate();
                    break;
                case 11:
                    confirmOrder();
                    break;
                case 12:
                    return;
                default:
                    System.out.println("Please re-enter your selection!!!");

            }
        }
    }



    public static void addProduct() {
        User currentUser = userController.getUserLogin();
        if (currentUser.getBalance() < storeController.checkServiceFee(currentUser)) {
            System.out.println("You don't have enough money to place an order. Service fee is " + storeController.checkServiceFee(currentUser) + " $!");
        } else {
            int id = productController.newId();
            boolean status = true;
            System.out.println("Enter product Name");
            String productName = InputMethods.getString();
            System.out.println("Enter product Description");
            String description = InputMethods.getString();
            System.out.println("Enter product price");
            double price = InputMethods.getDouble();
            double priceUpdate = price;
            System.out.println("Enter product end time (in seconds)");
            int endTimeSeconds = InputMethods.getInteger();
            Date currentTime = new Date();
            long endTimeMillis = currentTime.getTime() + (endTimeSeconds * 1000);
            Date endTime = new Date(endTimeMillis);
            double storeServiceFee = storeController.checkServiceFee(currentUser);
            Product newProduct = new Product(id, productName, description, price, priceUpdate, currentUser, endTime, status);
            currentUser.setBalance(currentUser.getBalance() - storeServiceFee);
            Store store = new Config<Store>().readToFile(Config.PATH_STORE);
            double newStoreRevenue = store.getRevenue() + storeServiceFee;
            store.setRevenue(newStoreRevenue);
            storeController.editStore(store);
            productController.save(newProduct);
//            newProduct.startCountdown(endTimeSeconds);
            productController.startCountdown(endTimeSeconds, newProduct);
            System.out.println("Products start to be auctioned");
        }
    }

    public static void showProduct() {
        List<Product> productList = productController.findAll();

        System.out.println("*********************************************** SHOW LIST PRODUCT ***********************************************************");
        String alignFormat = "| %-4d | %-15s | $%-14.2f | %-15s | %-15s | %-20s | %-18s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+-----------------+----------------------+---------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  |     PRICE       |     STATUS      |   DESCRIPTION   |       SELLER         |   TIME REMAINING    |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+-----------------+----------------------+---------------------+%n");
        for (Product product : productList) {
            System.out.format(alignFormat,
                    product.getId(), product.getProductName(), product.getPrice(),
                    (product.isStatus() ? "AUCTION" : "THE END"),
                    product.getDescription(), product.getUser().getName(), product.getRemainingTime());
        }

        System.out.format("+------+-----------------+-----------------+-----------------+-----------------+----------------------+---------------------+%n");
        System.out.println("****************************************************************************************************************************");
    }


    public static void editStatusProduct() {
        Store store = new Config<Store>().readToFile(Config.PATH_STORE);
        System.out.println("Enter the product ID you want to edit");
        int id = InputMethods.getInteger();
        Product product = productController.findById(id);

        if (product != null) {
            if (currentUser.getUserName().equals(product.getUser().getUserName())) {
                if (currentUser.getBalance() >= (storeController.checkServiceFee(currentUser))) {
                    productController.editProductStatus(id);
                    currentUser.setBalance(currentUser.getBalance() - (storeController.checkServiceFee(currentUser)));
                    store.setRevenue(store.getRevenue()+storeController.checkServiceFee(currentUser));
                    storeController.editStore(store);
                } else {
                    System.out.println("Your balance is not enough");
                }
            } else {
                System.out.println("You are not the owner of this product");
            }
        } else {
            System.out.println("Invalid product ID");
        }
    }

    public static void showMyProduct() {
        User currentUser = userController.getUserLogin();
        System.out.println("************************************************* MY PRODUCT **********************************************************");
        String alignFormat = "| %-4d | %-15s | $%-14.2f | %-15s | %-10s | %-18s | %-17s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+--------------------+-------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  | STARTING PRICE  |     STATUS      | DESCRIPTION |       SELLER       |   TIME REMAINING  |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+--------------------+-------------------+%n");

        boolean flag = false;

        for (Product product : productController.findAll()) {
            if (product.getUser().getUserName().equals(currentUser.getUserName())) {
                System.out.format(alignFormat,
                        product.getId(), product.getProductName(), product.getPrice(),
                        (product.isStatus() ? "AUCTION" : "END"),
                        product.getDescription(), product.getUser().getName(), product.getRemainingTime());
                flag = true;
            }
        }

        if (!flag) {
            System.out.println("There are no products in your list yet.");
        }

        System.out.format("+------+-----------------+-----------------+-----------------+------------+--------------------+-------------------+%n");
    }


    public static void deleteProductByStatus() {
        System.out.println("Delete Products whose status is false");
        System.out.println("Enter number 1 to delete");
        int confirm = InputMethods.getInteger();

        if (confirm == 1) {
            productController.deleteByStatus();
            System.out.println("Products with false status have been deleted.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    public static void showProductAuction() {
        System.out.println("Enter the Product Id you want to track");
        int id = InputMethods.getInteger();
        System.out.println("**************************************************** MY PRODUCT ***********************************************************************");
        String alignFormat = "| %-4d | %-10s | $%-10s | $%-10s | %-10s | %-18s | %-17s |  %-15s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+------------------+-------------------+------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  | STARTING PRICE  | CURRENT PRICE   |   STATUS    |       SELLER     |   TIME REMAINING  | NUMBER OF BIDDERS |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+------------------+-------------------+------------------+%n");

        Product product = productController.findById(id);
        cartController = new CartController();
        if (product != null) {
            boolean flag = false;

            for (CartItem cartItem : cartController.findAll()) {
                if (cartItem.getProduct().getId() == product.getId()) {
                    System.out.format(alignFormat,
                            product.getId(), product.getProductName(), product.getPrice(), cartItem.getAuctionPrice(),
                            (product.isStatus() ? "AUCTION" : "END"),
                            cartItem.getProduct().getUser().getUserName(), product.getRemainingTime(), cartItem.getAuctions().size());
                    flag = true;
                }
            }

            if (!flag) {
                System.out.format(alignFormat,
                        product.getId(), product.getProductName(), product.getPrice(), product.getPrice(),
                        (product.isStatus() ? "AUCTION" : "END"), product.getUser().getUserName(), product.getRemainingTime(), 0);
            }
        } else {
            System.out.println("Product not found.");
        }

        System.out.format("+------+-----------------+-----------------+-----------------+-------------+------------------+-------------------+------------------+%n");
    }


    public static void historyOrder() {
        User currentUser = userController.getUserLogin();
        System.out.println("*********************************************************************** MY ORDER **********************************************************************************");
        String alignFormat = "| %-4d | %-15s | $%-12s | %-15s | %-11s | %-11s | %-18s | %-35s | %-16s |%n";
        System.out.format("+------+-----------------+---------------+-----------------+-------------+-------------+-------------------+--------------------------------------+------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  |    PRICE      |     BUYER       |   RECEIVER  |   ADDRESS   |       PHONE       |     DATE                             |     STATUS       |%n");
        System.out.format("+------+-----------------+---------------+-----------------+-------------+-------------+-------------------+--------------------------------------+------------------+%n");
        int orderCount = 0;

        for (Order order : new OrderController().findAll()) {
            if (order.getBuyer().getUserName().equals(currentUser.getUserName())) {
                System.out.format(alignFormat,
                        order.getId(), order.getCartItem().getProduct().getProductName(), order.getCartItem().getAuctionPrice(), order.getBuyer().getUserName(),
                        (order.getReceiver() != null ? order.getReceiver() : "Not updated"),
                        (order.getAddress() != null ? order.getAddress() : "Not updated"),
                        (order.getPhoneNumber() != null ? order.getPhoneNumber() : "Not updated"),
                        order.getDate(), (order.isStatus() ? "WAITING" : "CONFIRM")
                );
                orderCount++;
            }
        }
        System.out.format("+------+-----------------+---------------+-----------------+-------------+-------------+-------------------+--------------------------------------+------------------+%n");
        System.out.println("*******************************************************************************************************************************************************************");
        if (orderCount == 0) {
            System.out.println("No orders found.");
        }
    }




    public static void confirmOrder(){
        new OrderController().findAll();
        System.out.println("Enter ID order you want to confirm");
        int id = InputMethods.getInteger();
        Order orderConfirm = orderController.findById(id);
        orderConfirm.setStatus(false);
        orderController.save(orderConfirm);
        System.out.println("Order confirmation successful");
    }

}
