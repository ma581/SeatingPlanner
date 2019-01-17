package com.philhudson.topicteams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TopicTeams {

    private static final int MAX_PEOPLE_PER_TABLE = 7;
    private static final int NO_OF_TABLES = 10;
    private static final int NO_OF_SESSIONS = 3;
    private static final String FILE_NAME = "/Users/maku/Documents/topicteams2/src/main/java/com/philhudson/topicteams/Camp2018.csv";

    private static ArrayList<ArrayList<ArrayList<Record>>> tableRotation = new ArrayList<>();

    private static ArrayList<Record> getPeopleAtTableForSession(int tableNumber, int sessionNumber) {
        return tableRotation.get(sessionNumber).get(tableNumber);
    }

    private static void setPersonAtTableForSession(int tableNumber, int sessionNumber, Record person) {
        tableRotation.get(sessionNumber).get(tableNumber).add(person);
    }


    private static void initializeTableRotation() {
        for (int i = 0; i < NO_OF_SESSIONS; i++) {
            tableRotation.add(new ArrayList<>());
            for (int j = 0; j < NO_OF_TABLES; j++) {
                tableRotation.get(i).add(new ArrayList<>());
            }
        }
    }


    private static void printTableRotation() {
        for (int i = 0; i < NO_OF_SESSIONS; i++) {
            for (int j = 0; j < NO_OF_TABLES; j++) {
                System.out.println("session = " + i + " , table = " + j + getPeopleAtTableForSession(j, i));
            }
        }

    }

    public static void main(String[] args) {

        initializeTableRotation();

        Set<Record> names = new HashSet<>();
        Set<String> projects = new HashSet<>();
        ArrayList<Record> topicTeamLead = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            parseNameProjectAndTopicTeamLead(names, projects, topicTeamLead, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        allocate(names);
        System.out.print("________________________________________________\n");
        printTableRotation();
        System.out.print("________________________________________________\n");
        printTableRotationProjects();


        names.forEach(System.out::println);
    }

    private static void parseNameProjectAndTopicTeamLead(Set<Record> names, Set<String> projects, ArrayList<Record> topicTeamLead, Stream<String> stream) {
        stream.forEach(line -> {
            String[] arr = line.split(",");
            Record r = new Record(arr[0], arr[1], arr[2]);

            if (!arr[2].startsWith("TTL-")) {
                names.add(r);
                projects.add(arr[2]);
            } else {
                topicTeamLead.add(r);
            }
        });
    }

    private static void allocate(Set<Record> names) {
        System.out.println("Allocating...");
        for (int k = 0; k < names.size(); k++) {
            ArrayList<Record> remainingNames = new ArrayList<>(names);

            for (int session = 0; session < NO_OF_SESSIONS; session++) {
                Record person = remainingNames.get(k);

                int proposedTableNumber;
                int counter = 0;
                do {
                    proposedTableNumber = getTableTheyHaveNotSatAtBefore(person);
                    counter++;
                    if (counter > 1000) {
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
    }

    private static boolean hasTooManyAtTable(ArrayList<Record> peopleAtTable, int tableNumber) {
        switch (tableNumber) {
            case 0:
            case 1:
            case 2:
                return peopleAtTable.size() > MAX_PEOPLE_PER_TABLE - 1;
            default:
                return peopleAtTable.size() > MAX_PEOPLE_PER_TABLE - 1;
        }
    }

    private static void printTableRotationProjects() {
        for (int i = 0; i < NO_OF_SESSIONS; i++) {
            for (int j = 0; j < NO_OF_TABLES; j++) {
                final ArrayList<Record> peopleAtTableForSession = getPeopleAtTableForSession(j, i);
                Map<String, Integer> numberOfPeopleOfProject = new HashMap<>();

                for (Record person : peopleAtTableForSession) {
                    numberOfPeopleOfProject.merge(person.project, 1, (a, b) -> a + b);
                }
                System.out.println("session = " + i + " , table = " + j + " peeps: " + peopleAtTableForSession.size() + " " + numberOfPeopleOfProject);
            }
        }
    }

    private static boolean hasTooManyOfSameProject(ArrayList<Record> peopleAtTableForSession, Record person) {
        final List<Record> peopleWithSameProject = peopleAtTableForSession.stream().filter(p -> p.project.equals(person.project)).collect(Collectors.toList());
        return peopleWithSameProject.size() > 2;
    }

    private static int getTableTheyHaveNotSatAtBefore(Record person) {
        int proposedTableNumber;
        do {
            proposedTableNumber = ThreadLocalRandom.current().nextInt(0, NO_OF_TABLES);
        }
        while (person.previousTables.contains(proposedTableNumber));
        return proposedTableNumber;
    }
}
