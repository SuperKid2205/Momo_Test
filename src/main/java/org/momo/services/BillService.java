package org.momo.services;

import org.momo.enums.BillState;
import org.momo.models.Bill;
import org.momo.models.Customer;
import org.momo.utilities.FileUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BillService {

    private List<Bill> bills = null;
    private Customer customer = null;

    public BillService(Customer customer) {
        this.customer = customer;
        this.bills = customer.getBills();
    }

    public void addBill(Bill bill) {
        bill.setId(getNextId());
        bills.add(bill);
        System.out.println("Bill Created, No: " + bill.getId());
    }

    public void updateBill(int id, String type, String ammout, String dueDate, String provider) throws ParseException {
        Bill bill = getBillById(id);
        if(bill == null) {
            System.out.println("Sorry! Not found a bill with such id.");
            return;
        }

        if(BillState.PROCESSED.equals(bill.getState())) {
            System.out.println("This bill has already been paid.");
            return;
        }

        // UPDATE
        if(type != null) {
            bill.setType(type);
        }
        if(ammout != null) {
            bill.setAmount(Double.parseDouble(ammout));
        }
        if(dueDate != null) {
            bill.setDueDate(new SimpleDateFormat("dd/MM/yyyy").parse(dueDate));
        }
        if(provider != null) {
            bill.setProvider(provider);
        }

        System.out.println("Bill Updated, No: " + bill.getId());

    }

    public void deleteBill(int id) {
        Bill bill = getBillById(id);
        if (bill == null) {
            System.out.println("Sorry! Not found a bill with such id.");
            return;
        }

        bills.remove(bill);
        System.out.println("Bill Deleted, No: " + bill.getId());
    }

    public void viewBill(int id) {
        Bill bill = getBillById(id);
        if (bill == null) {
            System.out.println("Sorry! Not found a bill with such id.");
            return;
        }
        System.out.println("Bill No. Type Amount Due Date State Provider");
        System.out.println(bill);
    }

    public int getNextId() {
        if(bills.size() < 1) return 1;
        return bills.getLast().getId() + 1;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public Bill getBillById(int id) {
        for (Bill bill : bills) {
            if (bill.getId() == id) {
                return bill;
            }
        }
        return null;
    }

    public void save() {
        FileUtil.saveBillsToFile(bills);
    }

    public void listBills() {
        showBills(getBills());
    }

    public List<Bill> getBillsByProvider(String provider) {
        return bills.stream()
                .filter(b -> b.getProvider().equals(provider)).toList();
    }

    public void showBillsByProvider(String provider) {
        showBills(getBillsByProvider(provider));
    }

    private void showBills(List<Bill> bills) {
        System.out.println("Bill No. Type Amount Due Date State Provider");
        for (Bill bill : bills) {
            System.out.println(bill);
        }
    }

    public void payListBills(Set ids) {
        List<Bill> payList = new ArrayList<>();
        double totalPay = 0.0;
        for (Bill bill : bills) {
            if(ids.contains(bill.getId())
                && !BillState.PROCESSED.equals(bill.getState())) {
                totalPay += bill.getAmount();
                payList.add(bill);
            }
        }
        if(payList.size() < 1) {
            System.out.println("No bills to pay.");
            return;
        }

        double accountBalance = customer.getAvailableBalance();
        if (accountBalance >= totalPay) {
            payList.sort(Comparator.comparing(Bill::getDueDate));

            for (Bill payBill : payList) {
                payBill(payBill.getId());
            }
        } else {
            System.out.println("Sorry! Not enough funds to proceed with payment.");
        }
    }

    public void payBill(int id) {

        Bill bill = getBillById(id);
        if(bill == null) {
            System.out.println("Sorry! Not found a bill with such id.");
            return;
        }

        if(BillState.PROCESSED.equals(bill.getState())) {
            System.out.println("This bill has already been paid.");
            return;
        }

        double accountBalance = customer.getAvailableBalance();

        if (accountBalance >= bill.getAmount()) {
            accountBalance -= bill.getAmount();
            bill.setState(BillState.PROCESSED);
            customer.setAvailableBalance(accountBalance);
            System.out.println("Payment has been completed for Bill with id " + id + ".");
            System.out.println("Your current balance is: " + accountBalance);
        } else {
            System.out.println("Sorry! Not enough funds to proceed with payment for Bill with id " + id + ".");
        }
    }

    public void payAllBills() {
        double totalPay = 0.0;
        double accountBalance = customer.getAvailableBalance();

        for (Bill bill : bills) {
            if(!BillState.PROCESSED.equals(bill.getState())) {
                totalPay += bill.getAmount();
                bill.setState(BillState.PENDING);
            }
        }

        if (accountBalance >= totalPay) {
            accountBalance -= totalPay;
            for (Bill bill : bills) {
                if(BillState.PENDING.equals(bill.getState())) {
                    bill.setState(BillState.PROCESSED);
                }
            }
            customer.setAvailableBalance(accountBalance);
            System.out.println("Payment has been completed for all bills.");
            System.out.println("Your current balance is: " + accountBalance);
        } else {
            System.out.println("Sorry! Not enough funds to proceed with payment.");
        }
    }

    public void showDueDateBills() {
        showBills(getDueDateBills());
    }

    public List<Bill> getDueDateBills() {
        bills.sort(Comparator.comparing(Bill::getDueDate));
        return bills.stream()
                .filter(b -> b.getState() == BillState.NOT_PAID).toList();
    }

    public void schedule(int id, String date) {
        Bill bill = getBillById(id);
        if(bill == null) {
            System.out.println("Sorry! Not found a bill with such id.");
            return;
        }

        if(BillState.PROCESSED.equals(bill.getState())) {
            System.out.println("This bill has already been paid.");
            return;
        }

        bill.setState(BillState.PENDING);
        System.out.println("Payment for bill id " + id + " is scheduled on " + date);
    }

    public void showListPayment() {
        showBills(getListPayment());
    }

    public List<Bill> getListPayment() {
        return bills.stream()
                .filter(b -> b.getState() == BillState.PROCESSED
                          || b.getState() == BillState.PENDING).toList();
    }
}
