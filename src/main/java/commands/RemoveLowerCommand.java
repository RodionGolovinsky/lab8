package commands;

import classesandenums.Person;
import classesandenums.User;
import exceptions.CollectionIsEmptyException;
import exceptions.IncorrectInputInScriptException;
import exceptions.PersonNotFoundException;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;

import java.time.LocalDateTime;

public class RemoveLowerCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final QuestionAboutPerson questionAboutPerson;
    private final User user;

    public RemoveLowerCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson, User user) {
        super("remove_lower {element}", "removeLower");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
        this.user = user;
    }


    public String execute(String argument) {
        return null;
    }

    @Override
    public String execute(Person p) {
        try {
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
//            if (!argument.isEmpty()) {
//                return "Зачем аргумент? Ну да ладно";
//            }
//            Person personToFind = new Person(
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
            p.setId(collectionManager.generateNextId());
            p.setCreationDate(LocalDateTime.now());
            Person personFromCollection = collectionManager.getByValue(p);
            if (personFromCollection == null) throw new PersonNotFoundException();
            personFromCollection.setOwner(user);
            collectionManager.removeLower(personFromCollection);
            return "removed";
        } catch (CollectionIsEmptyException exception) {
            return "emptyCollection";
        } catch (PersonNotFoundException exception) {
            return "personNotFound";
        }
//        } catch (IncorrectInputInScriptException e) {
//            return "Возникла ошибка при сборе данных";
//        }
    }

}
