package commands;

import classesandenums.Person;
import classesandenums.User;
import exceptions.IncorrectInputInScriptException;
import utility.CollectionManager;
import utility.QuestionAboutPerson;

import java.time.LocalDateTime;

public class AddCommand extends AbstractCommand {

    private final CollectionManager collectionManager;
    private final QuestionAboutPerson questionAboutPerson;
    private final User user;

    public AddCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson, User user) {
        super("add ", "add");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
        this.user = user;
    }

    public String execute(String argument) {
        return null;
    }

    @Override
    public String execute(Person p) {

//            collectionManager.addToCollection(new Person(
//                    collectionManager.generateNextId(),
//                    questionAboutPerson.askName(),
//                    questionAboutPerson.askCoordinates(),
//                    LocalDateTime.now(),
//                    questionAboutPerson.askHeight(),
//                    questionAboutPerson.askEyeColour(),
//                    questionAboutPerson.askHairColour(),
//                    questionAboutPerson.askNationality(),
//                    questionAboutPerson.askLocation()
//            ), user);
        p.setId(collectionManager.generateNextId());
        p.setCreationDate(LocalDateTime.now());
        collectionManager.addToCollection(p, user);
        return "added";
    }

}
