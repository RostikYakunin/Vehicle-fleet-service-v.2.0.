package org.example.models.transports;

import org.example.models.drivers.DriverQualificationEnum;

public class Bus extends Transport{
    private String type;
    private Integer numbersOfDoors;

    public Bus(Integer id, String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum, String type, Integer numbersOfDoors) {
        super(id, brandOfTransport, numbersOfPassengers, driverQualificationEnum);
        this.type = type;
        this.numbersOfDoors = numbersOfDoors;
    }

    public Bus (String brandOfTransport, Integer numbersOfPassengers, DriverQualificationEnum driverQualificationEnum, String type, Integer numbersOfDoors) {
        super(brandOfTransport, numbersOfPassengers, driverQualificationEnum);
        this.type = type;
        this.numbersOfDoors = numbersOfDoors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumbersOfDoors() {
        return numbersOfDoors;
    }

    public void setNumbersOfDoors(Integer numbersOfDoors) {
        this.numbersOfDoors = numbersOfDoors;
    }

    @Override
    public String toString() {
        return super.toString() + ", transport`s type: " + type+ ", door`s numbers: " + numbersOfDoors;
    }
}
