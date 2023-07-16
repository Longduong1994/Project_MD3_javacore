package model.product;

import model.user.User;
import view.OrderView;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Product implements Serializable {
    private int id;
    private String productName;
    private String description;
    private double price;
    private double priceUpdate;
    private User user;
    private Date endTime;
    private long remainingTime;
    private boolean status;
    private transient Timer countdownTimer;

    public Product() {
    }

    public Product(int id, String productName, String description, double price,double priceUpdate, User user, Date endTime,boolean status) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.priceUpdate = priceUpdate;
        this.user = user;
        this.endTime = endTime;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getPriceUpdate() {
        return priceUpdate;
    }

    public void setPriceUpdate(double priceUpdate) {
        this.priceUpdate = priceUpdate;
    }

    public Timer getCountdownTimer() {
        return countdownTimer;
    }

    public void setCountdownTimer(Timer countdownTimer) {
        this.countdownTimer = countdownTimer;
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }

//    public void startCountdown(int seconds) {
//        remainingTime = seconds * 1000; // Chuyển số giây thành mili giây
//
//        if (remainingTime > 0) {
//            countdownTimer = new Timer();
//            countdownTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    remainingTime -= 1000;
//                    if (remainingTime <= 0) {
//                        countdownTimer.cancel();
//                        setStatus(false);
//                        new OrderView().saveOrder(0);
//                    }
//                }
//            }, 0, 1000);
//        }
//    }

//    private void updateProductStatus(boolean status) {
//        this.status = status;
//    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", priceUpdate=" + priceUpdate +
                ", user=" + user +
                ", endTime=" + endTime +
                ", remainingTime=" + remainingTime +
                ", status=" + status +
                ", countdownTimer=" + countdownTimer +
                '}';
    }
}
