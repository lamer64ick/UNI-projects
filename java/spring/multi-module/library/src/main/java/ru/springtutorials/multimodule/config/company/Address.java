package ru.springtutorials.multimodule.application.company;

public class Address {
    private String street;
    private int number;

    public Address(String street, int number) {
        this.street = street;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }
}
