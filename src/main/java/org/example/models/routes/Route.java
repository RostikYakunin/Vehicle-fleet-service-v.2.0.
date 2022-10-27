package org.example.models.routes;

import org.example.models.drivers.Driver;
import org.example.models.transports.Transport;

public class Route {
    private Integer id;
    private String startOfWay;
    private String endOfWay;

    private Transport transport;
    private Driver driver;

    public Route(String startOfWay, String endOfWay) {
        this.startOfWay = startOfWay;
        this.endOfWay = endOfWay;
    }

    public Route(int id, String startOfWay, String endOfWay) {
        this.id = id;
        this.startOfWay = startOfWay;
        this.endOfWay = endOfWay;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartOfWay() {
        return startOfWay;
    }

    public void setStartOfWay(String startOfWay) {
        this.startOfWay = startOfWay;
    }

    public String getEndOfWay() {
        return endOfWay;
    }

    public void setEndOfWay(String endOfWay) {
        this.endOfWay = endOfWay;
    }

    public void setEmptyRouteByTransport(boolean emptyRouteByTransport) {
    }

    @Override
    public String toString() {
        return "\nRoute ID  " + id + ", start: "+startOfWay+ ", end: " + endOfWay;
    }
}
