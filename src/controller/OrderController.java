package controller;

import model.cart.Order;
import service.IGenericService;
import service.cart.OrderService;

import java.util.List;

public class OrderController implements IGenericService<Order,Integer> {
    OrderService orderService = new OrderService();
    @Override
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @Override
    public void save(Order order) {
        orderService.save(order);
    }

    @Override
    public Order findById(Integer id) {
        return orderService.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        orderService.deleteById(id);
    }
    public int newId(){
        return orderService.newID();
    }
}
