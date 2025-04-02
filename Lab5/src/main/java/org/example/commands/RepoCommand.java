package org.example.commands;

import org.example.repository.ImageRepository;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class RepoCommand {
    protected String command;

    protected String name;
    protected List<String> tags = new ArrayList<>();
    protected Path location;

    public RepoCommand(String command) {
        this.command = command;
    }

    public abstract void execute(ImageRepository repository);
    public abstract void decode();
}
