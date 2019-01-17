package com.maku.topicteams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

class FileReader {

    private Set<Record> names = new HashSet<>();

    Set<Record> getNames() {
        return names;
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
            Record r = new Record(arr[0], arr[1], arr[2]);
            names.add(r);
        });
    }
}
