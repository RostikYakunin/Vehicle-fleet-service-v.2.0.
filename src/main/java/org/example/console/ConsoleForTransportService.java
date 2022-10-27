package org.example.console;

import org.example.models.drivers.DriverQualificationEnum;
import org.example.models.transports.Bus;
import org.example.models.transports.Tram;
import org.example.models.transports.Transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.InputMismatchException;

public class ConsoleForTransportService extends ConsoleMain {

    public ConsoleForTransportService(Connection connection) {
        super(connection);
    }

    protected void serviceForTransports() {
        System.out.println("============================================================================================" +
                "\n Input number of action:" +
                "\n " +
                "\n Add transport - 1" +
                "\n Delete transport - 2" +
                "\n Find transport by id - 3" +
                "\n Find all transports - 4" +
                "\n Find transport by mark - 5" +
                "\n Find all transports without driver - 6" +
                "\n Add transport to route - 7" +
                "\n Delete transport from route - 8" +
                "\n Back to main menu - 9");

        int switcher = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

        switch (switcher) {
            case 1:
                addTransport();
                timerForTransports();
                break;

            case 2:
                System.out.println("Input transport`s id for delete: ");
                int idDelete = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));
                transportService.removeTransport(idDelete, connection);
                timerForTransports();
                break;

            case 3:
                System.out.println("Input transport`s id to find : ");
                int idFind = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));
                System.out.println(transportService.findTransportById(idFind, connection));
                timerForTransports();
                break;

            case 4:
                System.out.println(transportService.findAllTransports(connection));
                timerForTransports();
                break;

            case 5:
                System.out.println("Input transport`s mark to find all: ");
                String brand = validatorForStringInputValues(inputFromConsole());
                System.out.println(transportService.findTransportByBrand(brand, connection));
                timerForTransports();
                break;

            case 6:
                System.out.println(transportService.findTransportWithoutDriver(connection));
                timerForTransports();
                break;

            case 7:
                System.out.println("Input transport`s id: ");
                int idOfTransport = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

                System.out.println("Input route`s id: ");
                int idOfRoute = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

                transportService.addTransportToRoute(transportService.findTransportById(idOfTransport, connection).get(), routeService.findRouteById(idOfRoute, connection).get(), connection);
                timerForTransports();
                break;
            case 8:
                System.out.println("Input transport`s id : ");
                int idTransport = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

                System.out.println("Input route`s id :");
                int idRoute = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

                transportService.removeTransportFromRoute(transportService.findTransportById(idTransport, connection).get(), routeService.findRouteById(idRoute, connection).get(), connection);
                timerForTransports();
                break;

            case 9:
                startPanel();
                break;

            default:
                System.out.println("Invalid input, please try again !");
                serviceForTransports();
                break;
        }
    }

    private String inputFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return inputFromConsole();
    }

    private void addTransport() {
        System.out.println("Input number of action:" +
                "\n Add bus" +
                "\n Add tram");

        int switchTransports = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

        try {
            switch (switchTransports) {
                case 1:
                    addBus();
                    timerForTransports();
                    break;

                case 2:
                    addTram();
                    break;

                default: {
                    System.err.println("Invalid input, back to transport`s menu !");
                    serviceForTransports();
                }
            }

        } catch (InputMismatchException e) {
            System.err.println("Invalid input, back to transport`s menu !");
            serviceForTransports();
        }
    }

    private void addBus() {
        System.out.println("Input bus`s mark: ");
        String brand = validatorForStringInputValues(inputFromConsole());

        System.out.println("Input numbers of passengers :");
        int passengers = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

        System.out.println("Input type of bus:");
        String type = validatorForStringInputValues(inputFromConsole());

        System.out.println("Input numbers of doors : ");
        int doors = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

        Transport bus = new Bus(brand, passengers, DriverQualificationEnum.BUS_DRIVER, type, doors);
        transportService.addTransport(bus, connection);

        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Bus add successful: " + bus);
        System.out.println("------------------------------------------------------------------------------------");
    }

    private void addTram() {
        System.out.println("Input tram`s mark : ");
        String brand = validatorForStringInputValues(inputFromConsole());

        System.out.println("Input numbers of passengers :");
        int passengers = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

        System.out.println("Input numbers of railcar :");
        int railcar = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

        Transport tram = new Tram(brand, passengers, DriverQualificationEnum.TRAM_DRIVER, railcar);
        transportService.addTransport(tram, connection);

        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("Tram add successful: " + tram);
        System.out.println("------------------------------------------------------------------------------------");
    }

    private void timerForTransports() {
        System.out.println("Back to transport`s service menu after 5 seconds !");
        try {
            Thread.sleep(5000);
            serviceForTransports();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer validatorForIntegerInputValues(Integer integer) {
        if (integer == null || integer <= 0) {
            System.err.println("Invalid input, please try again");
            addTransport();
        }
        return integer;
    }

    private String validatorForStringInputValues(String string) {
        if (string == null) {
            System.err.println("Invalid input, please try again");
            addTransport();
        }
        return string;
    }
}
