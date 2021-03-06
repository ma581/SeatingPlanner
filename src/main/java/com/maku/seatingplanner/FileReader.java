package com.maku.seatingplanner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

class FileReader {

    private Set<Person> people = new HashSet<>();

    Set<Person> getPeople() {
        return people;
    }

    void read(String fileName) {
        try (Stream<String> stream = openStream(fileName)) {
            parseNameAndProject(stream);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private Stream<String> openStream(String fileName) throws IOException, URISyntaxException {
        return Files.lines(Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(fileName)).toURI()));
    }

    private void parseNameAndProject(Stream<String> stream) {
        stream.forEach(line -> {
            String[] arr = line.split(",");
            Person person = new Person(arr[0], arr[1], arr[2]);
            people.add(person);
        });
    }
}
