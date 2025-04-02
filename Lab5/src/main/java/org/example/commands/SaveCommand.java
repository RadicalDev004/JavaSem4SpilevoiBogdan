package org.example.commands;

import org.example.repository.ImageItem;
import org.example.repository.ImageRepository;
import org.example.repository.RepositoryManager;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

public class SaveCommand extends RepoCommand {
    private RepositoryManager repositoryManager;
    public SaveCommand(String command, RepositoryManager repositoryManager) {
        super(command);
        this.repositoryManager = repositoryManager;
        decode();
    }

    @Override
    public void execute(ImageRepository repository)
    {
        try
        {
            repositoryManager.save(repository.getSaveName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void decode() {
        try
        {
            String rawCommand = command;
            if(rawCommand != null) {
                throw new IncorrectFormat("Save command takes no arguments");
            }
        }
        catch (Exception e)
        {
            System.err.println("Error decoding save command: " + e.getMessage());
        }
    }
}
