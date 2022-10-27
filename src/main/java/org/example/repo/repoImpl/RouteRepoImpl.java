package org.example.repo.repoImpl;

import org.example.models.routes.Route;
import org.example.repo.repoInterfaces.RouteRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RouteRepoImpl implements RouteRepo {
    @Override
    public boolean add(Route route, Connection connection) {
        String sql = "INSERT INTO transport_service.routes_repo " +
                "(start_way, end_way) " +
                "VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, route.getStartOfWay());
            preparedStatement.setString(2, route.getEndOfWay());

            int result = preparedStatement.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id, Connection connection) {
        String sql = "DELETE from transport_service.routes_repo where id = (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int result = preparedStatement.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Route> getById(Integer id, Connection connection) {
        String sql = "SELECT * FROM transport_service.routes_repo WHERE id = (?)";
        Route route = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                String startOfWay = resultSet.getString(2);
                String endOFWay = resultSet.getString(3);

                route = new Route(id, startOfWay, endOFWay);
                return Optional.of(route);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean updateById(Integer id, Connection connection) {
        String sql = "UPDATE transport_service.routes_repo " +
                "SET start_way = (?), end_way = (?)" +
                "WHERE id = (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, inputFromConsole());
            preparedStatement.setString(2, inputFromConsole());
            preparedStatement.setInt(3, id);

            int result = preparedStatement.executeUpdate();
            return result==1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private String inputFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Route> getAll(Connection connection) {
        String sql = "SELECT * FROM transport_service.routes_repo";
        Route route = null;
        List <Route> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String startWay = resultSet.getString(2);
                String endPoint = resultSet.getString(3);

                route = new Route(id, startWay, endPoint);
                list.add(route);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
