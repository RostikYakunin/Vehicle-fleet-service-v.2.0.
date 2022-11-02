package org.example.services;

import org.example.models.routes.Route;
import org.example.repo.DBConnection.DBConnect;
import org.example.repo.repositories.RouteRepoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RouteServiceImplTest {

    Connection connection;

    @Mock
    RouteRepoImpl routeRepo;

    @InjectMocks
    RouteServiceImpl routeService;

    @Before
    public void beforeMethods() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.initMocks(this);
        connection = DBConnect.getConnection();
    }

    @After
    public void afterMethods() throws SQLException {
        connection.close();
    }

    @Test
    public void addRoute() {
        //Given
        ArgumentCaptor <Route> argumentCaptor = ArgumentCaptor.forClass(Route.class);
        ArgumentCaptor <Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Route expectedRoute = new Route(1, "Start", "End");

        //When
        when(routeRepo.add(any(Route.class), any(Connection.class))).thenReturn(true);
        Route actualRoute = routeService.addRoute(expectedRoute, connection).get();

        //Then
        assertEquals(expectedRoute.getId(), actualRoute.getId());
        verify(routeRepo, times(1)).add(argumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void findRouteById() {
        //Given
        ArgumentCaptor <Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor <Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Route expectedRoute = new Route(1, "Start", "End");

        //When
        when(routeRepo.getById(any(Integer.class), any(Connection.class))).thenReturn(Optional.of(expectedRoute));
        Optional <Route> actualRoute = routeService.findRouteById(1, connection);

        //Then
        assertEquals(expectedRoute.getId(), actualRoute.get().getId());
        verify(routeRepo, times(2)).getById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void removeRoute() {
        //Given
        ArgumentCaptor <Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor <Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Route expectedRoute = new Route(1, "Start", "End");
        boolean expectedResult = true;

        //When
        when(routeRepo.getById(any(Integer.class), any(Connection.class))).thenReturn(Optional.of(expectedRoute));
        when(routeRepo.deleteById(any(Integer.class), any(Connection.class))).thenReturn(true);
        boolean actualResult = routeService.removeRoute(1, connection);

        //Then
        assertEquals(expectedResult, actualResult);
        verify(routeRepo, times(2)).getById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
        verify(routeRepo, times(1)).deleteById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void findAllRoutes() {
        //Given
        ArgumentCaptor <Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        List <Route> expectedList = List.of(new Route(1, "Start", "End"));

        //When
        when(routeRepo.getAll(any(Connection.class))).thenReturn(expectedList);
        List<Route> actualList = routeService.findAllRoutes(connection);

        //Then
        assertEquals(expectedList,actualList);
        verify(routeRepo, times(1)).getAll(connectionArgumentCaptor.capture());
    }

    @Test
    public void findAllRoutesWithoutTransport() {
        //Given
        ArgumentCaptor <Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        List <Route> expectedList = List.of(new Route(1, "Start", "End"));

        //When
        when(routeRepo.getAll(any(Connection.class))).thenReturn(expectedList);
        List<Route> actualList = routeService.findAllRoutesWithoutTransport(connection);

        //Then
        assertEquals(expectedList, actualList);
        verify(routeRepo, times(1)).getAll(connectionArgumentCaptor.capture());
    }
}