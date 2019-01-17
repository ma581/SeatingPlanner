package com.philhudson.topicteams;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileReaderTest {

    String fileName = "sample.csv";

    @Test
    void shouldReadFileFromResources() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);

        String data = lines.collect(Collectors.joining("\n"));
        lines.close();

        assertThat(data, containsString("Newton"));
    }

    @Test
    void shouldRetrieveNames() {
        FileReader reader = new FileReader();

        reader.read(fileName);

        List<Record> namesAsList = new ArrayList<>(reader.getNames());
        assertEquals(4, namesAsList.size());
        assertEquals("Isaac", namesAsList.get(0).getFirstName());
        assertEquals("\uFEFFNewton", namesAsList.get(0).getSurname());
    }

    @Test
    void shouldRetrieveProjects() {
        FileReader reader = new FileReader();

        reader.read(fileName);

        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("Calculus");
        expectedSet.add("CodeBreaking");
        expectedSet.add("SpaceX");
        expectedSet.add("Amazon");

        assertEquals(expectedSet, reader.getNames().stream()
                .map(Record::getProject)
                .collect(Collectors.toSet()));
    }
}
