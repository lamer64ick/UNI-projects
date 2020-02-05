package ru.springtutorials.multimodule.config.company;

import org.springframework.stereotype.Component;
import ru.springtutorials.multimodule.config.company.Address;

@Component
public class Company {
    private Address address;

    public Company(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
// getter, setter and other properties