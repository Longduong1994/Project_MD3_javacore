package service.product;


import config.Config;
import model.product.Product;
import service.IGenericService;
import view.OrderView;
import view.ProductView;

import java.util.*;


public class ProductService implements IGenericService<Product,Integer> {

    List<Product> products = new config.Config<Product>().readFromFile(config.Config.PATH_PRODUCT);
    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void save(Product product) {
        if(findById(product.getId())== null){
            // add
            products.add(product);
        }else {
            // update
            products.set(products.indexOf(findById(product.getId())),product);
        }
        new Config<Product>().writeFromFile(Config.PATH_PRODUCT, products);
    }

    @Override
    public Product findById(Integer id) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                return products.get(i);
            }
        }
        return null;
    }

    public void deleteByStatus(){
        for (Product product:products) {
            if (product.isStatus()==false) {
                products.remove(product);
            }
        }
        System.out.println("Deleted auctioned products at the auction floor");
    }

    @Override
    public void deleteById(Integer id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.remove(products.get(i));
            }
        }
    }

    public int newId(){
        int max=0;
        for (Product product:products) {
            if(product.getId()>max){
                max = product.getId();
            }
        }
        return max+1;
    }

    public List<Product> searchByName(String name) {
        List<Product> listProductSearch = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(name.toLowerCase())) {
                listProductSearch.add(product);
            }

        }
        return listProductSearch;
    }

    public void editStatusProduct(int id){
        Product product = findById(id);
        boolean newStatus = false;
        product.setStatus(newStatus);
        product.setRemainingTime(0);

        String statusMessage = newStatus ? "Account unlocked" + product.getProductName() : "Account locked " + product.getProductName();
        System.out.println("User is now " + statusMessage);

        new Config<Product>().writeFromFile(Config.PATH_PRODUCT, products);
    }

    public void startCountdown(int seconds, Product product) {
        product.setRemainingTime(seconds * 1000); // Chuyển số giây thành mili giây

        if (product.getRemainingTime() > 0) {
            product.setCountdownTimer(new Timer());
            product.getCountdownTimer().schedule(new TimerTask() {
                @Override
                public void run() {
                    product.setRemainingTime(product.getRemainingTime() - 1000 ) ;
                    if (product.getRemainingTime() <= 0) {
                        product.getCountdownTimer().cancel();
                        product.setStatus(false);
                        save(product);
                        new OrderView().saveOrder(0);
                        save(product);
                    }
                }
            }, 0, 1000);
        }
    }

    public void changeStatus(Product product){
        product.setStatus(!product.isStatus());
        save(product);
    }

}
