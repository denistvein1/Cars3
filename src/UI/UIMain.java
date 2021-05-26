package UI;

import java.util.Scanner;

public class UIMain implements UI {

    private final UIAdmin uiAdmin;
    private final UIUser uiUser;

    public UIMain(UIAdmin uiAdmin, UIUser uiUser) {
        this.uiAdmin = uiAdmin;
        this.uiUser = uiUser;
    }

    @Override
    public void introduction() {
        System.out.println("""
                Welcome to Denis' car dealer!
                Here you can manage all the cars in the dealership
                Choose from the options below:\s""");
    }

    @Override
    public void options() {
        System.out.println("0. Exit");
        System.out.println("1. Enter in administrator mode");
        System.out.println("2. Enter in user mode");
        System.out.println("3. Print options again");
    }

    @Override
    public void menu() {
        introduction();
        options();
        while (true) {
            int choice;
            String sChoice;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your choice: ");
            sChoice = scanner.nextLine();
            try {
                choice = Integer.parseInt(sChoice);
            } catch (NumberFormatException e) {
                System.out.println(sChoice + " is not a number!");
                continue;
            }
            if (choice == 0) {
                System.out.println("Bye!");
                break;
            } else if (choice == 1) {
                uiAdmin.menu();
                options();
            } else if (choice == 2) {
                uiUser.menu();
                options();
            } else if (choice == 3) {
                options();
            } else {
                System.out.println("Invalid input!");
            }
        }
    }
}
