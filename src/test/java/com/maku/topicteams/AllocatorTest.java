package com.maku.topicteams;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllocatorTest {

    @Test
    void shouldHaveOneSession() {
        Allocator allocator = new Allocator(1, 0, 0, 5);
        List allocations = allocator.allocate(new HashSet<>());
        assertEquals(1, allocations.size());
    }

    @Test
    void shouldHaveZeroSessions() {
        Allocator allocator = new Allocator(2, 0, 0, 5);
        List allocations = allocator.allocate(new HashSet<>());
        assertEquals(2, allocations.size());
    }

    @Test
    void shouldHaveOneSessionOneTable() {
        Allocator allocator = new Allocator(1, 1, 0, 5);
        List<List<List<Person>>> allocations = allocator.allocate(new HashSet<>());
        assertEquals(1, allocations.get(0).size());
    }

    @Test()
    void shouldNotAllocateMoreThanMaxPerTable() {
        Allocator allocator = new Allocator(1, 2, 0, 5);
        Set<Person> people = new HashSet<>();
        people.add(new Person("Newton", "Isaac", "Calculus"));

        Executable ex = () -> allocator.allocate(people);

        Assertions.assertThrows(RuntimeException.class, ex, "Too many attempts");
    }

    @Test()
    void shouldAllocateOnePersonPerTable() {
        Allocator allocator = new Allocator(1, 2, 1, 5);
        Set<Person> people = new HashSet<>();
        people.add(new Person("Newton", "Isaac", "Calculus"));
        people.add(new Person("Watt", "James", "SteamEngine"));

        List<List<List<Person>>> allocations = allocator.allocate(people);

        assertEquals(1, allocations.get(0).get(0).size());
        assertEquals(1, allocations.get(0).get(1).size());
    }

    @Test()
    void shouldMovePersonForEachSession() {
        Allocator allocator = new Allocator(2, 2, 1, 5);
        Set<Person> people = new HashSet<>();
        people.add(new Person("Newton", "Isaac", "Calculus"));

        allocator.allocate(people);

        assertEquals(2, new ArrayList<>(people).get(0).previousTables.size());
    }
}
