package org.example.models.transports;

import org.example.models.routes.Route;
import org.example.models.drivers.Driver;
import org.example.models.drivers.DriverQualificationEnum;

public abstract class Transport {
    private Integer id;
    private String brandOfTransport;
    private Integer numbersOfPassengers;
    private Route route;
    private DriverQualificationEnum driverQualificationEnum;

    private Driver driver;

    public Transport(String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum) {
        this.brandOfTransport = brandOfTransport;
        this.numbersOfPassengers = numbersOfPassengers;
        this.driverQualificationEnum = driverQualificationEnum;
    }
    public Transport(int id, String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum) {
        this.id = id;
        this.brandOfTransport = brandOfTransport;
        this.numbersOfPassengers = numbersOfPassengers;
        this.driverQualificationEnum = driverQualificationEnum;
    }

    public Transport(Integer id, String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum, Route route) {
        this.id = id;
        this.brandOfTransport = brandOfTransport;
        this.numbersOfPassengers = numbersOfPassengers;
        this.route = route;
        this.driverQualificationEnum = driverQualificationEnum;
    }

    public Transport(Integer id, String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum, Route route, Driver driver) {
        this.id = id;
        this.brandOfTransport = brandOfTransport;
        this.numbersOfPassengers = numbersOfPassengers;
        this.route = route;
        this.driverQualificationEnum = driverQualificationEnum;
        this.driver = driver;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandOfTransport() {
        return brandOfTransport;
    }

    public void setBrandOfTransport(String brandOfTransport) {
        this.brandOfTransport = brandOfTransport;
    }

    public Integer getNumbersOfPassengers() {
        return numbersOfPassengers;
    }

    public void setNumbersOfPassengers(Integer numbersOfPassengers) {
        this.numbersOfPassengers = numbersOfPassengers;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public DriverQualificationEnum getDriverQualificationEnum() {
        return driverQualificationEnum;
    }

    public void setDriverQualificationEnum(DriverQualificationEnum driverQualificationEnum) {
        this.driverQualificationEnum = driverQualificationEnum;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "\nTransport ID " + id + ", model: " + brandOfTransport + ", numbers of passengers: " + numbersOfPassengers+
                ", driver qualification: " +
                (driverQualificationEnum.equals(DriverQualificationEnum.BUS_DRIVER)?" bus driver" : " tram driver");
    }
}
