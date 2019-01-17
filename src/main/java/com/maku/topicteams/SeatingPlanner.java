package com.maku.topicteams;

import java.util.List;
import java.util.Set;

public class SeatingPlanner {

    private static final int MAX_PEOPLE_PER_TABLE = 7;
    private static final int NO_OF_TABLES = 10;
    private static final int NO_OF_SESSIONS = 3;
    private static final int MAX_PEOPLE_OF_SAME_PROJECT_AT_A_TABLE = 2;
    private static final String FILE_NAME = "Camp2018.csv";

    public static void main(String[] args) {
        FileReader reader = new FileReader();
        reader.read(FILE_NAME);
        Set<Person> people = reader.getPeople();

        Allocator allocator = new Allocator(NO_OF_SESSIONS, NO_OF_TABLES, MAX_PEOPLE_PER_TABLE, MAX_PEOPLE_OF_SAME_PROJECT_AT_A_TABLE);
        List allocations = allocator.allocate(people);

        Printer printer = new Printer(allocations);
        printer.printAllocations();
        printer.printAllocationsProjectCount();
        printer.printPeoplesTables(people);
    }

}
