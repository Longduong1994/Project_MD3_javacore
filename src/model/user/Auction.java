package model.user;

import java.io.Serializable;

public class Auction implements Serializable {
    private int id;
    private User user;
    
    private double price;

    public Auction(int id, User user, double price) {
        this.id = id;
        this.user = user;
        this.price = price;
    }

    public Auction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", user=" + user +
                ", price=" + price +
                '}';
    }
}
