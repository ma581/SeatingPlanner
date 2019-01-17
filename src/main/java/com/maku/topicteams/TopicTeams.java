package com.maku.topicteams;

import java.util.List;
import java.util.Set;

public class TopicTeams {

    private static final int MAX_PEOPLE_PER_TABLE = 7;
    private static final int NO_OF_TABLES = 10;
    private static final int NO_OF_SESSIONS = 3;
    private static final String FILE_NAME = "Camp2018.csv";

    public static void main(String[] args) {
        FileReader reader = new FileReader();
        reader.read(FILE_NAME);
        Set<Record> names = reader.getNames();

        Allocator allocator = new Allocator(MAX_PEOPLE_PER_TABLE, NO_OF_TABLES, NO_OF_SESSIONS);
        List tableRotation = allocator.allocate(names);

        Printer printer = new Printer(NO_OF_TABLES, NO_OF_SESSIONS, tableRotation);
        printer.printTableRotation();
        printer.printTableRotationProjects();
        printer.printRecords(names);
    }

}
