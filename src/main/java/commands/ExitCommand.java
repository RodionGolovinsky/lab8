package commands;


import classesandenums.Person;

public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "завершить программу ");
    }


    public String execute(String argument) {
        System.exit(0);
        return null;
    }

    @Override
    public String execute(Person p) {
        return null;
    }

}
