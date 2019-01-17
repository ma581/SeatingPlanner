package com.maku.seatingplanner;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileReaderTest {

    private String fileName = "sample.csv";

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

        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("Isaac");
        expectedSet.add("Elon");
        expectedSet.add("Jeff");
        expectedSet.add("Alan");

        assertEquals(expectedSet, reader.getPeople().stream()
                .map(Person::getFirstName)
                .collect(Collectors.toSet()));
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

        assertEquals(expectedSet, reader.getPeople().stream()
                .map(Person::getProject)
                .collect(Collectors.toSet()));
    }
}
