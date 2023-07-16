package view;

import config.Config;
import config.InputMethods;
import controller.AuctionController;
import controller.CartController;
import controller.ProductController;
import controller.StoreController;
import model.cart.CartItem;
import model.product.Product;
import model.store.Store;
import model.user.Auction;
import model.user.User;

import java.util.ArrayList;
import java.util.List;


import static view.UserView.userController;

public class CartView {
    private static ProductController productController = new ProductController();
    private static CartController cartController = new CartController();
    private static User currentUser = userController.getUserLogin();
    private static StoreController storeController = new  StoreController();
    private static AuctionController auctionController = new AuctionController();

    public static void auction() {
        if (currentUser.getBalance() < storeController.checkServiceFee(currentUser)){
            System.out.println("You don't have enough money to use the service");
            return;
        }

        System.out.println("Enter the product ID you want to bid on:");
        int id = InputMethods.getInteger();
        productController = new ProductController();
        Product product = productController.findById(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        if (!product.isStatus()) {
            System.out.println("Auction has ended");
            return;
        }

        User currentUser = userController.getUserLogin();
        if (currentUser.getUserName().equals(product.getUser().getUserName())) {
            System.out.println("You are not allowed to bid on your own product.");
            return;
        }

        CartItem existingCartItem = null;
        for (CartItem cartItem : cartController.findAll()) {
            if (cartItem.getProduct().getId() == product.getId()) {
                existingCartItem = cartItem;
                break;
            }
        }

        int idAuction = auctionController.newId();

        if (existingCartItem != null) {
            Auction existingAuction = null;
            for (Auction auction : existingCartItem.getAuctions()) {
                if (auction.getUser().getUserName().equals(currentUser.getUserName())) {
                    existingAuction = auction;
                    break;
                }
            }

            if (existingAuction != null) {
                System.out.println("The current product price is " + existingCartItem.getAuctionPrice() + " $!");
                System.out.println("Enter your new bid price:");
                double newPrice = InputMethods.getDouble();
                if (newPrice <= existingCartItem.getAuctionPrice()) {
                    System.out.println("Your new bid price must be higher than the current bid price.");
                    return;
                }

                currentUser.setBalance(currentUser.getBalance() - newPrice + existingCartItem.getAuctionPrice()- storeController.checkServiceFee(currentUser));
                currentUser.setAuctionPrice(newPrice);
                existingAuction.setPrice(newPrice);
                auctionController.save(existingAuction);
                userController.save(currentUser);
                existingCartItem.setAuctionPrice(newPrice);
                product.setPriceUpdate(newPrice);
                productController.save(product);
                cartController.save(existingCartItem);
                Store store = new Store();
                store.setStoreBalance(storeController.checkServiceFee(currentUser));
                storeController.editStore(store);


                System.out.println("Updated your bid for the product successfully!");
            } else {
                System.out.println("The current product price is " + existingCartItem.getAuctionPrice() + " $!");
                System.out.println("Enter your bid price:");
                double newPrice = InputMethods.getDouble();
                if (newPrice <= existingCartItem.getAuctionPrice()) {
                    System.out.println("Your new bid price must be higher than the current bid price.");
                    return;
                }
                currentUser.setBalance(currentUser.getBalance() - newPrice + existingCartItem.getAuctionPrice());
                currentUser.setAuctionPrice(newPrice);
                userController.save(currentUser);
                existingCartItem.setAuctionPrice(newPrice);
                product.setPriceUpdate(newPrice);
                productController.save(product);
                Store store = new Store();
                store.setRevenue(storeController.checkServiceFee(currentUser));
                storeController.editStore(store);

                List<Auction> updatedAuctions = new ArrayList<>(existingCartItem.getAuctions());
                Auction newAuction = new Auction(idAuction, currentUser, newPrice);
                updatedAuctions.add(newAuction);

                existingCartItem.setAuctions(updatedAuctions);
                cartController.save(existingCartItem);

                System.out.println("Order successful! Please wait for the auction time to expire to receive the result.");
            }
        } else {
            System.out.println("The starting price of the product is: " + product.getPrice() + " $!");
            System.out.println("Enter your bid price:");
            double bidPrice = InputMethods.getDouble();
            if (bidPrice < product.getPrice()) {
                System.out.println("Your bid price must not be lower than the starting price.");
                return;
            }
            if (currentUser.getBalance() < bidPrice + storeController.checkServiceFee(currentUser)) {
                System.out.println("You don't have enough money. Please deposit more money to continue bidding.");
                return;
            }

            currentUser.setBalance(currentUser.getBalance() - bidPrice - storeController.checkServiceFee(currentUser));
            currentUser.setAuctionPrice(bidPrice);
            product.setPriceUpdate(bidPrice);
            productController.save(product);
            userController.save(currentUser);

            Auction newAuction = new Auction(idAuction, currentUser, bidPrice);
            List<Auction> auctions = new ArrayList<>();
            auctions.add(newAuction);

            CartItem newCartItem = new CartItem(cartController.newId(), product, bidPrice, true, auctions);
            cartController.save(newCartItem);

            Store store = new Store();
            store.setRevenue(storeController.checkServiceFee(currentUser));
            storeController.editStore(store);

            System.out.println("Order successful! Please wait for the auction time to expire to receive the result.");
        }
    }



    public static void cancelAuction() {
        User currentUser = userController.getUserLogin();
        Store store = new Config<Store>().readToFile(Config.PATH_STORE);
        System.out.println("Enter the product ID you want to cancel Auction:");
        int id = InputMethods.getInteger();

        CartItem cartItem = cartController.findById(id);
        if (cartItem == null) {
            System.out.println("Product not found in the auction.");
            return;
        }

        if (!currentUser.getUserName().equals(cartItem.getProduct().getUser().getUserName())) {
            System.out.println("You are not authorized to cancel the auction for this product.");
            return;
        }

        double refundAmount = cartItem.getAuctionPrice() - storeController.checkServiceFee(currentUser);
        currentUser.setBalance(currentUser.getBalance() + refundAmount);
        cartController.deleteById(id);
        store.setRevenue(store.getRevenue()+storeController.checkServiceFee(currentUser));
        storeController.editStore(store);
        System.out.println("Auction canceled successfully. Refund amount: " + refundAmount + " $. Service fee: " + storeController.checkServiceFee(currentUser) + " $!");
    }


    public static void showMyAuction() {
        User currentUser = userController.getUserLogin();
        System.out.println("************************************ MY AUCTION ************************************");
        String alignFormat = "| %-4s | %-15s | %-15s | %-15s | %-7s | %-15s | %-15s | %-17s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+--------------------+-------------------+-----------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  |      PRICE      |     STATUS      | DESCRIPTION |       SELLER       |   TIME REMAINING  | NUMBER OF PEOPLE  |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+--------------------+-------------------+-----------------+%n");

        boolean flag = false;
        for (CartItem cartItem : cartController.findAll()) {
            for (Auction auction :cartItem.getAuctions()) {
                if(auction.getUser().getUserName().equals(currentUser.getUserName())){
                    System.out.format(alignFormat,
                            cartItem.getId(), cartItem.getProduct().getProductName(),cartItem.getAuctionPrice(),
                            (cartItem.isStatus() ? "AUCTION" : "CANCELLED"),
                            cartItem.getProduct().getDescription(), cartItem.getProduct().getUser().getUserName(), cartItem.getProduct().getRemainingTime(),cartItem.getAuctions().size());
                    flag = true;
                }
            }
        }

        if (!flag) {
            System.out.println("You have no products in your auction list.");
        }
        System.out.format("+------+-----------------+-----------------+-----------------+---------+-------------------------------------+%n");
    }

    public static void showCart(){
        System.out.println("**************************************************** CART ***********************************************************************");
        String alignFormat = "| %-4d | %-15s | $%-14.2f | $%-14.2f | %-10s | %-18s | %-17s |  %-15s |%n";
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+------------------+-------------------+------------------+%n");
        System.out.format("|  ID  |   PRODUCT NAME  | STARTING PRICE  | CURRENT PRICE   |   STATUS    |       SELLER     |   TIME REMAINING  | NUMBER OF PEOPLE |%n");
        System.out.format("+------+-----------------+-----------------+-----------------+-------------+------------------+-------------------+------------------+%n");
        for (CartItem cartItem : cartController.findAll()) {
            System.out.format(alignFormat,
                    cartItem.getId(), cartItem.getProduct().getProductName(), cartItem.getProduct().getPrice(), cartItem.getAuctionPrice(),
                    (cartItem.isStatus() ? "AUCTION" : "END"),
                    cartItem.getProduct().getUser().getUserName(), cartItem.getProduct().getRemainingTime(), cartItem.getAuctions().size() );
        }
    }


}
