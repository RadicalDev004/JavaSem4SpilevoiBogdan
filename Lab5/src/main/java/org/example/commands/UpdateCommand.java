package org.example.commands;

import org.example.repository.ImageItem;
import org.example.repository.ImageRepository;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

public class UpdateCommand extends RepoCommand {
    public UpdateCommand(String command) {
        super(command);
        decode();
    }
    private String newName;
    @Override
    public void execute(ImageRepository repository)
    {
        try
        {
            if(!repository.contains(name)) {
                throw new ItemConflict("Cannot change image that doesn't exists " + name);
            }
            repository.changeImage(name, newName);
        }
        catch (Exception e)
        {
            System.err.println("Error executing update command: " + e.getMessage());
        }

    }

    @Override
    public void decode() {
        try
        {
            String rawCommand = command;
            String[] args = rawCommand.split(" ");
            if(args.length != 2)
            {
                throw new IncorrectFormat("Update command takes two arguments " + Arrays.toString(args));
            }

            name = args[0];

            newName = args[1];
        }
        catch (Exception e)
        {
            System.err.println("Error decoding update commands: " + e.getMessage());
        }

    }
}
