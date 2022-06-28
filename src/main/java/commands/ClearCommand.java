package commands;

import classesandenums.Person;
import classesandenums.User;
import utility.CollectionManager;
import utility.Console;

public class ClearCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final User user;

    public ClearCommand(CollectionManager collectionManager, User user) {
        super("clear", "clear");
        this.collectionManager = collectionManager;
        this.user = user;
    }

    public String execute(String argument) {
        collectionManager.clearCollection(user);
        return "cleared";
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
