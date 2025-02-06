package org.momo.validator;

public class ArgumentChecker {
    public static boolean check(String[] args) {
        switch (args[0]) {
            case "CASH_IN":
                if (!InputValidator.isValidCommand(args, 2)) {
                        System.out.println("Usage: CASH_IN <amount>");
                        return false;
                    } else if (!InputValidator.isValidAmount(args[1])) {
                        System.out.println("Invalid amount. Please provide a positive number.");
                        return false;
                }
                break;

            case "CREATE_BILL":
                if (!InputValidator.isValidCommand(args, 5)) {
                    System.out.println("Usage: CASH_IN <amount>");
                    return false;
                } else if (!InputValidator.isValidAmount(args[2])) {
                    System.out.println("Invalid amount. Please provide a positive number.");
                    return false;
                } else if (!InputValidator.isValidDate(args[3])) {
                    System.out.println("Invalid date.");
                    return false;
                }
                break;

            case "UPDATE_BILL":
                break;

            case "DELETE_BILL", "VIEW_BILL":
                if (!InputValidator.isValidCommand(args, 2)) {
                    System.out.println("Usage: DELETE_BILL <id>");
                    return false;
                } else if (!InputValidator.isValidAmount(args[1])) {
                    System.out.println("Invalid number.");
                    return false;
                }
                break;

            case "LIST_BILL":
                break;

            case "SEARCH_BILL_BY_PROVIDER":
                break;

            case "PAY":
                for(int i=1;i<args.length;i++) {
                    if (!InputValidator.isValidNumber(args[i])) {
                        System.out.println("Invalid number.");
                        return false;
                    }
                }
                break;

            case "PAY_ALL":
                break;

            case "DUE_DATE":
                break;

            case "SCHEDULE":
                if (!InputValidator.isValidCommand(args, 3)) {
                    System.out.println("Usage: SCHEDULE <id> <date>");
                    return false;
                } else if (!InputValidator.isValidAmount(args[1])) {
                    System.out.println("Invalid number.");
                    return false;
                } else if (!InputValidator.isValidDate(args[2])) {
                    System.out.println("Invalid date.");
                    return false;
                }
                break;

            case "LIST_PAYMENT":
                break;

            case "EXIT":
                break;

            default:
                break;
        }
        return true;
    }
}
