package main;

import controllers.AuthorizationController;
import databaseWorkers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.CollectionManager;
import utility.CommandManager;
import utility.QuestionAboutPerson;

import java.io.IOException;
import java.util.Scanner;

public class Proga extends Application {
    public static final String PS2 = "> ";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            PropHelper.getProperties();
            DataBaseHandler dataBaseHandler = new DataBaseHandler(PropHelper.getHost(), Integer.parseInt(PropHelper.getPort()), PropHelper.getUser(), PropHelper.getPassword(), PropHelper.getBasename());
            Scanner userScanner = new Scanner(System.in);
            DataBaseUserManager dataBaseUserManager = new DataBaseUserManager(dataBaseHandler);
            AuthorizationHelper helper = new AuthorizationHelper(userScanner, dataBaseUserManager);
            DataBaseCollectionManager dataBaseCollectionManager = new DataBaseCollectionManager(dataBaseHandler,dataBaseUserManager);
            CollectionManager collectionManager = new CollectionManager(dataBaseCollectionManager);
            QuestionAboutPerson questionAboutPerson = new QuestionAboutPerson(userScanner);
            CommandManager commandManager = new CommandManager(collectionManager, questionAboutPerson, helper);
            loadStage(collectionManager, commandManager);
//            while (true) {
//                System.out.println("Введите команду");
//                String arguments;
//                String[] commandNameAndArguments = userScanner.nextLine().split(" ");
//                String commandName = commandNameAndArguments[0];
//                if (commandNameAndArguments.length > 1) {
//                    arguments = commandNameAndArguments[1];
//                } else arguments = "";
//                commandManager.execute(commandName, arguments);
//            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Произошла катастрофа.");
        }
    }

    private void loadStage(CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../gui/Authorization.fxml"));
        loader.setController(new AuthorizationController(collectionManager, commandManager));
        loader.load();
        Stage stage = new Stage();
        stage.setTitle("Authorization");
        stage.setScene(new Scene(loader.getRoot(), stage.getWidth(), stage.getHeight()));
        stage.setResizable(false);
        stage.show();
    }

}
