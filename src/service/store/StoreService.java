package service.store;

import config.Config;
import model.store.Store;
import model.user.User;

public class StoreService {

    private Store store = new Config<Store>().readToFile(Config.PATH_STORE);

    public double checkServiceFee(User user) {
        double serviceFee = 0.0;

        if (user.getRole().getRole().equals("New")) {
            serviceFee = 2.5;
        } else if (user.getRole().getRole().equals("Verified")) {
            serviceFee = 2.0;
        } else if (user.getRole().getRole().equals("Trusted")) {
            serviceFee = 1.5;
        } else if (user.getRole().getRole().equals("Active")) {
            serviceFee = 1.0;
        } else if (user.getRole().getRole().equals("Exemplary")) {
            serviceFee = 0.5;
        }

        return serviceFee;
    }

    public void editStore(Store newStore) {
        if (store == null) {
            store = newStore;
        } else {
            store.setStoreBalance(newStore.getStoreBalance()+store.getStoreBalance());
            store.setExchangeRate(newStore.getExchangeRate());
            store.setServiceFee(newStore.getServiceFee());
            store.setRevenue(newStore.getRevenue()+store.getRevenue());
        }

        new Config<Store>().writeToFile(Config.PATH_STORE, store);
    }


    public double checkExchangeRate(User user) {
        double exchangeRate = 0.0;

        if (user.getRole().getRole().equals("New")) {
            exchangeRate = 10.0 + exchangeRate;
        } else if (user.getRole().getRole().equals("Verified")) {
            exchangeRate = 8.5 + exchangeRate;
        } else if (user.getRole().getRole().equals("Trusted")) {
            exchangeRate = 7.0 + exchangeRate;
        } else if (user.getRole().getRole().equals("Active")) {
            exchangeRate = 6.0 + exchangeRate;
        } else if (user.getRole().getRole().equals("Exemplary")) {
            exchangeRate = 5.0 + exchangeRate;
        }

        return exchangeRate;
    }
}
