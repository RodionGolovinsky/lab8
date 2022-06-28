package commands;


import classesandenums.Person;
import utility.CommandManager;

import java.util.HashMap;

public class HelpCommand extends AbstractCommand {
    private final CommandManager commandManager;
    private final HashMap<String, Command> commands;

    public HelpCommand(CommandManager commandManager, HashMap<String, Command> commands) {
        super("help", "helpC");
        this.commandManager = commandManager;
        this.commands = commands;

    }


    public String execute(String argument) {
        StringBuilder sb = new StringBuilder();
        for (Command command : commands.values()) {
            sb.append(command.getName()).append(" : ")
                    .append(commandManager.getLocaleManager().localize(command.getDescription())).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
