package com.philhudson.topicteams;

import java.util.ArrayList;
import java.util.List;

public class Record {

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    private String surname;
    private String firstName;
    String project;
    List<Integer> previousTables;

    Record(String surname, String firstName, String project) {
        this.surname = surname;
        this.firstName = firstName;
        this.project = project;
        previousTables = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Record{" +
            "surname='" + surname + '\'' +
            ", firstname='" + firstName + '\'' +
            ", project='" + project + '\'' +
            ", previousTables=" + previousTables +
            '}';
    }

    public String getProject() {
        return project;
    }
}
