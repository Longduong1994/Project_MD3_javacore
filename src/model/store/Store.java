package model.store;

import java.io.Serializable;

public class Store implements Serializable {

    private double maintenanceFee;
    private double storeBalance;
    private double exchangeRate = 10.0;
    private double serviceFee;
    private double revenue;
    private double profit;

    public Store() {
    }

    public Store(double storeBalance, double exchangeRate, double serviceFee, double revenue) {
        this.storeBalance = storeBalance;
        this.exchangeRate = exchangeRate;
        this.serviceFee = serviceFee;
        this.revenue = revenue;
    }

    public Store(double storeBalance, double exchangeRate, double serviceFee, double revenue, double maintenanceFee, double profit) {
        this.storeBalance = storeBalance;
        this.exchangeRate = exchangeRate;
        this.serviceFee = serviceFee;
        this.revenue = revenue;
        this. maintenanceFee = maintenanceFee;
    }


    public double getStoreBalance() {
        return storeBalance;
    }

    public void setStoreBalance(double storeBalance) {
        this.storeBalance = storeBalance;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getMaintenanceFee() {
        return maintenanceFee;
    }

    public void setMaintenanceFee(double maintenanceFee) {
        this.maintenanceFee = maintenanceFee;
    }


    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "Store{" +
                "maintenanceFee=" + maintenanceFee +
                ", storeBalance=" + storeBalance +
                ", exchangeRate=" + exchangeRate +
                ", serviceFee=" + serviceFee +
                ", revenue=" + revenue +
                ", profit=" + profit +
                '}';
    }
}
