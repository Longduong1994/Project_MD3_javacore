package view;

import config.Config;
import config.InputMethods;
import controller.StoreController;
import model.store.Store;
import model.user.User;

import java.text.DecimalFormat;

import static view.UserView.userController;

public class StoreView {
    private static StoreController storeController = new StoreController();
    private static User currentUser = userController.getUserLogin();

    public static void StoreManager() {
        while (true) {
            System.out.println("*********************************************** Store *********************************************");
            String alignFormat = " | %-4d | %-15s | %-4d |%-15s | %-4d | %-15s |%-4d | %-15s |%n";
            System.out.format("-+------+-----------------+------+---------------+-------+------------------+-----+----------------+%n");
            System.out.format(alignFormat, 1, "Store", 2, "Edit", 3, "Profit", 4, "Home");
            System.out.format("-+------+-----------------+------+---------------+-------+------------------+-----+----------------+%n");
            System.out.println("****************************************************************************************************");
            System.out.println("Please choose a service...");
            int choose = InputMethods.getInteger();
            switch (choose) {
                case 1:
                    showStore();
                    break;
                case 2:
                    editStore();
                    break;
                case 3:
                    profit();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Please enter 1 to 3!!!");
            }
        }


    }

    public static void findAll() {
        Store store =  new Config<Store>().readToFile(Config.PATH_STORE);
        System.out.println(store);
    }

    public static void showStore() {
        Store store = new Config<Store>().readToFile(Config.PATH_STORE);
        System.out.println("******************************************* STORE INFORMATION *****************************************");
        String alignFormat = "| %-12s | %-15s | %-15s | %-14s | %-15s | %-14s  |%n";

        System.out.format("+--------------+-----------------+-----------------+---------------+----------------+----------------+%n");
        System.out.format("|     RATE     |  SERVICE FEE    |     BALANCE     |    REVENUE    | MAINTAINING FEE|     PROFIT     |%n");
        System.out.format("+--------------+-----------------+-----------------+---------------+----------------+----------------+%n");
        System.out.format(alignFormat, store.getExchangeRate(), store.getServiceFee(),
                store.getStoreBalance(), store.getRevenue(),
                (store.getMaintenanceFee() != 0 ? store.getMaintenanceFee() : "Not Yet"),
                (store.getProfit() != 0 ? store.getProfit() : "Not Yet"));
        System.out.format("+--------------+-----------------+-----------------+---------------+----------------+----------------+%n");
        System.out.println("*******************************************************************************************************");


    }

    // Định dạng số thập phân với 2 chữ số sau dấu phẩy
    private static String formatDecimal(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(number);
    }



    public static void editStore() {
        Store newStore = new Store();
        System.out.println("Enter new RATE");
        double newRate = InputMethods.getDouble();
        System.out.println("Enter new ServiceFee");
        double newServiceFee = InputMethods.getDouble();
        newStore.setServiceFee(newServiceFee);
        newStore.setExchangeRate(newRate);
        storeController.editStore(newStore);
        System.out.println("Updated successfully");
    }

    public static void profit() {
        Store store = new Config<Store>().readToFile(Config.PATH_STORE);
        System.out.println("enter this month's maintenance fee :");
        double maintenanceFee = InputMethods.getDouble();
        store.getMaintenanceFee();
        double profit = (store.getRevenue() - maintenanceFee) / store.getRevenue() * 100;
        formatDecimal(profit);
        store.setProfit(profit);
        store.setRevenue(0);
        store.setStoreBalance(store.getStoreBalance()+store.getRevenue());
        System.out.println("Profit this month is " + profit);
    }
}
