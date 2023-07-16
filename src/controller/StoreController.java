package controller;


import model.store.Store;
import model.user.User;
import service.store.StoreService;

import java.util.List;

public class StoreController {
    StoreService storeService = new StoreService();

    public void  editStore(Store newStore) {
        storeService.editStore(newStore);
    }

    public double checkServiceFee(User user) {
        return storeService.checkServiceFee(user);
    }
    public double checkRate(User user) {
       return storeService.checkExchangeRate(user);
    }
}
