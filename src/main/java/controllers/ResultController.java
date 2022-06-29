package controllers;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import utility.CommandManager;

import java.awt.*;

public class ResultController {

    private final CommandManager commandManager;
    private final String result;

    public ResultController(CommandManager commandManager, String result) {
        this.commandManager = commandManager;
        this.result = result;
    }

    public ScrollPane resultPane;

    public void initialize() {
        Text text = new Text(commandManager.getLocaleManager().localize(result));
        text.wrappingWidthProperty().bind(resultPane.widthProperty());
        text.setStyle("-fx-font: 30 arial;");
        resultPane.setContent(text);
    }

}
