package org.example;

import org.example.commands.*;
import org.example.persistence.*;
import org.example.repository.MaximalGroups;
import org.example.repository.RepositoryManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        RepositoryManager manager = new RepositoryManager("C:/Users/Bogdan S/OneDrive/Desktop/JavaRepos");
        PersistenceMode mode;
        
        /*ImageItem img1 = new ImageItem("Sunset", LocalDate.of(2025, 3, 26), Path.of("C:/Users/Bogdan S/OneDrive/Desktop/Screenshot_90.png"));
        repository.addImage(img1);

        repository.displayImage(img1);*/

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("Command>:");
            String command = scanner.nextLine();
            if(command.equals("exit"))
                break;
            String type = command.split(" ")[0];

            String commArgs = command.length() > type.length() + 1  ? command.substring(type.length() + 1) : null;

            if(type.equals("display") && manager.current != null) {
                manager.current.displayAllImages();
                continue;
            }

            if(type.equals("changePersistence") && command.split(" ").length == 2)
            {
                try
                {
                    mode = stringToPersistance(command.split(" ")[1], manager);
                    continue;
                }
                catch (Exception e)
                {
                    System.out.println("Error on changing persistence type: " + e.getMessage());
                }
            }

            try
            {
                RepoCommand repoCommand = switch (type)
                {
                    case "add" -> new AddCommand(commArgs);
                    case "remove" -> new RemoveCommand(commArgs);
                    case "update" -> new UpdateCommand(commArgs);
                    case "save" -> new SaveCommand(commArgs, manager);
                    case "load" -> new LoadCommand(commArgs, manager);
                    case "report" -> new ReportCommand(commArgs, manager);
                    case "addAll" -> new AddAllCommand(commArgs, manager);
                    default -> throw new IncorrectFormat("Unexpected value: " + type);
                };

                repoCommand.execute(manager.current);

            }
            catch (Exception e)
            {
                System.err.println("Error wrong command: " + e.getMessage());
            }

        }
        manager.printAllRepos();
        try {
            MaximalGroups maximalGroups = new MaximalGroups(manager.current);
            var grouping = maximalGroups.run();
            System.out.println(grouping.toString());
        }
        catch (Exception e)
        {
            System.err.println("Error with maximal groups: " + e.getMessage());
        }
    }

    public static PersistenceMode stringToPersistance(String type, RepositoryManager manager) {
        return switch (type)
        {
            case "json" -> new JsonPersistence(manager);
            case "plainText" -> new PlainTextPersistence(manager);
            case "binary" -> new BinaryPersistence(manager);
            case  "other" -> new OtherPersistence(manager);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}