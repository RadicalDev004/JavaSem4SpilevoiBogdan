package org.example.commands;

import freemarker.template.Configuration;
import org.example.repository.ImageRepository;
import org.example.repository.MissingRepoException;
import org.example.repository.RepositoryManager;

import java.io.IOException;

public class ReportCommand extends RepoCommand {
    private RepositoryManager repositoryManager;
    public ReportCommand(String command, RepositoryManager repositoryManager) {
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
            ReportGenerator.generateHtmlReport(repository, repositoryManager.getSavePath() + "/reports/" + repositoryManager.current.getSaveName() + ".html");
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
            if(rawCommand != null) {
                throw new IncorrectFormat("Report command takes no arguments");
            }
        }
        catch (Exception e)
        {
            System.err.println("Error decoding report command: " + e.getMessage());
        }
    }
}
