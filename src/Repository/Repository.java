package Repository;

import Domain.Car;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Repository {

    private final String TABLE_CARS = "Cars";
    private final String TABLE_SHOPPING_CART = "ShoppingCart";
    private final String COLUMN_COMPANY = "company";
    private final String COLUMN_MODEL = "model";
    private final String COLUMN_YEAR = "year";
    private final String COLUMN_LINK = "link";
    private static Statement statement;
    private static Connection connection;

    public Repository() throws SQLException {
        createDatabase();
    }

    private void createDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:D:\\JavaProjects\\A6\\cars.db");
        statement = connection.createStatement();
        createTableCars(TABLE_CARS);
        createTableCars(TABLE_SHOPPING_CART);
    }

    private void createTableCars(String tableName) throws SQLException {
        statement.execute("CREATE TABLE IF NOT EXISTS " + tableName +
                " (" + COLUMN_COMPANY + " text, " +
                COLUMN_MODEL + " text, " +
                COLUMN_YEAR + " integer, " +
                COLUMN_LINK + " text" +
                ")");
    }

    public boolean contains(Car car, String tableName) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName +
                " WHERE " + COLUMN_COMPANY + "='" + car.getCompany() + "' AND " +
                COLUMN_MODEL + "='" + car.getModel() + "' AND " +
                COLUMN_YEAR + "=" + car.getYear());
        return resultSet.next();
    }

    public void add(Car car, String tableName) throws SQLException, UnsupportedOperationException {
        if (contains(car, tableName)) {
            throw new UnsupportedOperationException();
        }
        statement.execute("INSERT INTO " + tableName +
                " (" + COLUMN_COMPANY + ", " +
                COLUMN_MODEL + ", " +
                COLUMN_YEAR + ", " +
                COLUMN_LINK +
                ") " +
                "VALUES('" + car.getCompany() + "', '" + car.getModel() + "', " + car.getYear() + ", '" + car.getLink() + "')");
    }

    public void delete(Car car, String tableName) throws SQLException, UnsupportedOperationException {
        if (!contains(car, tableName)) {
            throw new UnsupportedOperationException();
        }
        statement.execute("DELETE FROM " + tableName +
                " WHERE " + COLUMN_COMPANY + "='" + car.getCompany() + "' AND " +
                COLUMN_MODEL + "='" + car.getModel() + "' AND " +
                COLUMN_YEAR + "=" + car.getYear());
    }

    public void update(Car car, String tableName) throws SQLException, UnsupportedOperationException {
        if (!contains(car, tableName)) {
            throw new UnsupportedOperationException();
        }
        statement.execute("UPDATE " + tableName +
                " SET " + COLUMN_LINK + "='" + car.getLink() +
                "' WHERE " + COLUMN_COMPANY + "='" + car.getCompany() +
                "' AND " + COLUMN_MODEL + "='" + car.getModel() +
                "' AND " + COLUMN_YEAR + "=" + car.getYear());
    }

    public ArrayList<Car> getAll(String tableName) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
        ArrayList<Car> allCars = new ArrayList<>();
        while (resultSet.next()) {
            Car car = new Car(resultSet.getString(COLUMN_COMPANY), resultSet.getString(COLUMN_MODEL), resultSet.getInt(COLUMN_YEAR), resultSet.getString(COLUMN_LINK));
            allCars.add(car);
        }
        return allCars;
    }

    public void deleteTable(String tableName) throws SQLException {
        statement.execute("DELETE FROM " + tableName);
    }

    public String getTableCars() {
        return TABLE_CARS;
    }

    public String getTableShoppingCart() {
        return TABLE_SHOPPING_CART;
    }

    public void saveToFile(String tableName, String fileName) throws IOException, SQLException {
        FileWriter fileWriter = new FileWriter(fileName);
        for (Car car : getAll(tableName)) {
            Car.writeToHtmlFile(fileWriter, car);
        }
        fileWriter.close();
    }

    public void endConnection() throws SQLException {
        statement.close();
        connection.close();
    }
}
