package commands;


import classesandenums.Person;
import utility.CollectionManager;


public class GroupCountingByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;

    public GroupCountingByIdCommand(CollectionManager collectionManager) {
        super("group_counting_by_id", "groupCounting");
        this.collectionManager = collectionManager;
    }

    public String execute(String argument) {
        return collectionManager.groupCountingById();
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
