package org.example.commands;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;

import org.example.TagGenerator;
import org.example.repository.*;

public class AddCommand extends RepoCommand {

    public AddCommand(String command) {
        super(command);
        decode();
    }

    @Override
    public void execute(ImageRepository repository)
    {
        try
        {
            var item = new ImageItem(name, tags, LocalDate.now(), location);
            if(repository.contains(item)) {
                throw new ItemConflict("Cannot add image that already exists");
            }
            repository.addImage(item);
        }
        catch (Exception e)
        {
            System.err.println("Error executing add command: " + e.getMessage());
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
                throw new IncorrectFormat("Add command takes two arguments " + Arrays.toString(args));
            }

            name = args[0];

            tags = TagGenerator.getRandomTags(3);

            location = Paths.get(args[1]);
        }
        catch (Exception e)
        {
            System.err.println("Error decoding add command: " + e.getMessage());
        }

    }
}
