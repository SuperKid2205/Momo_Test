package org.momo.services;

import org.momo.models.Customer;
import org.momo.utilities.FileUtil;

public class CustomerService {

    Customer customer = null;

    public CustomerService() {
        this.customer = new Customer(FileUtil.loadBalanceFromFile());
        customer.setBills(FileUtil.loadBillsFromFile());
    }

    public void cashIn(double amount) {
        customer.addBalance(amount);
        FileUtil.saveBalanceToFile(customer.getAvailableBalance());
        System.out.println("Your available balance: " + customer.getAvailableBalance());
    }

    public Customer getCustomer() {
        return customer;
    }

    public void save() {
        FileUtil.saveBalanceToFile(customer.getAvailableBalance());
    }

}
