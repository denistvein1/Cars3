package UI;

import Domain.Car;
import Service.ServiceCars;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UIAdmin implements UI {

    private final ServiceCars service;
    private final Scanner scanner;

    public UIAdmin(ServiceCars service) {
        this.service = service;
        scanner = new Scanner(System.in);
    }

    private Car readElementNoLink() throws InputMismatchException {
        String company, model, sYear;
        int year;
        System.out.println("Enter company: ");
        company = scanner.nextLine();
        System.out.println("Enter model: ");
        model = scanner.nextLine();
        System.out.println("Enter year: ");
        sYear = scanner.nextLine();
        year = Integer.parseInt(sYear);
        return new Car(company.toLowerCase(), model.toLowerCase(), year);
    }

    private Car readEntity(boolean newLink) throws InputMismatchException {
        Car entity = readElementNoLink();
        String link;
        if (newLink) {
            System.out.println("Enter new link: ");
        } else {
            System.out.println("Enter link: ");
        }
        link = scanner.nextLine();
        entity.setLink(link.toLowerCase());
        return entity;
    }

    private void add() {
        Car entity;
        try {
            entity = readEntity(false);
        } catch (InputMismatchException e) {
            System.out.println("year must be a number!");
            return;
        }
        try {
            service.add(entity);
            System.out.println(entity.toStringNoLink() + " added");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input!");
        } catch (UnsupportedOperationException e) {
            System.out.println(entity.toStringNoLink() + " already exists");
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database");
            e.printStackTrace();
        }
    }

    private void delete() {
        Car entity;
        try {
            entity = readElementNoLink();
        } catch (InputMismatchException e) {
            System.out.println("year must be a number!");
            return;
        }
        try {
            service.delete(entity);
            System.out.println(entity.toStringNoLink() + " deleted");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input!");
        } catch (UnsupportedOperationException e) {
            System.out.println(entity.toStringNoLink() + " doesn't exist");
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database");
            e.printStackTrace();
        }
    }

    private void update() {
        Car entity;
        try {
            entity = readEntity(true);
        } catch (InputMismatchException e) {
            System.out.println("year must be a number!");
            return;
        }
        try {
            service.update(entity);
            System.out.println(entity.toStringNoLink() + " updated");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input!");
        } catch (UnsupportedOperationException e) {
            System.out.println(entity.toStringNoLink() + " doesn't exist");
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database");
            e.printStackTrace();
        }
    }

    private void printAll() {
        try {
            if (!service.areElements()) {
                System.out.println("Empty list");
            }
            for (Car entity : service.getAll()) {
                System.out.print(entity);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database");
            e.printStackTrace();
        }
    }

    @Override
    public void introduction() {
        System.out.println("Here are your options for the administrator mode: ");
    }

    @Override
    public void options() {
        System.out.println("0. Back");
        System.out.println("1. Add a car");
        System.out.println("2. Delete a car");
        System.out.println("3. Update a car");
        System.out.println("4. Print all the cars");
        System.out.println("5. Print options again");
    }

    @Override
    public void menu() {
        introduction();
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
                add();
            } else if (choice == 2) {
                delete();
            } else if (choice == 3) {
                update();
            } else if (choice == 4) {
                printAll();
            } else if (choice == 5) {
                options();
            } else {
                System.out.println("Invalid input!");
            }
        }
    }
}
