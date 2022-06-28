package commands;

import classesandenums.Person;
import utility.CommandManager;
import utility.Console;
import utility.FileReaderForScript;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ExecuteScriptCommand extends AbstractCommand {

    CommandManager commandManager;

    public ExecuteScriptCommand(CommandManager commandManager) {
        super("execute_script <file_name>", "executeScript");
        this.commandManager = commandManager;
    }

    public String execute(String argument) {
        FileReaderForScript readerForScript = new FileReaderForScript();
        Path path;
        try {
            path = Paths.get(argument);
            Scanner inputFromFile = new Scanner(path);
            StringBuilder sb = new StringBuilder();
            while (inputFromFile.hasNext()) {
                String arguments;
                String[] commandNameAndArguments = inputFromFile.nextLine().split(" ");
                String commandName = commandNameAndArguments[0];
                if (commandNameAndArguments.length > 1) {
                    arguments = commandNameAndArguments[1];
                } else {
                    arguments = "";
                }
                if (commandName.equals("execute_script") || commandName.equals("add") || commandName.equals("add_if_min")
                        || commandName.equals("remove_greater") || commandName.equals("remove_lower")
                        || commandName.equals("update")) {
                    sb.append(commandManager.getLocaleManager().localize("forbiddenCommand"));
                } else {
                    sb.append(commandManager.getLocaleManager()
                            .localize(commandManager.execute(commandName, arguments, null))).append("\n");
                }
            }
            return sb.toString();
        } catch (IOException e) {
            return "noRights";
        }
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
