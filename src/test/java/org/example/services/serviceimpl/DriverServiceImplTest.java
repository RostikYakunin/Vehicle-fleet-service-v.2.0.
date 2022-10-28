package org.example.services.serviceimpl;

import net.bytebuddy.asm.Advice;
import org.example.models.drivers.Driver;
import org.example.models.drivers.DriverQualificationEnum;
import org.example.models.transports.Tram;
import org.example.models.transports.Transport;
import org.example.repo.DBConnection.DBConnect;
import org.example.repo.repoImpl.DriverRepoImpl;
import org.example.repo.repoInterfaces.DriversRepo;
import org.example.services.interfaces.DriverService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DriverServiceImplTest {

    Connection connection;

    @Mock
    DriverRepoImpl driverRepo;

    @InjectMocks
    DriverServiceImpl driverService;

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
    public void addDriver() {
        //Given
        ArgumentCaptor<Driver> driverArgumentCaptor = ArgumentCaptor.forClass(Driver.class);
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Driver expectedDriver = new Driver(1, "Name", "Surname", "03404040", DriverQualificationEnum.BUS_DRIVER);

        //When
        when(driverRepo.add(any(Driver.class), any(Connection.class))).thenReturn(true);
        Driver actualDriver = driverService.addDriver(expectedDriver, connection);

        //Then
        assertEquals(expectedDriver.getId(), actualDriver.getId());
        verify(driverRepo, times(1)).add(driverArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void findDriverById() {
        //Given
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Driver expectedDriver = new Driver(1, "Name", "Surname", "03404040", DriverQualificationEnum.BUS_DRIVER);

        //When
        when(driverRepo.getById(any(Integer.class), any(Connection.class))).thenReturn(Optional.of(expectedDriver));
        Optional<Driver> actualDriver = driverService.findDriverById(1, connection);

        //Then
        assertEquals(expectedDriver, actualDriver.get());
        verify(driverRepo, times(2)).getById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void removeDriver() {
        //Given
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        Driver expectedDriver = new Driver(1, "Name", "Surname", "03404040", DriverQualificationEnum.BUS_DRIVER);
        boolean expectedResult = true;

        //When
        when(driverRepo.getById(any(Integer.class), any(Connection.class))).thenReturn(Optional.of(expectedDriver));
        when(driverRepo.deleteById(any(Integer.class), any(Connection.class))).thenReturn(true);
        boolean actualResult = driverService.removeDriver(1, connection);

        //Then
        assertEquals(expectedResult, actualResult);
        verify(driverRepo, times(2)).getById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
        verify(driverRepo, times(1)).deleteById(integerArgumentCaptor.capture(), connectionArgumentCaptor.capture());
    }

    @Test
    public void findAllDrivers() {
        //Given
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        List<Driver> expectedList = List.of(new Driver(1, "Name", "Surname", "03404040", DriverQualificationEnum.BUS_DRIVER));

        //When
        when(driverRepo.getAll(any(Connection.class))).thenReturn(expectedList);
        List<Driver> actualList = driverService.findAllDrivers(connection);

        //Then
        assertEquals(expectedList,actualList);
        verify(driverRepo, times(1)).getAll(connectionArgumentCaptor.capture());
    }

    @Test
    public void findAllDriversBySurname() {
        //Given
        ArgumentCaptor<Connection> connectionArgumentCaptor = ArgumentCaptor.forClass(Connection.class);
        List<Driver> expectedList = List.of(new Driver(1, "Name", "Surname", "03404040", DriverQualificationEnum.BUS_DRIVER));
        String expectedSurname = "Surname";

        //When
        when(driverRepo.getAll(any(Connection.class))).thenReturn(expectedList);
        List<Driver> actualList = driverService.findAllDriversBySurname(expectedSurname, connection);
        String actualSurname = actualList.get(0).getSurnameOfDriver();

        //Then
        assertEquals(expectedSurname, actualSurname);
        verify(driverRepo, times(1)).getAll(connectionArgumentCaptor.capture());
    }

    @Test
    public void findAllDriversOnRoute() {
    }

    @Test
    public void findAllTransportsWithoutDriver() {
    }

    @Test
    public void addDriverOnTransport() {
    }
}