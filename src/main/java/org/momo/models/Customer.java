package org.momo.models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private double availableBalance;
    private List<Bill> bills = new ArrayList<Bill>();

    public Customer(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public void addBalance(double amount) {
        this.availableBalance += amount;
    }
}