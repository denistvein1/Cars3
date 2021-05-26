package Service;

import Domain.Car;
import Repository.Repository;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceCart {

    private final Repository repository;

    public ServiceCart(Repository repository) {
        this.repository = repository;
    }

    public void add(Car car) throws UnsupportedOperationException, SQLException {
        repository.add(car, repository.getTableShoppingCart());
        repository.delete(car, repository.getTableCars());
    }

    public ArrayList<Car> getAll() throws SQLException {
        return repository.getAll(repository.getTableShoppingCart());
    }

    public boolean areElements() throws SQLException {
        return getAll().size() != 0;
    }

    public void clear() throws SQLException {
        repository.deleteTable(repository.getTableShoppingCart());
    }

    public void open() throws SQLException, IOException {
        String fineName = "D:\\JavaProjects\\A6\\src\\shoppingCart.html";
        repository.saveToFile(repository.getTableShoppingCart(), fineName);
        File file = new File(fineName);
        Desktop.getDesktop().browse(file.toURI());
    }
}
