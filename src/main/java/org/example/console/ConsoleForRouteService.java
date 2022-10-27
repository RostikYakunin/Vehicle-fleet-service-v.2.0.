package org.example.console;

import org.example.models.routes.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;

public class ConsoleForRouteService extends ConsoleMain {

    public ConsoleForRouteService(Connection connection) {
        super(connection);
    }

    protected void serviceForRouts() {
        System.out.println(
                "============================================================================================" +
                        "\n Input number of action: \n" +
                        "\n " +
                        "\n Add route - 1 " +
                        "\n Delete route - 2" +
                        "\n Find route by id - 3 " +
                        "\n Find all routes - 4 " +
                        "\n Find routes without transport - 5 " +
                        "\n Back to main menu - 6 ");

        int switcher = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));

        switch (switcher) {
            case 1:
                addRoute();
                timerForRouts();
                break;

            case 2:
                System.out.println("Input route`s id for delete: ");
                int idDelete = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));
                routeService.removeRoute(idDelete, connection);
                timerForRouts();
                break;

            case 3:
                System.out.println("Input route`s id for find: ");
                int idFind = validatorForIntegerInputValues(Integer.parseInt(inputFromConsole()));
                System.out.println(routeService.findRouteById(idFind, connection));
                timerForRouts();
                break;

            case 4:
                System.out.println(routeService.findAllRoutes(connection));
                timerForRouts();
                break;

            case 5:
                System.out.println(routeService.findAllRoutesWithoutTransport(connection));
                timerForRouts();
                break;

            case 6:
                startPanel();
                break;

            default:
                System.err.println("Invalid input, please try again !");
                serviceForRouts();
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

    private void addRoute() {
        try {
            System.out.println("Input start of route");
            String start = validatorForStringInputValues(inputFromConsole());

            System.out.println("Input end of route");
            String end = validatorForStringInputValues(inputFromConsole());

            Route route = new Route(start, end);
            routeService.addRoute(route, connection);

            System.out.println("------------------------------------------------------------------------------------");
            System.out.println("Route`s add to database is successful: " + route);
            System.out.println("------------------------------------------------------------------------------------");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.err.println("Something wrong with input value, return to service for routes ! ");
            timerForRouts();
        }
    }

    private void timerForRouts() {
        System.out.println("Back to routes menu after 5 seconds !");
        try {
            Thread.sleep(5000);
            serviceForRouts();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer validatorForIntegerInputValues(Integer integer) {
        if (integer == null || integer <= 0) {
            System.err.println("Invalid input, please try again");
            addRoute();
        }
        return integer;
    }

    private String validatorForStringInputValues(String string) {
        if (string == null) {
            System.err.println("Invalid input, please try again");
            addRoute();
        }
        return string;
    }
}
