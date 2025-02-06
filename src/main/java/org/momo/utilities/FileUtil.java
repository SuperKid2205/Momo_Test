package org.momo.utilities;

import org.momo.models.Bill;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {

    public final static String BALANCE_FILE = "balance.temp";
    public final static String BILL_TEMP_FILE = "bills.temp";

    public static void saveBalanceToFile(double balance) {
        try (PrintWriter out = new PrintWriter(new FileWriter(BALANCE_FILE))) {
            out.println(balance);
        } catch (IOException e) {
            System.err.println("Save file error.");
        }
    }

    public static double loadBalanceFromFile() {
        try (Scanner scanner = new Scanner(new File(BALANCE_FILE))) {
            if (scanner.hasNext()) {
                return Double.valueOf(scanner.next());
            }
        } catch (FileNotFoundException e) {
        }
        // if doesn't have temp file, return balance = 0
        return 0;
    }

    public static void saveBillsToFile(List<Bill> bills) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BILL_TEMP_FILE))) {
            out.writeObject(bills);
        } catch (IOException e) {
            System.err.println("Error saving bills to file.");
        }
    }

    public static List<Bill> loadBillsFromFile() {
        List<Bill> bills = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BILL_TEMP_FILE))) {
            bills = (List<Bill>) in.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading bills from file.");
        }
        return bills;
    }

    public static void deleteTempFiles() {
        File dir = new File(".");
        File[] tempFiles = dir.listFiles((d, name) -> name.endsWith(".temp"));

        if (tempFiles != null) {
            for (File file : tempFiles) {
                if (file.delete()) {
                    System.out.println("Deleted file: " + file.getName());
                } else {
                    System.err.println("Failed to delete file: " + file.getName());
                }
            }
        }
    }
}