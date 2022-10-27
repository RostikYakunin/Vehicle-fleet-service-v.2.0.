package org.example.console;

import org.example.models.drivers.Driver;
import org.example.models.drivers.DriverQualificationEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class ConsoleForDriverService extends ConsoleMain {

    public ConsoleForDriverService(Connection connection) {
        super(connection);
    }

    protected void serviceForDrivers() {
        System.out.println("============================================================================================" +
                "\n Input number of action :" +
                "\n " +
                "\n Add driver - 1" +
                "\n Delete driver - 2" +
                "\n Add driver on transport - 3" +
                "\n Find driver by id - input 4" +
                "\n Show all drivers - input 5" +
                "\n Find drivers by surname - input 6" +
                "\n Find drivers on route - input 7" +
                "\n Find all transports without drivers - 8" +
                "\n Back to main menu - 9");


        int switcher = Integer.parseInt(inputFromConsole());
        switch (switcher) {
            case 1:
                addDriver();
                timerForDrivers();
                break;

            case 2:
                System.out.println("Input driver`s id for delete: ");
                int idDelete = Integer.parseInt(inputFromConsole());
                driverService.removeDriver(idDelete, connection);
                timerForDrivers();
                break;

            case 3:
                System.out.println("Input driver`s id that will be added on transport: ");
                int idOfDriver = Integer.parseInt(inputFromConsole());
                System.out.println("Input transport`s id: ");
                int idOfTransport = Integer.parseInt(inputFromConsole());
                driverService.addDriverOnTransport(driverService.findDriverById(idOfDriver, connection).get(), transportService.findTransportById(idOfTransport, connection).get(), connection);
                timerForDrivers();
                break;
            case 4:
                System.out.println("Input driver`s id for find: ");
                int idFinde = Integer.parseInt(inputFromConsole());
                System.out.println(driverService.findDriverById(idFinde, connection));
                timerForDrivers();
                break;

            case 5:
                System.out.println(driverService.findAllDrivers(connection));
                timerForDrivers();
                break;

            case 6:
                System.out.println("Input driver`s surname: ");
                String surnameOfDriver = inputFromConsole();
                System.out.println(driverService.findAllDriversBySurname(surnameOfDriver, connection));
                timerForDrivers();
                break;

            case 7:
                System.out.println("Input route`s id: ");
                int idOfRoute = Integer.parseInt(inputFromConsole());
                driverService.findAllDriversOnRoute(routeService.findRouteById(idOfRoute, connection).get(), connection);
                timerForDrivers();
                break;

            case 8:
                System.out.println(driverService.findAllTransportsWithoutDriver(connection));
                timerForDrivers();
                break;

            case 9:
                super.startPanel();
                break;

            default:
                System.err.println("Invalid input ! Try again !");
                serviceForDrivers();
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

    private void addDriver() {
        try {
            System.out.println("Input driver`s name: ");
            String name = validatorForStringInputValues(inputFromConsole());

            System.out.println("Input driver`s surname: ");
            String surname = validatorForStringInputValues(inputFromConsole());

            System.out.println("Input driver`s phone number: ");
            String number = validatorForStringInputValues(inputFromConsole());

            System.out.println("Input driver`s qualification: " +
                    "\n1 - BUS_DRIVER;" +
                    "\n2 - TRAM_DRIVER");

            int qualification = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));
            DriverQualificationEnum driverQualificationEnum = switchDriverQualificationEnum(qualification);

            Driver driver = new Driver(name, surname, number, driverQualificationEnum);
            driverService.addDriver(driver, connection);

            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("Driver`s add to database is successful " + driver);
            System.out.println("------------------------------------------------------------------------------------");

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println("Something wrong with input value, return to service for drivers ! ");
            timerForDrivers();
        }
    }

    private DriverQualificationEnum switchDriverQualificationEnum(int qualification) {
        DriverQualificationEnum driverQualificationEnum = null;

        switch (qualification) {
            case 1:
                driverQualificationEnum = DriverQualificationEnum.BUS_DRIVER;
                break;

            case 2:
                driverQualificationEnum = DriverQualificationEnum.TRAM_DRIVER;
                break;

            default:
                System.err.println("Invalid input !");
        }
        return driverQualificationEnum;
    }

    private void timerForDrivers() {
        System.err.println("Back to driver`s service menu after 5 seconds ! ");
        try {
            TimeUnit.SECONDS.sleep(5);
            serviceForDrivers();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer validatorForIntegerInputValues(Integer integer) {
        if (integer == null || integer <= 0) {
            System.err.println("Invalid input, please try again");
            addDriver();
        }
        return integer;
    }

    private String validatorForStringInputValues(String string) {
        if (string == null) {
            System.err.println("Invalid input, please try again");
            addDriver();
        }
        return string;
    }
}
