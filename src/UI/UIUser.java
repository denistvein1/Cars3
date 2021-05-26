package UI;

import Domain.Car;
import Service.ServiceCars;
import Service.ServiceCart;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UIUser implements UI {

    private final ServiceCars service;
    private final ServiceCart serviceCart;
    private Iterator<Car> iterator = null;
    private final Scanner scanner;
    private Car currentEntity;

    public UIUser(ServiceCars service, ServiceCart serviceCart) {
        this.service = service;
        this.serviceCart = serviceCart;
        scanner = new Scanner(System.in);
        currentEntity = null;
    }

    private void printEntity() {
        System.out.println(currentEntity);
    }

    private void printNext() throws UnsupportedOperationException, SQLException {
        if (!service.areElements()) {
            throw new UnsupportedOperationException();
        }
        try {
            currentEntity = iterator.next();
        } catch (NoSuchElementException | ConcurrentModificationException e) {
            iterator = service.getAll().iterator();
            currentEntity = iterator.next();
        }
        printEntity();
    }

    private void buyEntity() throws SQLException {
        serviceCart.add(currentEntity);
        System.out.println(currentEntity.toStringNoLink() + " bought\n");
    }

    private void iterateEntities() {
        try {
            printNext();
        } catch (UnsupportedOperationException e) {
            System.out.println("No cars available!");
            return;
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database");
            e.printStackTrace();
            return;
        }
        iterateEntitiesOptions();
        while (true) {
            try {
                if (!service.areElements()) {
                    System.out.println("No cars available!");
                    return;
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong with the database");
                e.printStackTrace();
            }
            int choice;
            String sChoice;
            System.out.println("Enter your choice: ");
            sChoice = scanner.nextLine();
            try {
                choice = Integer.parseInt(sChoice);
            } catch (NumberFormatException e) {
                System.out.println(sChoice + " is not a number!");
                continue;
            }
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                try {
                    printNext();
                } catch (SQLException e) {
                    System.out.println("Something went wrong with the database");
                    e.printStackTrace();
                }
                iterateEntitiesOptions();
            } else if (choice == 2) {
                try {
                    buyEntity();
                } catch (UnsupportedOperationException e) {
                    System.out.println(currentEntity.toStringNoLink() + " already bought\n");
                } catch (SQLException e) {
                    System.out.println("Something went wrong with the database");
                    e.printStackTrace();
                }
                try {
                    printNext();
                } catch (UnsupportedOperationException e) {
                    System.out.println("No cars available!");
                    return;
                } catch (SQLException e) {
                    System.out.println("Something went wrong with the database");
                    e.printStackTrace();
                }
                iterateEntitiesOptions();
            } else {
                System.out.println("Invalid input!");
            }
        }
    }

    private void printShoppingCart() {
        try {
            serviceCart.open();
        } catch (IOException e) {
            System.out.println("Something went wrong with the file");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database");
            e.printStackTrace();
        }
    }

    private void clearShoppingCart() throws SQLException {
        if (!serviceCart.areElements()) {
            System.out.println("Shopping cart is already empty");
            return;
        }
        serviceCart.clear();
        System.out.println("Shopping cart cleared");
    }

    @Override
    public void introduction() {
        System.out.println("Here are your options for the user mode: ");
    }

    private void iterateEntitiesOptions() {
        System.out.println("0. Back");
        System.out.println("1. Next");
        System.out.println("2. Buy");
    }

    @Override
    public void options() {
        System.out.println("0. Back");
        System.out.println("1. Iterate through all the cars");
        System.out.println("2. Display shopping cart");
        System.out.println("3. Clear shopping cart");
        System.out.println("4. Print options again");
    }

    @Override
    public void menu() {
        options();
        while (true) {
            int choice;
            String sChoice;
            System.out.println("Enter your choice: ");
            sChoice = scanner.nextLine();
            try {
                choice = Integer.parseInt(sChoice);
            } catch (NumberFormatException e) {
                System.out.println(sChoice + " is not a number!");
                continue;
            }
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                try {
                    iterator = service.getAll().iterator();
                } catch (SQLException e) {
                    System.out.println("Something went wrong with the database");
                    e.printStackTrace();
                    continue;
                }
                iterateEntities();
                options();
            } else if (choice == 2) {
                printShoppingCart();
            } else if (choice == 3) {
                try {
                    clearShoppingCart();
                } catch (SQLException e) {
                    System.out.println("Something went wrong with the database");
                    e.printStackTrace();
                }
            } else if (choice == 4) {
                options();
            } else {
                System.out.println("Invalid input!");
            }

        }
    }
}
