package commands;

import classesandenums.Person;
import classesandenums.User;
import exceptions.CollectionIsEmptyException;
import exceptions.PersonNotFoundException;
import exceptions.UserAccessException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;

public class RemoveByIdCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final User user;

    public RemoveByIdCommand(CollectionManager collectionManager, User user) {
        super("remove_by_id <ID>", "remove");
        this.collectionManager = collectionManager;
        this.user = user;
    }

    public String execute(String argument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Long id = Long.parseLong(argument);
            Person personToRemove = collectionManager.getById(id);
            if (personToRemove == null) throw new PersonNotFoundException();
            if (!personToRemove.getOwner().getUsername().equals(user.getUsername())) throw new UserAccessException();
            collectionManager.removeFromCollection(personToRemove);
            return "removed";
        } catch (WrongAmountOfElementsException exception) {
            return "noArgument";
        } catch (CollectionIsEmptyException exception) {
            return "emptyCollection";
        } catch (NumberFormatException exception) {
            return "notNumber";
        } catch (PersonNotFoundException exception) {
            return "personNotFound";
        } catch (UserAccessException exception){
            return "У вас недостаточно прав, чтобы удалить человека с этим ID!";
        }
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
