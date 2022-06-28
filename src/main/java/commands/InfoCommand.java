package commands;

import classesandenums.Person;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.CommandManager;
import utility.Console;

import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand {

    private final CommandManager commandManager;
    private final CollectionManager collectionManager;

    public InfoCommand(CommandManager commandManager, CollectionManager collectionManager) {
        super("info", "info");
        this.commandManager = commandManager;
        this.collectionManager = collectionManager;
    }


    public String execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
            String lastSaveTimeString = (lastSaveTime == null) ? "" :
                    lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

            return collectionManager.collectionType() + "\n"
            + "[" + collectionManager.collectionSize() + "]\n"
            + commandManager.getLocaleManager().localize("lastSave") +  ": " + lastSaveTimeString + "\n"
            + commandManager.getLocaleManager().localize("lastInit") +  ": " + lastInitTimeString;
        } catch (WrongAmountOfElementsException exception) {
            return "Использование: '" + getName() + "'";
        }
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}

