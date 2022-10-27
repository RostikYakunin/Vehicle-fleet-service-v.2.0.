package org.example.console;

import org.example.repo.repoImpl.DriverRepoImpl;
import org.example.repo.repoImpl.RouteRepoImpl;
import org.example.repo.repoImpl.TransportRepoImpl;
import org.example.repo.repoInterfaces.DriversRepo;
import org.example.repo.repoInterfaces.RouteRepo;
import org.example.repo.repoInterfaces.TransportRepo;
import org.example.services.interfaces.DriverService;
import org.example.services.interfaces.RouteService;
import org.example.services.interfaces.TransportService;
import org.example.services.serviceimpl.DriverServiceImpl;
import org.example.services.serviceimpl.RouteServiceImpl;
import org.example.services.serviceimpl.TransportServiceImpl;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConsoleMain {

    protected final TransportService transportService;
    protected final DriverService driverService;
    protected final RouteService routeService;
    protected final Connection connection;

    static {
        System.out.println("Welcome to the vehicle service !");
    }

    public ConsoleMain(Connection connection) {
        TransportRepo transportRepo = new TransportRepoImpl();
        this.transportService = new TransportServiceImpl(transportRepo);

        DriversRepo driversRepo = new DriverRepoImpl();
        this.driverService = new DriverServiceImpl(driversRepo, transportRepo);

        RouteRepo routeRepo = new RouteRepoImpl();
        this.routeService = new RouteServiceImpl(routeRepo);

        this.connection = connection;
    }

    public void startPanel(){
        System.out.println(
                """
                        ================================================================================================
                        Main menu !\s
                        Input number of action !
                        ------------------------------------------------------------------------------------------------
                        Transport`s service - input "1"\s
                        Route`s service - input "2"\s
                        Driver`s service - input "3"\s
                        Exit from programme - input "0"\s
                        ------------------------------------------------------------------------------------------------
                        """
        );

        try {
            Scanner in = new Scanner(System.in);
            int number = in.nextInt();

            switch (number) {
                case 1 -> {
                    ConsoleForTransportService consoleForTransportService = new ConsoleForTransportService(connection);
                    consoleForTransportService.serviceForTransports();
                }
                case 2 -> {
                    ConsoleForRouteService consoleForRouteService = new ConsoleForRouteService(connection);
                    consoleForRouteService.serviceForRouts();
                }
                case 3 -> {
                    ConsoleForDriverService consoleForDriverService = new ConsoleForDriverService(connection);
                    consoleForDriverService.serviceForDrivers();
                }
                case 0 -> System.exit(0);

                default -> {
                    System.err.println("Invalid input ! ");
                    timerForStartPanel();
                }
            }
        } catch (InputMismatchException e) {
            System.err.println("Invalid input !");
            timerForStartPanel();
        }
    }

    private void timerForStartPanel() {
        System.out.println("After 5 seconds return to the main menu !");
        try {
            TimeUnit.SECONDS.sleep(5);
            startPanel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}





