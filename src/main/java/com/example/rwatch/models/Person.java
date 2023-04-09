package com.example.rwatch.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Person {

    private final StringProperty firstName;
    private final StringProperty sureName;
    private final StringProperty lastName;
    private final ObjectProperty<LocalDate> birthDay;
    private final StringProperty phoneNumber;
    private final StringProperty serialPass;
    private final StringProperty numPass;
    private final StringProperty city;
    private final ObjectProperty<LocalDate> regFrom;
    private final ObjectProperty<LocalDate> regTo;
    private final ObjectProperty<LocalDate> patFrom;
    private final ObjectProperty<LocalDate> patTo;

    public Person() {this(null, null, null,
            null, null, null, null,
            null, null, null, null, null);}



    public Person(String firstName, String sureName, String lastName,
                  LocalDate birthDay, String phoneNumber, String serialPass,
                  String numPass, LocalDate regFrom, LocalDate regTo,
                  LocalDate patFrom, LocalDate patTo, String city)
    {
        this.firstName = new SimpleStringProperty(firstName);
        this.sureName = new SimpleStringProperty(sureName);
        this.lastName = new SimpleStringProperty(lastName);

        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.numPass = new SimpleStringProperty(numPass);
        this.city = new SimpleStringProperty(city);
        this.serialPass = new SimpleStringProperty(serialPass);
        this.birthDay = new SimpleObjectProperty<LocalDate>(birthDay);
        this.regFrom = new SimpleObjectProperty<LocalDate>(regFrom);
        this.regTo = new SimpleObjectProperty<LocalDate>(regTo);
        this. patFrom = new SimpleObjectProperty<LocalDate>(patFrom);
        this.patTo = new SimpleObjectProperty<LocalDate>(patTo);
    }

    //Name
    public String getFirstName() {return firstName.get();}
    public void setFirstName(String firstName) {this.firstName.set(firstName);}
    public StringProperty firstNameProperty() {return firstName;}

    public String getSureName() {return sureName.get();}
    public void setSureName(String sureName) {this.sureName.set(sureName);}
    public StringProperty sureNameProperty() {return sureName;}

    public String getLastName() {return lastName.get();}
    public void setLastName(String lastName) {this.lastName.set(lastName);}
    public StringProperty lastNameProperty() {return lastName;}

    //Phone number
    public String getPhoneNumber() {return phoneNumber.get();}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber.set(phoneNumber);}
    public StringProperty phoneNumberProperty() {return phoneNumber;}

    //PasData
    public String getSerialPass() {return serialPass.get();}
    public void setSerialPass(String serialPass) {this.serialPass.set(serialPass);}
    public StringProperty serialProperty() {return serialPass;}

    public String getNumPass() {return numPass.get();}
    public void setNumPass(String numPas) {this.numPass.set(numPas);}
    public StringProperty numPassProperty() {return numPass;}

    public String getCity() {return city.get();}
    public void setCity(String city) {this.city.set(city);}
    public StringProperty cityProperty() {return city;}

    //Dates
    public LocalDate getBirthDay() {return birthDay.get();}
    public void setBirthDay(LocalDate birthDay) {this.birthDay.set(birthDay);}
    public ObjectProperty<LocalDate> birthDayProperty() {return birthDay;}

    public LocalDate getRegFrom() {return regFrom.get();}
    public void setRegFrom(LocalDate regFrom) {this.regFrom.set(regFrom);}
    public ObjectProperty<LocalDate> regFromProperty() {return regFrom;}

    public LocalDate getRegTo() {return regTo.get();}
    public void setRegTo(LocalDate regTo) {this.regTo.set(regTo);}
    public ObjectProperty<LocalDate> regToProperty() {return regTo;}

    public LocalDate getPatFrom() {return patFrom.get();}
    public void setPatFrom(LocalDate patFrom) {this.patFrom.set(patFrom);}
    public ObjectProperty<LocalDate> patFromProperty() {return patFrom;}

    public LocalDate getPatTo() {return patTo.get();}
    public void setPatTo(LocalDate patTo) {this.patTo.set(patTo);}
    public ObjectProperty<LocalDate> patToProperty() {return patTo;}

    @Override
    public String toString() {
        return "fisrtname" + firstName + "surename" + sureName +  "lastname" + lastName +
                "birthday" + birthDay + "phonenumber" + phoneNumber + "serialpas" + serialPass +
                "numpas" + numPass + "regfrom" + regFrom + "regto" + regTo + "patfrom" + patFrom +
                "patto" + patTo + "city" + city;
    }
}
