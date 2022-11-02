package org.example.services.serviceImpl;


import org.example.models.routes.Route;
import org.example.repo.repoInterfaces.RouteRepo;
import org.example.services.interfaces.RouteService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteServiceImpl implements RouteService {

    private final RouteRepo routeRepo;

    public RouteServiceImpl(RouteRepo routeRepo) {
        this.routeRepo = routeRepo;
    }

    @Override
    public Optional<Route> addRoute(Route route, Connection connection) {
        if (route == null) {
            System.err.println("Invalid input !");
            return Optional.empty();
        }

        routeRepo.add(route, connection);
        return Optional.of(route);
    }

    @Override
    public Optional<Route> findRouteById(Integer id, Connection connection) {
        if (routeRepo.getById(id, connection).isEmpty()) {
            System.err.println("Route with id = " + id + " not found !");
            return Optional.empty();
        }

        return Optional.of(routeRepo.getById(id, connection).get());
    }

    @Override
    public boolean removeRoute(Integer id, Connection connection) {
        if (routeRepo.getById(id, connection).isEmpty()) {
            System.err.println("Route with id = " + id + " not found !");
            return false;
        }

        if (routeRepo.getById(id, connection).get().getTransport() != null) {
            System.err.println("This route can`t be deleted, route is assigned to the vehicle ! ");
            return false;
        }

        routeRepo.deleteById(id, connection);
        return true;
    }

    @Override
    public List<Route> findAllRoutes(Connection connection) {
        return routeRepo.getAll(connection);
    }

    @Override
    public List<Route> findAllRoutesWithoutTransport(Connection connection) {
        List<Route> tempList = new ArrayList<>();

        for (Route rt : routeRepo.getAll(connection)) {
            if (rt.getTransport() == null) {
                tempList.add(rt);
            }
        }

        if (tempList.isEmpty()) {
            System.err.println("List is empty !");
        }

        return tempList;
    }
}
