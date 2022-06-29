package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utility.CollectionManager;
import utility.CommandManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AuthorizationController {

    private final CollectionManager collectionManager;
    private final CommandManager commandManager;

    public AuthorizationController(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public TextField nameFld;

    public PasswordField passwordFld;

    public CheckBox registerCheck;

    public Button signInBut;

    public Label resLbl;

    public void initialize() {
        nameFld.setPromptText(commandManager.getLocaleManager().localize("username"));
        passwordFld.setPromptText(commandManager.getLocaleManager().localize("password"));
        registerCheck.setText(commandManager.getLocaleManager().localize("register"));
        signInBut.setText(commandManager.getLocaleManager().localize("signIn"));
    }

    public void execute() throws IOException {
        if (!nameFld.getText().equals("") && !passwordFld.getText().equals("")) {
            if (registerCheck.isSelected()) {
                String result = commandManager.getHelper().run(nameFld.getText(), passwordFld.getText(), true);
                if (result != null) resLbl.setText(commandManager.getLocaleManager().localize(result));
                else {
                    commandManager.setUser(commandManager.getHelper().getUser());
                    loadStage();
                }
            } else {
                String result = commandManager.getHelper().run(nameFld.getText(), passwordFld.getText(), false);
                if (result != null) resLbl.setText(commandManager.getLocaleManager().localize(result));
                else {
                    commandManager.setUser(commandManager.getHelper().getUser());
                    loadStage();
                }
            }
        }
    }

    private void loadStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../gui/MainApplication.fxml"));
        loader.setController(new MainApplicationController(collectionManager, commandManager));
        loader.load();
        Stage stage = new Stage();
        InputStream icon = getClass().getResourceAsStream("/icon.png");
        Image iconImage = new Image(icon);
        stage.getIcons().add(iconImage);
        stage.setTitle("Lab 8");
        stage.setScene(new Scene(loader.getRoot(), stage.getWidth(), stage.getHeight()));
        stage.setResizable(false);
        stage.show();
        Stage cur = (Stage) resLbl.getScene().getWindow();
        cur.close();
    }

}
