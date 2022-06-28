package commands;

import classesandenums.*;
import exceptions.*;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;
import java.time.LocalDateTime;

public class UpdateCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final QuestionAboutPerson questionAboutPerson;
    private final User user;

    public UpdateCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson, User user) {
        super("update <ID> {element}", "update");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
        this.user=user;
    }


    public String execute(String argument) {
        return null;
    }

    @Override
    public String execute(Person p) {
        try {
//            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
//            Long id = Long.valueOf(argument);
            Person oldPerson = collectionManager.getById(p.getId());
            if (oldPerson == null) throw new PersonNotFoundException();
            if (!oldPerson.getOwner().getUsername().equals(user.getUsername())) throw new UserAccessException();
            p.setCreationDate(oldPerson.getCreationDate());
            p.setOwner(oldPerson.getOwner());
//            String name = oldPerson.getName();
//            Coordinates coordinates = oldPerson.getCoordinates();
//            LocalDateTime creationDate = oldPerson.getCreationDate();
//            int height = oldPerson.getHeight();
//            EColor eyeColor = oldPerson.getEyeColor();
//            HColor hairColor = oldPerson.getHairColor();
//            Country nationality = oldPerson.getNationality();
//            Location location = oldPerson.getLocation();
//            if (questionAboutPerson.askQuestion("Хотите изменить имя человека?")) name = questionAboutPerson.askName();
//            if (questionAboutPerson.askQuestion("Хотите изменить координаты человека?"))
//                coordinates = questionAboutPerson.askCoordinates();
//            if (questionAboutPerson.askQuestion("Хотите изменить рост человека?"))
//                height = questionAboutPerson.askHeight();
//            if (questionAboutPerson.askQuestion("Хотите изменить цвет глаз человека?"))
//                eyeColor = questionAboutPerson.askEyeColour();
//            if (questionAboutPerson.askQuestion("Хотите изменить цвет волос человека?"))
//                hairColor = questionAboutPerson.askHairColour();
//            if (questionAboutPerson.askQuestion("Хотите изменить местопроживание человека?"))
//                nationality = questionAboutPerson.askNationality();
//            if (questionAboutPerson.askQuestion("Хотите изменить местоположение человека?"))
//                location = questionAboutPerson.askLocation();
//            Person newPerson = new Person(
//                    id,
//                    name,
//                    coordinates,
//                    creationDate,
//                    height,
//                    eyeColor,
//                    hairColor,
//                    nationality,
//                    location,
//                    user);
            collectionManager.updatePerson(p.getId(), p);
            return "updated";
        } catch (CollectionIsEmptyException exception) {
            return "emptyCollection";
        } catch (NumberFormatException exception) {
            return "mustBeNumber";
        } catch (PersonNotFoundException exception) {
            return "personNotFound";
//        } catch (WrongAmountOfElementsException e) {
//            return "Необходимо ввести аргумент";
//        } catch (IncorrectInputInScriptException e) {
//            return "Возникла ошибка при сборе данных";
        } catch (UserAccessException e){
            return "Вы не можете изменить этого человека!";
        }
    }

}
