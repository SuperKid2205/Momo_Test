package org.momo.services;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.momo.models.Customer;
import org.momo.utilities.FileUtil;

public class CustomerServiceTest {

    private CustomerService customerService;
    private Customer customer;

    @Before
    public void setUp() {
        FileUtil.deleteTempFiles();
        customerService = new CustomerService();
        customer = new Customer(111);
    }

    @Test
    public void testAddFunds() {
        double initialBalance = customer.getAvailableBalance();
        double amountToAdd = 222;
        
        customerService.cashIn(amountToAdd);
        
        assertEquals(initialBalance + amountToAdd, customer.getAvailableBalance(),333);
    }

    @Test
    public void testSave() {
        customer.setAvailableBalance(444);
        customerService.save();
        assertEquals(customer.getAvailableBalance(), FileUtil.loadBalanceFromFile(), 444);
    }

}
