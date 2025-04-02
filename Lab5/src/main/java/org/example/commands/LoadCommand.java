package org.example.commands;

import org.example.repository.ImageRepository;
import org.example.repository.RepositoryManager;

import java.util.Arrays;

public class LoadCommand extends RepoCommand {
    private RepositoryManager repositoryManager;
    public LoadCommand(String command, RepositoryManager repositoryManager) {
        super(command);
        this.repositoryManager = repositoryManager;
        decode();
    }

    @Override
    public void execute(ImageRepository repository)
    {
        repositoryManager.load(name);
    }

    @Override
    public void decode() {
        try
        {
            String rawCommand = command;
            String[] args = rawCommand.split(" ");
            if(args.length != 1) {
                throw new IncorrectFormat("Save command takes one argument " + Arrays.toString(args));
            }

            name = args[0];
        }
        catch (Exception e)
        {
            System.err.println("Error decoding load command: " + e.getMessage());
        }
    }
}
