package service.cart;

import config.Config;
import model.cart.CartItem;
import service.IGenericService;

import java.util.List;

public class CartService implements IGenericService<CartItem,Integer> {
    List<CartItem> carts = new config.Config<CartItem>().readFromFile(Config.PATH_CART);

    @Override
    public List<CartItem> findAll() {
        return carts;
    }

    @Override
    public void save(CartItem cartItem) {
        if(findById(cartItem.getId())== null){
            // add
            carts.add(cartItem);
        }else {
            // update
            carts.set(carts.indexOf(findById(cartItem.getId())),cartItem);
        }
        new Config<CartItem>().writeFromFile(Config.PATH_CART, carts);
    }

    @Override
    public CartItem findById(Integer id) {
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getId() == id) {
                return carts.get(i);
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getId() == id) {
                carts.remove(carts.get(i));
            }
        }
        new Config<CartItem>().writeFromFile(Config.PATH_CART, carts);
    }

    public int newID(){
        int max = 0;
        for (CartItem cartItem : carts) {
            if(cartItem.getId()>max)
                max = cartItem.getId();
        }
        return max+1;
    }
}
