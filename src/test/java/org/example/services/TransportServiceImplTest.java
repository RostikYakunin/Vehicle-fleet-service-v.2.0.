package org.example.services;

import org.example.models.drivers.Driver;
import org.example.models.drivers.DriverQualificationEnum;
import org.example.models.routes.Route;
import org.example.models.transports.Tram;
import org.example.models.transports.Transport;
import org.example.repo.DBConnection.DBConnect;
import org.example.repo.repositories.TransportRepoImpl;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransportServiceImplTest {
    Connection connection;

    @Mock
    TransportRepoImpl transportRepo;

    @InjectMocks
    TransportServiceImpl transportService;

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
    public void addTransport() {
        //Given
        ArgumentCaptor<Transport> transportArgumentCaptor = ArgumentCaptor.forClass(Transport.class);
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Transport expectedTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);

        //When
        when(transportRepo.add(any(Transport.class), any(Connection.class))).thenReturn(true);
        Optional<Transport> actualTransport = transportService.addTransport(expectedTransport, connection);

        //Then
        assertEquals(expectedTransport.getId(), actualTransport.get().getId());
        verify(transportRepo, times(1)).add(transportArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void findTransportById() {
        //Given
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Transport expectedTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);

        //When
        when(transportRepo.getById(any(Integer.class), any(Connection.class))).thenReturn(Optional.of(expectedTransport));
        Optional<Transport> actualTransport = transportService.findTransportById(1, connection);

        //Then
        assertEquals(expectedTransport.getId(), actualTransport.get().getId());
        verify(transportRepo, times(2)).getById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void removeTransport() {
        //Given
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Transport expectedTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);
        boolean expectedResult = true;

        //When
        when(transportRepo.getById(any(Integer.class), any(Connection.class))).thenReturn(Optional.of(expectedTransport));
        when(transportRepo.deleteById(any(Integer.class), any(Connection.class))).thenReturn(true);
        boolean actualResult = transportService.removeTransport(1, connection);

        //Then
        assertEquals(expectedResult, actualResult);
        verify(transportRepo, times(2)).getById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
        verify(transportRepo, times(1)).deleteById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void findAllTransports() {
        //Given
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Transport testTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);
        List<Transport> expectedList = List.of(testTransport);

        //When
        when(transportRepo.getAll(any(Connection.class))).thenReturn(expectedList);
        List<Transport> actualList = transportService.findAllTransports(connection);

        //Then
        assertEquals(expectedList, actualList);
        verify(transportRepo, times(1)).getAll(connectionArgumentCaptor.capture());
    }

    @Test
    public void findTransportByBrand() {
        //Given
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Transport testTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);
        List<Transport> expectedList = List.of(testTransport);
        String expectedBrand = "Mark";

        //When
        when(transportRepo.getAll(any(Connection.class))).thenReturn(expectedList);
        List<Transport> actualList = transportService.findTransportByBrand(expectedBrand, connection);

        //Then
        assertEquals(expectedBrand, actualList.get(0).getBrandOfTransport());
        verify(transportRepo, times(1)).getAll(connectionArgumentCaptor.capture());
    }

    @Test
    public void addTransportToRoute() {
        //Given
        Transport testTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);
        Route testRoute = new Route(1, "Start", "End");
        Driver testDriver = new Driver(1, "Name", "Surname", "0583495", DriverQualificationEnum.TRAM_DRIVER);
        boolean expectedResult = true;

        //When
        testTransport.setDriver(testDriver);
        boolean actualResult = transportService.addTransportToRoute(testTransport, testRoute, connection);

        //Then
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void findTransportWithoutDriver() {
        //Given
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Transport testTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);
        List<Transport> expectedList = List.of(testTransport);

        //When
        when(transportRepo.getAll(any(Connection.class))).thenReturn(expectedList);
        List<Transport> actualList = transportService.findTransportWithoutDriver(connection);

        //Then
        assertEquals(expectedList, actualList);
        verify(transportRepo, times(1)).getAll(connectionArgumentCaptor.capture());
    }

    @Test
    public void removeTransportFromRoute() {
        //Given
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Transport testTransport = new Tram(1, "Mark", 20, DriverQualificationEnum.TRAM_DRIVER, 3);
        Route testRoute = new Route(1, "Start", "End");
        boolean expectedResult = true;

        //When
        testRoute.setTransport(testTransport);
        boolean actualResult = transportService.removeTransportFromRoute(testTransport, testRoute, connection);

        //Then
        assertEquals(expectedResult, actualResult);
    }
}