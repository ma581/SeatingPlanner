package com.philhudson.topicteams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class TopicTeams {

    private static final int MAX_PEOPLE_PER_TABLE = 7;
    private static final int NO_OF_TABLES = 10;
    private static final int NO_OF_SESSIONS = 3;
    private static final String FILE_NAME = "/Users/maku/Documents/topicteams2/src/main/resources/Camp2018.csv";


    public static void main(String[] args) {
        Set<Record> names = new HashSet<>();
        Set<String> projects = new HashSet<>();
        ArrayList<Record> topicTeamLead = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            parseNameProjectAndTopicTeamLead(names, projects, topicTeamLead, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Allocator allocator = new Allocator(MAX_PEOPLE_PER_TABLE, NO_OF_TABLES, NO_OF_SESSIONS);
        ArrayList<ArrayList<ArrayList<Record>>> tableRotation = (ArrayList<ArrayList<ArrayList<Record>>>) allocator.allocate(names);
        Printer printer = new Printer(NO_OF_TABLES, NO_OF_SESSIONS, tableRotation);
        printer.printTableRotation();
        printer.printTableRotationProjects();

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
}
