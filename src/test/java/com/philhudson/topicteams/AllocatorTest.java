package com.philhudson.topicteams;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllocatorTest {

    @Test
    void shouldHaveOneSession(){
        Allocator allocator = new Allocator(1, 1, 1);
        Set names = new HashSet<>();
        List allocate = allocator.allocate(names);
        assertEquals(1, allocate.size());
    }

    @Test
    void shouldHaveZeroSessions(){
        Allocator allocator = new Allocator(1, 1, 0);
        Set names = new HashSet<>();
        List allocate = allocator.allocate(names);
        assertEquals(0, allocate.size());
    }
}
