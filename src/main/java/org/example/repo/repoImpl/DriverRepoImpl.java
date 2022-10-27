package org.example.repo.repoImpl;

import org.example.models.drivers.Driver;
import org.example.models.drivers.DriverQualificationEnum;
import org.example.repo.repoInterfaces.DriversRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverRepoImpl  implements DriversRepo {

    public DriverRepoImpl() {
    }

    @Override
    public boolean add(Driver driver, Connection connection) {
        String sql = "INSERT INTO transport_service.drivers_repo " +
                "(name, surname, phone_number, qualification) " +
                "VALUES (?, ?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, driver.getNameOfDriver());
            preparedStatement.setString(2, driver.getSurnameOfDriver());
            preparedStatement.setString(3, driver.getNumberOfPhone());
            preparedStatement.setString(4, String.valueOf(driver.getQualificationEnum()));

            int result = preparedStatement.executeUpdate();
            return result==1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id, Connection connection) {
        String sql = "DELETE from transport_service.drivers_repo where id = (?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,id);

            int result = preparedStatement.executeUpdate();
            return result==1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Driver> getById(Integer id, Connection connection) {
        String sql = "SELECT * FROM transport_service.drivers_repo WHERE id = (?)";
        Driver driver = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String phonenumber = resultSet.getString(4);
                String qualification = resultSet.getString(5);

                DriverQualificationEnum driverQualification = qualification.equals("BUS_DRIVER") ? DriverQualificationEnum.BUS_DRIVER : DriverQualificationEnum.TRAM_DRIVER;

                driver = new Driver(id, name, surname, phonenumber, driverQualification);
                return Optional.of(driver);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean updateById(Integer id, Connection connection) {
        String sql = "UPDATE transport_service.drivers_repo " +
                        "SET name = (?), surname = (?), phone_number = (?), qualification = (?) " +
                         "WHERE id = (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, inputFromConsole());
            preparedStatement.setString(2, inputFromConsole());
            preparedStatement.setString(3, inputFromConsole());

            String qualification = inputFromConsole();
            if (qualification.trim().equals("BUS_DRIVER") || qualification.trim().equals("TRAM_DRIVER")) {
                preparedStatement.setString(4, qualification);
            } else {
                updateById(id,connection);
            }

            preparedStatement.setInt(5, id);

            int result = preparedStatement.executeUpdate();
            return result==1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String inputFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Driver> getAll(Connection connection) {
        String sql = "SELECT * FROM transport_service.drivers_repo";
        Driver driver = null;
        List <Driver> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String surname = resultSet.getString(3);
                String phonenumber = resultSet.getString(4);
                String qualification = resultSet.getString(5);

                DriverQualificationEnum driverQualification = qualification.equals("BUS_DRIVER") ? DriverQualificationEnum.BUS_DRIVER : DriverQualificationEnum.TRAM_DRIVER;

                driver = new Driver(id, name, surname, phonenumber, driverQualification);
                list.add(driver);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
