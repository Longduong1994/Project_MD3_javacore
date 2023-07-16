package service.cart;


import config.Config;
import model.cart.Order;
import service.IGenericService;

import java.util.List;

public class OrderService implements IGenericService<Order,Integer> {
    List<Order> orders = new config.Config<Order>().readFromFile(config.Config.PATH_ORDER);


    @Override
    public List<Order> findAll() {
        return orders;
    }

    @Override
    public void save(Order order) {
        if(findById(order.getId())== null){
            // add
            orders.add(order);
        }else {
            // update
            orders.set(orders.indexOf(findById(order.getId())),order);
        }
        new Config<Order>().writeFromFile(Config.PATH_ORDER, orders);
    }

    @Override
    public Order findById(Integer id) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == id) {
                return orders.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == id) {
                orders.remove(orders.get(i));
            }
        }
    }

    public int newID(){
        int max = 0;
        for (Order orders : orders) {
            if(orders.getId()>max)
                max = orders.getId();
        }
        return max+1;
    }
}
