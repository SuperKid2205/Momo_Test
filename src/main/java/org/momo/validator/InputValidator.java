package org.momo.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InputValidator {

    public static boolean isValidCommand(String[] args, int expectedLength) {
        return args.length == expectedLength;
    }

    public static boolean isValidAmount(String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidNumber(String number) {
        try {
            int result = Integer.parseInt(number);
            return result > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
