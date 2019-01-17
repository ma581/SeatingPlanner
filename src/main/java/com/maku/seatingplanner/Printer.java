package com.maku.seatingplanner;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Printer {

    private final int numberOfSessions;
    private final int numberOfTables;
    private List<List<List<Person>>> allocations;

    Printer(List<List<List<Person>>> allocations) {
        this.allocations = allocations;
        this.numberOfSessions = allocations.size();
        this.numberOfTables = allocations.get(0).size();
    }

    void printAllocations() {
        printTitle("ALLOCATED SEATING PER SESSION");
        for (int i = 0; i < numberOfSessions; i++) {
            for (int j = 0; j < numberOfTables; j++) {
                System.out.println("session = " + i + " , table = " + j + " " + getNames(getPeopleAtTableForSession(j, i)));
            }
            System.out.println();
        }
    }

    private void printTitle(String title) {
        System.out.println("****************************************************************************");
        System.out.println("*********************** " + title + " ********************** ");
        System.out.println("****************************************************************************");
        System.out.println();
    }

    private List<String> getNames(List<Person> people) {
        return people.stream()
                .map(r -> r.getFirstName() + " " + r.getSurname())
                .collect(Collectors.toList());
    }

    private List<Person> getPeopleAtTableForSession(int tableNumber, int sessionNumber) {
        return allocations.get(sessionNumber).get(tableNumber);
    }

    void printAllocationsProjectCount() {
        printTitle("ALLOCATED PEOPLE PROJECT COUNT");
        for (int i = 0; i < numberOfSessions; i++) {
            for (int j = 0; j < numberOfTables; j++) {
                final List<Person> peopleAtTableForSession = getPeopleAtTableForSession(j, i);
                Map<String, Integer> numberOfPeopleOfProject = new HashMap<>();

                for (Person person : peopleAtTableForSession) {
                    numberOfPeopleOfProject.merge(person.project, 1, (a, b) -> a + b);
                }
                System.out.println("session = " + i + " , table = " + j + " people: " + peopleAtTableForSession.size() + " " + numberOfPeopleOfProject);
            }
        }
        System.out.println();
    }

    void printPeoplesTables(Collection names) {
        printTitle("ALLOCATED TABLES FOR EACH PERSON");
        names.forEach(System.out::println);
    }
}
