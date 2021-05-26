import Repository.Repository;
import Service.ServiceCars;
import Service.ServiceCart;
import UI.UIAdmin;
import UI.UIMain;
import UI.UIUser;

import java.sql.SQLException;

public class start {
    public static void main(String[] args) {
        create();
    }

    public static void create() {
        Repository repository;
        try {
            repository = new Repository();
        } catch (SQLException e) {
            System.out.println("Cannot create database");
            e.printStackTrace();
            return;
        }
        ServiceCars service = new ServiceCars(repository);
        ServiceCart serviceCart = new ServiceCart(repository);
        UIAdmin uiAdmin = new UIAdmin(service);
        UIUser uiUser = new UIUser(service, serviceCart);
        UIMain uiMain = new UIMain(uiAdmin, uiUser);
        uiMain.menu();
        try {
            repository.endConnection();
        } catch (SQLException e) {
            System.out.println("Something went wrong with the database");
            e.printStackTrace();
            //return;
        }
    }
}
