package org.example.models.transports;


import org.example.models.drivers.DriverQualificationEnum;

public class Tram extends Transport{
    private Integer numbersOfRailcar;

    public Tram(Integer id, String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum, Integer numbersOfRailcar) {
        super(id, brandOfTransport, numbersOfPassengers, driverQualificationEnum);
        this.numbersOfRailcar = numbersOfRailcar;
    }

    public Tram(String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum, Integer numbersOfRailcar) {
        super(brandOfTransport, numbersOfPassengers, driverQualificationEnum);
        this.numbersOfRailcar = numbersOfRailcar;
    }

    public Integer getNumbersOfRailcar() {
        return numbersOfRailcar;
    }

    public void setNumbersOfRailcar(Integer numbersOfRailcar) {
        this.numbersOfRailcar = numbersOfRailcar;
    }

    @Override
    public String toString() {
        return super.toString() + ", railcar`s numbers: " + numbersOfRailcar;
    }
}
