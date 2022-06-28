package commands;

import classesandenums.Person;
import classesandenums.User;
import exceptions.IncorrectInputInScriptException;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;

import java.time.LocalDateTime;

public class AddIfMinCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final QuestionAboutPerson questionAboutPerson;
    private final User user;

    public AddIfMinCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson, User user) {
        super("add_if_min {element}", "addIfMin");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
        this.user = user;
    }

    public String execute(String argument) {
        return null;
    }

    @Override
    public String execute(Person p) {
//        Person personToAdd = null;
//        try {
//            personToAdd = new Person(
//                    collectionManager.generateNextId(),
//                    questionAboutPerson.askName(),
//                    questionAboutPerson.askCoordinates(),
//                    LocalDateTime.now(),
//                    questionAboutPerson.askHeight(),
//                    questionAboutPerson.askEyeColour(),
//                    questionAboutPerson.askHairColour(),
//                    questionAboutPerson.askNationality(),
//                    questionAboutPerson.askLocation()
//            );
//        } catch (IncorrectInputInScriptException e) {
//            return "Возникла ошибка при сборе данных";
//        }
        if (collectionManager.collectionSize() == 0 || p.compareTo(collectionManager.getFirst()) < 0) {
            p.setId(collectionManager.generateNextId());
            p.setCreationDate(LocalDateTime.now());
            collectionManager.addToCollection(p, user);
            return "added";
        } else return "notMinPerson";
    }

}


