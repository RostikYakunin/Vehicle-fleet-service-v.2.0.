package org.example.services.interfaces;


import org.example.models.routes.Route;
import org.example.models.drivers.Driver;
import org.example.models.transports.Transport;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface DriverService {
    Driver addDriver (Driver driver, Connection connection);
    boolean removeDriver (Integer id, Connection connection);
    Optional<Driver> findDriverById (Integer id, Connection connection);
    List<Driver> findAllDriversBySurname(String surname, Connection connection);
    List <Driver> findAllDrivers(Connection connection);
    List<Driver> findAllDriversOnRoute(Route route, Connection connection);
    List <Transport> findAllTransportsWithoutDriver(Connection connection);
   boolean addDriverOnTransport (Driver driver, Transport transport, Connection connection);


}
