package org.momo.models;

import org.momo.enums.BillState;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String type;
    private double amount;
    private Date dueDate;
    private BillState state;
    private String provider;

    public Bill(String type, double amount, String dueDate, String provider) throws ParseException {
        this.type = type;
        this.amount = amount;
        this.dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
        this.state = BillState.NOT_PAID;
        this.provider = provider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BillState getState() {
        return state;
    }

    public void setState(BillState state) {
        this.state = state;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString() {
        DecimalFormat format = new DecimalFormat("0.##");
        return id + ". " + type + " " + format.format(amount) + " " + new SimpleDateFormat("dd/MM/yyyy").format(dueDate) + " " + state + " " + provider;
    }
}