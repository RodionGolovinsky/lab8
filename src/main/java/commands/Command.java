package commands;

import classesandenums.Person;

public interface Command {
    String getDescription();

    String getName();

    String execute(String argument);

    String execute(Person p);
}
