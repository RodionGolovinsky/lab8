package commands;


import classesandenums.Person;
import utility.CollectionManager;
import utility.Console;

public class ShowCommand extends AbstractCommand {

    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show", "show");
        this.collectionManager = collectionManager;
    }


    public String execute(String argument) {
        return collectionManager.toString();
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
