package com.philhudson.topicteams;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class FileReader {

    private Set<Record> names;
    private Set<String> projects;
    private List<Record> topicTeamLead;

    public FileReader() {
        names = new HashSet<>();
        projects = new HashSet<>();
        topicTeamLead = new ArrayList<>();
    }

    public Set<Record> getNames() {
        return names;
    }

    void read(String fileName){
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader()
                .getResource(fileName).toURI()))) {
            parseNameProjectAndTopicTeamLead(names, projects, topicTeamLead, stream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void parseNameProjectAndTopicTeamLead(Set<Record> names, Set<String> projects, List<Record> topicTeamLead, Stream<String> stream) {
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
