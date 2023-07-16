package controller;

import model.cart.CartItem;
import service.IGenericService;
import service.cart.CartService;

import java.util.List;

public class CartController implements IGenericService<CartItem,Integer> {
    CartService cartService = new CartService();
    @Override
    public List<CartItem> findAll() {
        return cartService.findAll();
    }

    @Override
    public void save(CartItem cartItem) {
        cartService.save(cartItem);
    }

    @Override
    public CartItem findById(Integer id) {
        return cartService.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        cartService.deleteById(id);
    }
    public int newId(){
        return cartService.newID();
    }
}
