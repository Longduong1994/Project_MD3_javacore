package model.cart;

import model.product.Product;
import model.user.User;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private CartItem cartItem;
    private User buyer;
    private double total;
    private String receiver;
    private Date date;
    private String address;
    private String phoneNumber;
    private boolean status;

    public Order() {
    }



    public Order(int id, User buyer, CartItem cartItem, double total, Date date, boolean status) {
        this.id = id;
        this.cartItem = cartItem;
        this.buyer = buyer;
        this.total = total;
        this.date = date;
        this.status = status;
    }

    public void date(){
        new Date();
    }

    public Order(int id, CartItem cartItem, User buyer, double total, String receiver, Date date, String address, String phoneNumber, boolean status) {
        this.id = id;
        this.cartItem = cartItem;
        this.buyer = buyer;
        this.total = total;
        this.receiver = receiver;
        this.date = date;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        buyer = buyer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }


    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", cartItem=" + cartItem +
                ", buyer=" + buyer +
                ", total=" + total +
                ", receiver='" + receiver + '\'' +
                ", date=" + date +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
