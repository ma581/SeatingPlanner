package com.philhudson.topicteams;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllocatorTest {

    @Test
    void shouldHaveOneSession(){
        Allocator allocator = new Allocator(1, 1, 1);
        List allocate = allocator.allocate(new HashSet<>());
        assertEquals(1, allocate.size());
    }

    @Test
    void shouldHaveZeroSessions(){
        Allocator allocator = new Allocator(1, 1, 0);
        List allocate = allocator.allocate(new HashSet<>());
        assertEquals(0, allocate.size());
    }
}
