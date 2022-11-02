package org.example.services.serviceImpl;


import org.example.models.routes.Route;
import org.example.models.drivers.Driver;
import org.example.models.transports.Transport;
import org.example.repo.repoInterfaces.DriversRepo;
import org.example.repo.repoInterfaces.TransportRepo;
import org.example.services.interfaces.DriverService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverServiceImpl implements DriverService {

    DriversRepo driversRepo;
    TransportRepo transportRepo;

    public DriverServiceImpl(DriversRepo driversRepo, TransportRepo transportRepo) {
        this.driversRepo = driversRepo;
        this.transportRepo = transportRepo;
    }

    @Override
    public Driver addDriver(Driver driver, Connection connection) {
        if (driver == null) {
            System.err.println("Invalid input !");
            return null;
        }

        driversRepo.add(driver, connection);
        return driver;
    }

    @Override
    public Optional<Driver> findDriverById(Integer id, Connection connection) {
        if (driversRepo.getById(id, connection).isEmpty()) {
            System.err.println("Driver with id = " + id + " not found !");
            return Optional.empty();
        }

        return Optional.of(driversRepo.getById(id, connection).get());
    }

    @Override
    public boolean removeDriver(Integer id, Connection connection) {
        if (driversRepo.getById(id, connection).isEmpty()) {
            System.err.println("Driver with id = " + id + " not found !");
            return false;
        }

        if (driversRepo.getById(id, connection).get().getTransport() != null) {
            System.err.println("This driver can`t be deleted, driver is assigned to the vehicle ! " +
                    "You must delete this driver from vehicle and try again !");
            return false;
        }

        driversRepo.deleteById(id, connection);
        return true;
    }

    @Override
    public List<Driver> findAllDrivers(Connection connection) {
        return driversRepo.getAll(connection);
    }


    @Override
    public List<Driver> findAllDriversBySurname(String surname, Connection connection) {
        List<Driver> list = new ArrayList<>();

        if (surname == null) {
            System.err.println("Invalid input !");
            return list;
        }

        for (Driver dr : driversRepo.getAll(connection)) {
            if (dr.getSurnameOfDriver().equals(surname)) {
                list.add(dr);
            }
        }

        if (list.isEmpty()) {
            System.err.println("Input surname doesn`t exist !");
        }

        return list;
    }


    @Override
    public List<Driver> findAllDriversOnRoute(Route route, Connection connection) {
        List<Driver> drivers = new ArrayList<>();

        if (route == null) {
            System.err.println("Route not found !");
            return drivers;
        }

        if (route.getTransport().getDriver() == null) {
            System.err.println("Driver doesn`t exists on this route !");
            return drivers;
        }

        drivers.add(route.getTransport().getDriver());
        return drivers;
    }

    @Override
    public List<Transport> findAllTransportsWithoutDriver(Connection connection) {
        List<Transport> tempList = new ArrayList<>();

        for (Transport tr : transportRepo.getAll(connection)) {
            if (tr.getDriver() == null) {
                tempList.add(tr);
            }
        }

        if (tempList.isEmpty()) {
            System.err.println("List is empty !");
        }

        return tempList;
    }

    @Override
    public boolean addDriverOnTransport(Driver driver, Transport transport, Connection connection) {

        if (driver == null || transport == null) {
            System.err.println("Driver or transport not found !");
            return false;
        }

        if (!driver.getQualificationEnum().equals(transport.getDriverQualificationEnum())) {
            System.err.println("Error ! Driver`s and transport`s qualification not equal");
            return false;
        }

        transport.setDriver(driver);
        return true;
    }
}
