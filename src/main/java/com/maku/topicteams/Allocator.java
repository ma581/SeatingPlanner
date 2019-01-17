package com.maku.topicteams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

class Allocator {

    private final int maxAttempts = 1000;
    private final int maxPeopleOfSameProjectAtATable;
    private final int numberOfSessions;
    private final int numberOfTables;
    private final int maxPeoplePerTable;
    private List<List<List<Person>>> tableRotation = new ArrayList<>();

    Allocator(int numberOfSessions, int numberOfTables, int maxPeoplePerTable, int maxPeopleOfSameProjectAtATable) {
        this.maxPeoplePerTable = maxPeoplePerTable;
        this.numberOfTables = numberOfTables;
        this.numberOfSessions = numberOfSessions;
        this.maxPeopleOfSameProjectAtATable = maxPeopleOfSameProjectAtATable;
    }


    private int getTableTheyHaveNotSatAtBefore(Person person) {
        int proposedTableNumber;
        do {
            proposedTableNumber = ThreadLocalRandom.current().nextInt(0, numberOfTables);
        }
        while (person.previousTables.contains(proposedTableNumber));
        return proposedTableNumber;
    }

    private void initializeTableRotation() {
        for (int sessionNumber = 0; sessionNumber < numberOfSessions; sessionNumber++) {
            tableRotation.add(new ArrayList<>());
            for (int tableNumber = 0; tableNumber < numberOfTables; tableNumber++) {
                tableRotation.get(sessionNumber).add(new ArrayList<>());
            }
        }
    }

    private boolean hasTooManyAtTable(List<Person> peopleAtTable, int tableNumber) {
        return peopleAtTable.size() > maxPeoplePerTable - 1;
    }

    List<List<List<Person>>> allocate(Set<Person> people) {
        initializeTableRotation();

        System.out.println("Allocating...");
        for (int p = 0; p < people.size(); p++) {

            for (int session = 0; session < numberOfSessions; session++) {
                Person currentPerson = new ArrayList<>(people).get(p);

                int proposedTableNumber;
                int numberOfAttempts = 0;
                do {
                    proposedTableNumber = getTableTheyHaveNotSatAtBefore(currentPerson);
                    numberOfAttempts++;
                    if (numberOfAttempts > maxAttempts) {
                        throw new RuntimeException("Too many attempts");
                    }
                }
                while (
                        hasTooManyAtTable(getPeopleAtTableForSession(proposedTableNumber, session), proposedTableNumber) ||
                                hasTooManyOfSameProject(getPeopleAtTableForSession(proposedTableNumber, session), currentPerson));

                currentPerson.previousTables.add(proposedTableNumber);
                setPersonAtTableForSession(proposedTableNumber, session, currentPerson);
            }
        }
        return tableRotation;
    }

    private List<Person> getPeopleAtTableForSession(int tableNumber, int sessionNumber) {
        return tableRotation.get(sessionNumber).get(tableNumber);
    }

    private boolean hasTooManyOfSameProject(List<Person> peopleAtTableForSession, Person person) {
        final List<Person> peopleWithSameProject = peopleAtTableForSession.stream().filter(p -> p.project.equals(person.project)).collect(Collectors.toList());
        return peopleWithSameProject.size() > maxPeopleOfSameProjectAtATable;
    }

    private void setPersonAtTableForSession(int tableNumber, int sessionNumber, Person person) {
        tableRotation.get(sessionNumber).get(tableNumber).add(person);
    }
}
