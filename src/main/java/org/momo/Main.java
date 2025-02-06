package org.momo;

import org.momo.models.Bill;
import org.momo.services.BillService;
import org.momo.services.CustomerService;
import org.momo.utilities.FileUtil;
import org.momo.validator.ArgumentChecker;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws ParseException {
        CustomerService customerService = new CustomerService();
        BillService billService = new BillService(customerService.getCustomer());

        if (args.length < 1) {
            System.out.println("Please provide a command.");
            return;
        }

        // Check Arguments
        if(!ArgumentChecker.check(args)) {
            return;
        }

        // Create default bill to test
        if(billService.getBills().size() < 1 ) {
            createDefaultBills(billService);
        }

        String id;
        String type;
        double amount;
        String amountStr;
        String dueDate;
        String provider;

        // Process
        switch (args[0]) {
            case "CASH_IN":
                amount = Double.parseDouble(args[1]);
                customerService.cashIn(amount);
                break;

            case "CREATE_BILL":
                type = args[1];
                amount = Double.parseDouble(args[2]);
                dueDate = args[3];
                provider = args[4];
                billService.addBill(new Bill(type, amount, dueDate, provider));
                billService.save();
                break;

            case "UPDATE_BILL":
                id = args[1];
                type = args[2];
                amountStr = args[3];
                dueDate = args[4];
                provider = args[5];
                billService.updateBill(Integer.parseInt(id), type, amountStr, dueDate, provider);
                billService.save();
                break;

            case "DELETE_BILL":
                billService.deleteBill(Integer.parseInt(args[1]));
                billService.save();
                break;

            case "VIEW_BILL":
                billService.viewBill(Integer.parseInt(args[1]));
                break;

            case "LIST_BILL":
                billService.listBills();
                break;

            case "SEARCH_BILL_BY_PROVIDER":
                billService.showBillsByProvider(args[1]);
                break;

            case "PAY":
                Set<Integer> ids = new HashSet<>();
                for(int i=1;i<args.length;i++) {
                    ids.add(Integer.valueOf(args[i]));
                }
                billService.payListBills(ids);
                billService.save();
                customerService.save();
                break;

            case "PAY_ALL":
                billService.payAllBills();
                billService.save();
                customerService.save();
                break;

            case "DUE_DATE":
                billService.showDueDateBills();
                break;

            case "SCHEDULE":
                billService.schedule(Integer.valueOf(args[1]), args[2]);
                billService.save();
                customerService.save();
                break;

            case "LIST_PAYMENT":
                billService.showListPayment();
                break;

            case "EXIT":
                FileUtil.deleteTempFiles();
                System.out.println("Good bye.");
                break;

            default:
                System.out.println("Unknown command.");
                break;
        }
    }

    private static void createDefaultBills(BillService billService) throws ParseException {

        System.out.println("Don't have any bill, create default bills.");
        billService.addBill(new Bill("ELECTRIC", 100, "25/10/2020", "EVN HCMC"));
        billService.addBill(new Bill("WATER", 200, "30/10/2020", "SAVACO HCMC"));
        billService.addBill(new Bill("INTERNET", 300, "30/11/2020", "VNPT"));
        billService.addBill(new Bill("INTERNET", 400, "30/11/2020", "VNPT"));
        billService.addBill(new Bill("INTERNET", 500, "10/01/2020", "VNPT"));
        billService.addBill(new Bill("INTERNET", 600, "20/01/2020", "VNPT"));
        billService.save();

    }
}
