package com.maku.topicteams;

import java.util.ArrayList;
import java.util.List;

public class Person {

    String project;
    List<Integer> previousTables;
    private String surname;
    private String firstName;

    Person(String surname, String firstName, String project) {
        this.surname = surname;
        this.firstName = firstName;
        this.project = project;
        previousTables = new ArrayList<>();
    }

    String getSurname() {
        return surname;
    }

    String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return "{" + surname + ' ' + firstName +
                ", allocatedTables=" + previousTables +
                '}';
    }

    String getProject() {
        return project;
    }
}
