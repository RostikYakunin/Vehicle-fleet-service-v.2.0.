package org.example.services.interfaces;



import org.example.models.routes.Route;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface RouteService {

    Optional<Route> addRoute(Route route, Connection connection);
    void removeRoute (Integer id, Connection connection);
    Optional<Route> findRouteById (Integer id, Connection connection);
    List <Route> findAllRoutes (Connection connection);
    List <Route> findAllRoutesWithoutTransport (Connection connection);

}
