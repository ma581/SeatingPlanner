package com.philhudson.topicteams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

class Allocator {

    private final int maxAllocationAttempts = 1000;
    private final int numberOfSessions;
    private final int numberOfTables;
    private final int maxPeoplePerTable;
    ArrayList<ArrayList<ArrayList<Record>>> tableRotation = new ArrayList<>();

    Allocator(int maxPeoplePerTable, int numberOfTables, int numberOfSessions) {
        this.maxPeoplePerTable = maxPeoplePerTable;
        this.numberOfTables = numberOfTables;
        this.numberOfSessions = numberOfSessions;
    }


    private int getTableTheyHaveNotSatAtBefore(Record person) {
        int proposedTableNumber;
        do {
            proposedTableNumber = ThreadLocalRandom.current().nextInt(0, numberOfTables);
        }
        while (person.previousTables.contains(proposedTableNumber));
        return proposedTableNumber;
    }

    private void initializeTableRotation() {
        for (int i = 0; i < numberOfSessions; i++) {
            tableRotation.add(new ArrayList<>());
            for (int j = 0; j < numberOfTables; j++) {
                tableRotation.get(i).add(new ArrayList<>());
            }
        }
    }

    private boolean hasTooManyAtTable(ArrayList<Record> peopleAtTable, int tableNumber) {
        switch (tableNumber) {
            case 0:
            case 1:
            case 2:
                return peopleAtTable.size() > maxPeoplePerTable - 1;
            default:
                return peopleAtTable.size() > maxPeoplePerTable - 1;
        }
    }

    List allocate(Set<Record> names) {
        initializeTableRotation();

        System.out.println("Allocating...");
        for (int k = 0; k < names.size(); k++) {
            ArrayList<Record> remainingNames = new ArrayList<>(names);

            for (int session = 0; session < numberOfSessions; session++) {
                Record person = remainingNames.get(k);

                int proposedTableNumber;
                int counter = 0;
                do {
                    proposedTableNumber = getTableTheyHaveNotSatAtBefore(person);
                    counter++;
                    if (counter > maxAllocationAttempts) {
                        throw new RuntimeException("Too many attempts");
                    }
                }
                while (
                        hasTooManyAtTable(getPeopleAtTableForSession(proposedTableNumber, session), proposedTableNumber) ||
                                hasTooManyOfSameProject(getPeopleAtTableForSession(proposedTableNumber, session), person));

                person.previousTables.add(proposedTableNumber);
                setPersonAtTableForSession(proposedTableNumber, session, person);
            }
        }
        return tableRotation;
    }

    private ArrayList<Record> getPeopleAtTableForSession(int tableNumber, int sessionNumber) {
        return tableRotation.get(sessionNumber).get(tableNumber);
    }

    private boolean hasTooManyOfSameProject(ArrayList<Record> peopleAtTableForSession, Record person) {
        final List<Record> peopleWithSameProject = peopleAtTableForSession.stream().filter(p -> p.project.equals(person.project)).collect(Collectors.toList());
        return peopleWithSameProject.size() > 2;
    }

    private void setPersonAtTableForSession(int tableNumber, int sessionNumber, Record person) {
        tableRotation.get(sessionNumber).get(tableNumber).add(person);
    }
}
