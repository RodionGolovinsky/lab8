package utility;

import java.util.HashMap;

import classesandenums.Person;
import classesandenums.User;
import commands.*;
import databaseWorkers.AuthorizationHelper;

/**
 * Управляет командами.
 */
public class CommandManager {
    private final HashMap<String, Command> commands;
    private User user;
    private final AuthorizationHelper helper;
    private final LocaleManager localeManager;
    private final QuestionAboutPerson qap;
    private final CollectionManager collectionManager;

    public CommandManager(CollectionManager collectionManager, QuestionAboutPerson qap,
                          AuthorizationHelper helper) {
        this.collectionManager = collectionManager;
        this.qap = qap;
        this.helper = helper;
        localeManager = new LocaleManager();
        commands = new HashMap<>();
//        this.user = user;
    }

    public void registerCommands() {
        commands.put("help", new HelpCommand(this, commands));
        commands.put("info", new InfoCommand(this, collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager, qap, user));
        commands.put("update", new UpdateCommand(collectionManager, qap, user));
        commands.put("remove_by_id", new RemoveByIdCommand(collectionManager, user));
        commands.put("clear", new ClearCommand(collectionManager,user));
        commands.put("execute_script", new ExecuteScriptCommand(this));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager, qap,user));
        commands.put("remove_greater", new RemoveGreaterCommand(collectionManager, qap, user));
        commands.put("remove_lower", new RemoveLowerCommand(collectionManager, qap, user));
        commands.put("group_counting_by_id", new GroupCountingByIdCommand(collectionManager));
        commands.put("filter_starts_with_name", new FilterStartsWithNameCommand(collectionManager, qap));
        commands.put("print_unique_location", new PrintUniqueLocationCommand(collectionManager, qap));
//        commands.put("exit", new ExitCommand());
    }

    public String execute(String commandName, String arguments, Person p) {
        try {
            Command command = commands.get(commandName);
            if (p != null) return command.execute(p);
            return command.execute(arguments);
        } catch (NullPointerException exp) {
            exp.printStackTrace();
            return "Команда < " + commandName + " > не найдена.";
        }
    }


    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthorizationHelper getHelper() {
        return helper;
    }

    public LocaleManager getLocaleManager() {
        return localeManager;
    }

    public QuestionAboutPerson getQap() {
        return qap;
    }

}

