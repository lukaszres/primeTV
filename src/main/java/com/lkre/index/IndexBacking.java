package com.lkre.index;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class IndexBacking {

    private String firstName = "Johny";
    private String lastName = "Mnemonic";

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String showGreeting() {
        return "Hello " + firstName + " " + lastName + "!";
    }
}