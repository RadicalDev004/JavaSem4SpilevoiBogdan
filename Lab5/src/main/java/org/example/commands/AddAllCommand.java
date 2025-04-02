package org.example.commands;

import org.example.repository.ImageItem;
import org.example.repository.ImageRepository;
import org.example.repository.MissingRepoException;
import org.example.repository.RepositoryManager;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddAllCommand extends RepoCommand {
    private RepositoryManager repositoryManager;
    private  String directoryPath;
    public AddAllCommand(String command, RepositoryManager repositoryManager) {
        super(command);
        this.repositoryManager = repositoryManager;
        decode();
    }

    @Override
    public void execute(ImageRepository repository)
    {
        try
        {
            if(repositoryManager.current == null)
            {
                throw new MissingRepoException("No current repository");
            }
            File directory = new File(directoryPath);
            scanDirectory(directory);
        }
        catch (Exception e)
        {
            System.out.println("Cannot generate report, no repository is loaded: " + e.getMessage());
        }

    }

    @Override
    public void decode() {
        try
        {
            String rawCommand = command;
            String[] args = rawCommand.split(" ");
            if(args.length != 1) {
                throw new IncorrectFormat("AdAll command takes one argument " + Arrays.toString(args));
            }

            directoryPath = args[0];
        }
        catch (Exception e)
        {
            System.err.println("Error decoding addAll command: " + e.getMessage());
        }
    }

    private void scanDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file);
            } else if (isImageFile(file)) {
                RepoCommand newAddCommand = new AddCommand(file.getName() + " " + file.getPath());
                newAddCommand.execute(repositoryManager.current);
            }
        }
    }

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".gif");
    }
}
