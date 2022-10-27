package org.example.services.interfaces;

import org.example.models.routes.Route;
import org.example.models.transports.Transport;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface TransportService {
    Optional<Transport> addTransport (Transport transport, Connection connection);
    void removeTransport (Integer id, Connection connection);
    Optional<Transport> findTransportById (Integer id, Connection connection);
    List<Transport> findAllTransports (Connection connection);
    Optional<List<Transport>> findTransportByBrand(String brand, Connection connection);
    List<Transport> findTransportWithoutDriver(Connection connection);
    Optional<Route> addTransportToRoute(Transport transport, Route route, Connection connection);
    void removeTransportFromRoute(Transport transport, Route route, Connection connection);
}
