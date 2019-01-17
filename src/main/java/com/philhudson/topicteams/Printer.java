package com.philhudson.topicteams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Printer {

    private final int numberOfSessions;
    private final int numberOfTables;
    private ArrayList<ArrayList<ArrayList<Record>>> tableRotation;

    Printer(int numberOfTables, int numberOfSessions, List tableRotation) {
        this.numberOfTables = numberOfTables;
        this.numberOfSessions = numberOfSessions;
        this.tableRotation = (ArrayList<ArrayList<ArrayList<Record>>>) tableRotation;
    }

    void printTableRotation() {
        for (int i = 0; i < numberOfSessions; i++) {
            for (int j = 0; j < numberOfTables; j++) {
                System.out.println("session = " + i + " , table = " + j + getPeopleAtTableForSession(j, i));
            }
        }
        System.out.print("________________________________________________\n");
    }

    private ArrayList<Record> getPeopleAtTableForSession(int tableNumber, int sessionNumber) {
        return tableRotation.get(sessionNumber).get(tableNumber);
    }

    void printTableRotationProjects() {
        for (int i = 0; i < numberOfSessions; i++) {
            for (int j = 0; j < numberOfTables; j++) {
                final ArrayList<Record> peopleAtTableForSession = getPeopleAtTableForSession(j, i);
                Map<String, Integer> numberOfPeopleOfProject = new HashMap<>();

                for (Record person : peopleAtTableForSession) {
                    numberOfPeopleOfProject.merge(person.project, 1, (a, b) -> a + b);
                }
                System.out.println("session = " + i + " , table = " + j + " people: " + peopleAtTableForSession.size() + " " + numberOfPeopleOfProject);
            }
        }
        System.out.print("________________________________________________\n");
    }

}
