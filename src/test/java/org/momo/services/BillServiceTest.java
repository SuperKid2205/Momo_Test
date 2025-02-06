package org.momo.services;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.momo.enums.BillState;
import org.momo.models.Bill;
import org.momo.models.Customer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BillServiceTest {

    private Customer customer;
    private BillService billService;
    private List<Bill> bills;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Before
    public void setUp() throws ParseException {

        customer = new Customer(1000);
        bills = new ArrayList<>();

        Bill bill1 = new Bill("ELECTRIC", 200, "03/01/2021", "EVN");
        bill1.setId(1);
        Bill bill2 = new Bill("WATER", 456.5, "01/01/2021", "AQUA");
        bill2.setId(2);
        Bill bill3 = new Bill("INTERNET", 789.12, "04/01/2021", "VNPT");
        bill3.setId(3);
        Bill bill4 = new Bill("HOUSE", 1001, "02/01/2021", "TECHCOMBANK");
        bill4.setId(4);
        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);
        bills.add(bill4);
        customer.setBills(bills);

        billService = new BillService(customer);
    }

    @Test
    public void testListBills_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            billService.listBills();
            String expectedOutput = "Bill No. Type Amount Due Date State Provider\n" +
                    "1. ELECTRIC 200 03/01/2021 NOT_PAID EVN\n" +
                    "2. WATER 456,5 01/01/2021 NOT_PAID AQUA\n" +
                    "3. INTERNET 789,12 04/01/2021 NOT_PAID VNPT\n" +
                    "4. HOUSE 1001 02/01/2021 NOT_PAID TECHCOMBANK\n"
                    ;
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testAddBill_success() throws ParseException {
        Bill newBill = new Bill("FOOD", 300.5, "20/10/2020", "VIFON");
        billService.addBill(newBill);

        Bill addedBill = bills.getLast();
        assertEquals(5, addedBill.getId());
        assertEquals("FOOD", addedBill.getType());
        assertEquals(300.5, addedBill.getAmount(), 0.0);
        Date expectedDueDate = sdf.parse("20/10/2020");
        assertEquals(expectedDueDate, addedBill.getDueDate());
        assertEquals(BillState.NOT_PAID, addedBill.getState());
        assertEquals("VIFON", addedBill.getProvider());
    }

    @Test
    public void testDeleteBill_success() throws ParseException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            billService.deleteBill(3);
            billService.listBills();
            String expectedOutput = "Bill Deleted, No: 3\n" +
                    "Bill No. Type Amount Due Date State Provider\n" +
                    "1. ELECTRIC 200 03/01/2021 NOT_PAID EVN\n" +
                    "2. WATER 456,5 01/01/2021 NOT_PAID AQUA\n" +
                    "4. HOUSE 1001 02/01/2021 NOT_PAID TECHCOMBANK\n"
                    ;
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testViewBill_success() throws ParseException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            billService.viewBill(3);
            String expectedOutput = "Bill No. Type Amount Due Date State Provider\n" +
                    "3. INTERNET 789,12 04/01/2021 NOT_PAID VNPT\n"
                    ;
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testPayBill_pay1bill_success() {
        billService.payBill(1);
        assertEquals(BillState.PROCESSED, bills.get(0).getState());
        assertEquals(800, customer.getAvailableBalance(), 0.0);
    }

    @Test
    public void testPayBill_notenoughbalance_fail() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            billService.payBill(4);
            String expectedOutput =
                    "Sorry! Not enough funds to proceed with payment for Bill with id 4.\n";
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testPayBill_multibills_success() {
        Set<Integer> billSet = new HashSet<>();
        billSet.add(1);
        billSet.add(2);

        billService.payListBills(billSet);
        assertEquals(BillState.PROCESSED, bills.get(0).getState());
        assertEquals(BillState.PROCESSED, bills.get(1).getState());
        assertEquals(BillState.NOT_PAID, bills.get(2).getState());
        assertEquals(343.5, customer.getAvailableBalance(), 0.0);
    }

    @Test
    public void testPayBill_multibills_notenoughbalance_fail() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {

            Set<Integer> billSet = new HashSet<>();
            billSet.add(2);
            billSet.add(3);
            billService.payListBills(billSet);

            String expectedOutput =
                    "Sorry! Not enough funds to proceed with payment.\n";
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testSortBillsByDueDate_success() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            billService.showDueDateBills();
            String expectedOutput = "Bill No. Type Amount Due Date State Provider\n" +
                    "2. WATER 456,5 01/01/2021 NOT_PAID AQUA\n" +
                    "4. HOUSE 1001 02/01/2021 NOT_PAID TECHCOMBANK\n" +
                    "1. ELECTRIC 200 03/01/2021 NOT_PAID EVN\n" +
                    "3. INTERNET 789,12 04/01/2021 NOT_PAID VNPT\n"
                    ;
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testSchedule_success() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            billService.schedule(3, "02/01/2021");
            String expectedOutput = "Payment for bill id 3 is scheduled on 02/01/2021\n";
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testListPayment_success() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        bills.get(0).setState(BillState.PROCESSED);
        bills.get(2).setState(BillState.PENDING);
        try {
            billService.showListPayment();

            String expectedOutput = "Bill No. Type Amount Due Date State Provider\n" +
                    "1. ELECTRIC 200 03/01/2021 PROCESSED EVN\n" +
                    "3. INTERNET 789,12 04/01/2021 PENDING VNPT\n"
                    ;
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }


    @Test
    public void testSearchBillsByProvider_success() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            billService.showBillsByProvider("TECHCOMBANK");

            String expectedOutput = "Bill No. Type Amount Due Date State Provider\n" +
                    "4. HOUSE 1001 02/01/2021 NOT_PAID TECHCOMBANK\n"
                    ;
            assertEquals(expectedOutput, outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }

}
