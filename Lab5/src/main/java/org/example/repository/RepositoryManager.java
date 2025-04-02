package org.example.repository;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositoryManager {
    private List<ImageRepository> allRepos = new ArrayList<>();
    public ImageRepository current;
    private String savePath;

    public RepositoryManager(String savePath) {
        this.savePath = savePath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void printAllRepos() {
        for (ImageRepository repo : allRepos) {
            System.out.print(repo.getSaveName());
        }
    }


    public void load(String repoName)
    {
        if(!doesRepoExist(savePath, repoName))
        {
            System.out.println("Repository " + repoName + " at " + savePath + "/" + repoName + ".json not found adding new repository");
            current = new ImageRepository(repoName);
            allRepos.add(current);
        }
        else
        {
            current = new ImageRepository(repoName);
            String fullPath = savePath + "/" + repoName + ".json";
            ObjectMapper mapper = new ObjectMapper();

            try {
                JsonNode root = mapper.readTree(new File(fullPath));

                for (JsonNode imageNode : root) {
                    String name = imageNode.get("name").asText();
                    LocalDate date = LocalDate.parse(imageNode.get("date").asText());
                    String path = imageNode.get("path").asText();

                    List<String> tags = new ArrayList<>();
                    for (JsonNode tagNode : imageNode.get("tags")) {
                        tags.add(tagNode.asText());
                    }

                    current.addImage(new ImageItem(name, tags, date, Path.of(path)));
                }

                System.out.println("Repository loaded successfully from: " + fullPath);
                allRepos.add(current);

            } catch (IOException e) {
                System.err.println("Error loading repository: " + e.getMessage());
            }
        }




    }
    public void save(String repoName) throws IOException {
        try
        {
            if(allRepos.stream().noneMatch(repo -> repo.getSaveName().equals(repoName)))
            {
                throw new MissingRepoException("No repo with name " + repoName + " exists");
            }
            current = allRepos.stream().filter(repo -> repo.getSaveName().equals(repoName)).findFirst().get();

            JsonFactory factory = new JsonFactory();
            String fullPath = savePath + "/" + repoName + ".json";
            File file = new File(fullPath);
            if (!file.exists()) {
                boolean created = file.createNewFile();
                System.out.println("File created: " + created);
            }

            try (FileWriter fileWriter = new FileWriter(fullPath);
                 JsonGenerator generator = new JsonFactory().createGenerator(fileWriter)) {
                generator.writeStartArray();

                for (ImageItem img : current.getImages()) {
                    generator.writeStartObject();
                    generator.writeStringField("name", img.name());
                    generator.writeStringField("date", img.date().toString());

                    generator.writeFieldName("tags");
                    generator.writeStartArray();
                    for (String tag : img.tags()) {
                        generator.writeString(tag);
                    }
                    generator.writeEndArray();

                    generator.writeStringField("path", img.location().toString());
                    generator.writeEndObject();
                }

                generator.writeEndArray();
                generator.flush();

                System.out.println("Repository saved successfully at: " + fullPath);
            } catch (IOException e) {
                System.err.println("Error saving images: " + e.getMessage());
            }
        }
        catch (Exception e)
        {
            System.err.println("Error saving images: " + e.getMessage());
        }

    }

    public boolean doesRepoExist(String savePath, String repoName) {
        File file = new File(savePath + "/" + repoName + ".json");
        return file.exists();
    }
}
