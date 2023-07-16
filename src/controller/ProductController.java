package controller;

import model.product.Product;
import service.IGenericService;
import service.product.ProductService;

import java.util.List;

public class ProductController implements IGenericService<Product,Integer> {

    ProductService productService = new ProductService();
    @Override
    public List<Product> findAll() {
        return productService.findAll();
    }

    @Override
    public void save(Product product) {
        productService.save(product);
    }

    @Override
    public Product findById(Integer id) {
        return productService.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        productService.deleteById(id);
    }
    public int newId() {
        return productService.newId();
    }
    public List<Product> searchByName(String name){
        return productService.searchByName(name);
    }

    public void editProductStatus(Integer id){
        productService.editStatusProduct(id);
    }

    public void deleteByStatus(){
       productService.deleteByStatus();
    }

    public void startCountdown(int seconds, Product product){
        new ProductService().startCountdown(seconds, product);
    }

    public void changeStatus(Product product){
       productService.changeStatus(product);
    }
}
