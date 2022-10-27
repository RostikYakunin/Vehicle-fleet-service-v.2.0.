package org.example.services.serviceimpl;


import org.example.models.routes.Route;
import org.example.models.transports.Transport;
import org.example.repo.repoInterfaces.TransportRepo;
import org.example.services.interfaces.TransportService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransportServiceImpl implements TransportService {

    private final TransportRepo transportRepo;

    public TransportServiceImpl(TransportRepo transportRepo) {
        this.transportRepo = transportRepo;
    }

    @Override
    public Optional<Transport> addTransport(Transport transport, Connection connection) {
        if (transport == null) {
            System.err.println("Invalid input !");
            return Optional.empty();
        }

        transportRepo.add(transport, connection);
        return Optional.of(transport);
    }

    @Override
    public Optional<Transport> findTransportById(Integer id, Connection connection) {
        if (transportRepo.getById(id,connection).isEmpty()) {
            System.err.println("Transport with id = " + id + " not found !");
            return Optional.empty();
        }

        return Optional.of(transportRepo.getById(id, connection).get());
    }

    @Override
    public void removeTransport(Integer id, Connection connection) {
        if (transportRepo.getById(id, connection).isEmpty()) {
            System.err.println("Transport with id = " + id + " not found !");
            return;
        }

        if (transportRepo.getById(id, connection).get().getDriver() != null) {
            System.err.println("This transport can`t be deleted, transport is assigned by driver ! ");
            return;
        }

        transportRepo.deleteById(id, connection);
    }

    @Override
    public List<Transport> findAllTransports(Connection connection) {
        return transportRepo.getAll(connection);
    }

    @Override
    public Optional<List<Transport>> findTransportByBrand(String brand, Connection connection) {
        List<Transport> tempList = new ArrayList<>();

        if (brand == null) {
            System.err.println("Invalid input !");
            return Optional.empty();
        }

        for (Transport tr : transportRepo.getAll(connection)) {
            if (tr.getBrandOfTransport().equals(brand)) {
                tempList.add(tr);
            }
        }

        if (tempList.isEmpty()) {
            System.err.println("Input brand doesn`t exist !");
        }

        return Optional.of(tempList);
    }

    @Override
    public Optional<Route> addTransportToRoute(Transport transport, Route route, Connection connection) {

        if (transport == null || route == null) {
            System.err.println("Route or transport not found !");
            return Optional.empty();
        }

        if (transport.getDriver() == null || transport.getRoute() != null) {
            System.err.println("Transport doesn`t have driver or transport has assigned on other route !");
            return Optional.empty();
        }

        route.setTransport(transport);
        return Optional.of(route);
    }

    @Override
    public List<Transport> findTransportWithoutDriver(Connection connection) {
        List<Transport> tempList = new ArrayList<>();

        for (Transport tr : transportRepo.getAll(connection)) {
            if (tr.getDriver() == null) {
                tempList.add(tr);
            }
        }

        return tempList;
    }

    @Override
    public void removeTransportFromRoute(Transport transport, Route route, Connection connection) {
        if (transport == null && route == null) {
            System.err.println("Input transport or route not found !");
            return;
        }

        if (route.getTransport() == null) {
            System.err.println("There are no transports for deletion on this route !");
            return;
        }

        route.setTransport(null);
    }
}
