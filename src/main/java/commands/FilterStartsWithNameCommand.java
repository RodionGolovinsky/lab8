package commands;

import classesandenums.Person;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;

public class FilterStartsWithNameCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final QuestionAboutPerson questionAboutPerson;

    public FilterStartsWithNameCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson) {
        super("filter_starts_with_name (name)", "filterStarts");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
    }

    public String execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            StringBuilder sb = new StringBuilder();
            collectionManager.filterStartsWithName(argument).forEach(p -> sb.append(p).append("\n"));
            return sb.toString();
        } catch (WrongAmountOfElementsException exception) {
            return "noArgument";
        }
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
