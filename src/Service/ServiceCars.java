package Service;

import Domain.Car;
import Domain.Validator;
import Repository.Repository;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceCars {

    protected Repository repository;

    public ServiceCars(Repository repository) {
        this.repository = repository;
    }

    public void add(Car car) throws SQLException, IllegalArgumentException, UnsupportedOperationException {
        Validator.validate(car);
        repository.add(car, repository.getTableCars());
    }

    public void delete(Car car) throws SQLException, IllegalArgumentException, UnsupportedOperationException {
        Validator.validate(car);
        repository.delete(car, repository.getTableCars());
    }

    public void update(Car car) throws SQLException, IllegalArgumentException, UnsupportedOperationException {
        Validator.validate(car);
        repository.update(car, repository.getTableCars());
    }

    public ArrayList<Car> getAll() throws SQLException {
        return repository.getAll(repository.getTableCars());
    }

    public boolean areElements() throws SQLException {
        return getAll().size() != 0;
    }
}
