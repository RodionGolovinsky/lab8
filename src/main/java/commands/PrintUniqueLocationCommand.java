package commands;

import classesandenums.Person;
import exceptions.CollectionIsEmptyException;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;

public class PrintUniqueLocationCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final QuestionAboutPerson questionAboutPerson;


    public PrintUniqueLocationCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson) {
        super("print_unique_location","printUnique");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
    }

    public String execute(String argument) {
       try {
           if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
           if (!argument.isEmpty()) {
               return "Зачем аргумент? Ну да ладно";
           }
           StringBuilder sb = new StringBuilder();
           collectionManager.getArrayOfUniqueLocation().forEach(l -> sb.append(l).append("\n"));
           return sb.toString();
       } catch (CollectionIsEmptyException e) {
           return "emptyCollection";
       }
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
