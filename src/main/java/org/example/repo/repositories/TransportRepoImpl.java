package org.example.repo.repositories;

import org.example.models.drivers.DriverQualificationEnum;
import org.example.models.transports.Bus;
import org.example.models.transports.Tram;
import org.example.models.transports.Transport;
import org.example.repo.repoInterfaces.TransportRepo;

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

public class TransportRepoImpl implements TransportRepo {

    @Override
    public boolean add(Transport transport, Connection connection) {

        if (transport.getDriverQualificationEnum().equals(DriverQualificationEnum.BUS_DRIVER)) {
            return addBus(transport, connection);
        } else {
            return addTrain (transport, connection);
        }

    }

    private boolean addBus(Transport transport, Connection connection) {
        String sql = "INSERT INTO transport_service.transports_repo " +
                "(brand, numbers_passengers, qualification, type, numbers_of_doors)" +
                "VALUES ((?), (?), (?), (?), (?))";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, transport.getBrandOfTransport());
            preparedStatement.setInt(2, transport.getNumbersOfPassengers());
            preparedStatement.setString(3, String.valueOf(transport.getDriverQualificationEnum()));
            preparedStatement.setString(4, ((Bus) transport).getType());
            preparedStatement.setInt( 5, ((Bus) transport).getNumbersOfDoors());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean addTrain(Transport transport, Connection connection) {
        String sql = "INSERT INTO transport_service.transports_repo " +
                "(brand, numbers_passengers, qualification, numbers_of_railcar)" +
                "VALUES ((?), (?), (?), (?))";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, transport.getBrandOfTransport());
            preparedStatement.setInt(2, transport.getNumbersOfPassengers());
            preparedStatement.setString(3, String.valueOf(transport.getDriverQualificationEnum()));
            preparedStatement.setInt(4, ((Tram) transport).getNumbersOfRailcar());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id, Connection connection) {
        String sql = "DELETE FROM transport_service.transports_repo WHERE id = (?)";

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
    public Optional<Transport> getById(Integer id, Connection connection) {
        String sql = "SELECT * FROM transport_service.transports_repo WHERE id = (?)";
        Transport transport = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                String brand = resultSet.getString(2);
                int numbersOfPassengers = resultSet.getInt(3);
                String qualification = resultSet.getString(4);

                DriverQualificationEnum driverQualification = qualification.equals("BUS_DRIVER") ? DriverQualificationEnum.BUS_DRIVER : DriverQualificationEnum.TRAM_DRIVER;

                if (driverQualification.equals(DriverQualificationEnum.BUS_DRIVER)){
                    String type = resultSet.getString(6);
                    int numbersOfDoors = resultSet.getInt(7);

                    transport = new Bus(id, brand, numbersOfPassengers, driverQualification,type,numbersOfDoors);
                } else {
                    int numbersOfRailCar = resultSet.getInt(8);

                    transport = new Tram(id, brand, numbersOfPassengers, driverQualification, numbersOfRailCar);
                }

                return Optional.of(transport);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean updateById(Integer id, Connection connection) {

        DriverQualificationEnum driverQualificationEnum = getById(id, connection).get().getDriverQualificationEnum();

        if (String.valueOf(driverQualificationEnum).equals("BUS_DRIVER")) {
             return updateBus(id, connection);
        } else return updateTrain (id, connection);

    }

    private boolean updateBus(Integer id, Connection connection) {
        String sql = "UPDATE transport_service.transports_repo " +
                "SET brand = (?), numbers_passengers = (?), qualification = (?), type = (?), numbers_of_doors = (?) " +
                "WHERE id = (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, inputFromConsole());
            preparedStatement.setInt(2, Integer.parseInt(inputFromConsole()));
            preparedStatement.setString(3, inputFromConsole());
            preparedStatement.setString(4, inputFromConsole());
            preparedStatement.setInt(5, Integer.parseInt(inputFromConsole()));
            preparedStatement.setInt(6, id);

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updateTrain(Integer id, Connection connection) {
        String sql = "UPDATE transport_service.transports_repo " +
                "SET brand = (?), numbers_passengers = (?), qualification = (?), numbers_of_railcar = (?) " +
                "WHERE id = (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, inputFromConsole());
            preparedStatement.setInt(2, Integer.parseInt(inputFromConsole()));
            preparedStatement.setString(3, inputFromConsole());
            preparedStatement.setString(4, inputFromConsole());
            preparedStatement.setInt(5, id);

            int result = preparedStatement.executeUpdate();
            return result == 1;

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
    public List<Transport> getAll(Connection connection) {
        String sql = "SELECT * FROM transport_service.transports_repo";
        Transport transport = null;
        List <Transport> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String brand = resultSet.getString(2);
                Integer numbersPassangers = resultSet.getInt(3);
                String qualification = resultSet.getString(4);
                String type = resultSet.getString(6);
                Integer numbersOfDoor = resultSet.getInt(7);
                Integer numbersOfRailcar = resultSet.getInt(8);

                DriverQualificationEnum driverQualification = qualification.equals("BUS_DRIVER") ? DriverQualificationEnum.BUS_DRIVER : DriverQualificationEnum.TRAM_DRIVER;

                if (driverQualification.equals(DriverQualificationEnum.BUS_DRIVER)) {
                    transport = new Bus(id, brand,numbersPassangers, driverQualification, type, numbersOfDoor);
                } else {
                    transport = new Tram(id , brand, numbersPassangers, driverQualification, numbersOfRailcar);
                }
                list.add(transport);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
