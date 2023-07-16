package model.cart;

import model.product.Product;
import model.user.Auction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


public class CartItem implements Serializable {
    private int id;
    private Product product;
    private double auctionPrice;
    private volatile boolean status;
    private List<Auction> auctions;

    private LocalDateTime date;

    public CartItem() {
    }

    public CartItem(int id, Product product, double auctionPrice, boolean status, List<Auction> auctions, LocalDateTime date) {
        this.id = id;
        this.product = product;
        this.auctionPrice = auctionPrice;
        this.status = status;
        this.auctions = auctions;
        this.date = date;
    }

    public CartItem(int id, Product product,  double auctionPrice, boolean status, List<Auction> auctions) {
        this.id = id;
        this.product = product;
        this.auctionPrice= auctionPrice;
        this.status = status;
        this.auctions = auctions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(double auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", status=" + status +
                ", product=" + product +
                ", auctionPrice=" + auctionPrice +
                ", auctions=" + auctions +
                ", date=" + date +
                '}';
    }
}
