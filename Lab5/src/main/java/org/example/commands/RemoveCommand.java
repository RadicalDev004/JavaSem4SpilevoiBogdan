package org.example.commands;

import org.example.repository.ImageRepository;

public class RemoveCommand extends RepoCommand {
    public RemoveCommand(String command) {
        super(command);
        decode();
    }

    @Override
    public void execute(ImageRepository repository) {
        try
        {
            if(!repository.contains(name)) {
                throw new ItemConflict("Cannot remove image that doesn't exists");
            }
            repository.removeImage(name);
        }
        catch (Exception e)
        {
            System.err.println("Error executing remove command: " + e.getMessage());
        }

    }

    @Override
    public void decode() {
        try
        {
            String rawCommand = command;
            if(rawCommand.split(" ").length != 1)
            {
                throw new IncorrectFormat("Remove command takes one argument");
            }
            name = rawCommand;
        }
        catch (Exception e)
        {
            System.err.println("Error decoding remove command: " + e.getMessage());
        }

    }
}
