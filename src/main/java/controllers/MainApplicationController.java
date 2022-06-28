package controllers;

import classesandenums.Country;
import classesandenums.EColor;
import classesandenums.HColor;
import classesandenums.Person;
import controllers.map.MapController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.CollectionManager;
import utility.CommandManager;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashSet;

public class MainApplicationController {

    private final CollectionManager collectionManager;
    private final CommandManager commandManager;
    private MapController mapController;
    private GraphicsContext gc;
    private String activeCommand = null;

    public Menu userMenu, commandsMenu, helpMenu, langMenu;
    public MenuItem logoutItm;

    public ToggleGroup langs;
    public RadioMenuItem ruItm, rsItm, huItm, enItm;

    public TableView<Person> personTable;
    public TableColumn<Person, Long> idCol;
    public TableColumn<Person, String> nameCol, xCol, yCol, dateCol;
    public TableColumn<Person, Integer> heightCol;
    public TableColumn<Person, EColor> eyeCol;
    public TableColumn<Person, HColor> hairCol;
    public TableColumn<Person, Country> nationCol;
    public TableColumn<Person, String> locXCol, locYCol, locZCol, locNameCol, ownerCol;

    public Canvas canvas;

    public TextField filterFld;
    public ChoiceBox<String> filterBox;
    public Button filterBut;

    public TextField idFld, nameFld, xFld, yFld, dateFld, heightFld;
    public ChoiceBox<EColor> eyeBox;
    public ChoiceBox<HColor> hairBox;
    public ChoiceBox<Country> nationBox;
    public TextField locXFld, locYFld, locZFld, locNameFld, ownerFld;

    public Button okBut, removeBut, updateBut;

    public MainApplicationController(CollectionManager collectionManager, CommandManager commandManager) {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
    }

    public void initialize() {
        commandManager.registerCommands();
        initBoxes();
        userMenu.setText(commandManager.getUser().getUsername());
        initTable();
        initMap();
        initLangs();
        fillTableAndMap();
        okBut.setDisable(true);
    }

    private void initTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        xCol.setCellValueFactory(cell -> new SimpleStringProperty(NumberFormat
                .getInstance(commandManager.getLocaleManager().getCurrentLocale())
                .format(cell.getValue().getCoordinates().getX())));
        yCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCoordinates().getY())));
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(DateFormat
                .getDateInstance(DateFormat.DEFAULT, commandManager.getLocaleManager().getCurrentLocale())
                .format(Date.from(cell.getValue().getCreationDate().toLocalDate()
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()))));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        eyeCol.setCellValueFactory(new PropertyValueFactory<>("eyeColor"));
        hairCol.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        nationCol.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        locXCol.setCellValueFactory(cell -> new SimpleStringProperty(NumberFormat
                .getInstance(commandManager.getLocaleManager().getCurrentLocale())
                .format(cell.getValue().getLocation().getX())));
        locYCol.setCellValueFactory(cell -> new SimpleStringProperty(NumberFormat
                .getInstance(commandManager.getLocaleManager().getCurrentLocale())
                .format(cell.getValue().getLocation().getY())));
        locZCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLocation().getZ().toString()));
        locNameCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLocation().getName()));
        ownerCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOwner().getUsername()));
        personTable.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    setFields(row.getItem());
                }
            });
            return row;
        });
    }

    private void initMap() {
        mapController = new MapController(gc, canvas, collectionManager.getPersonCollection(), commandManager);
        canvas.setOnMouseClicked(event -> {
            Person p = mapController.getClicked(event.getX(), event.getY());
            if (p != null) {
                setFields(p);
            }
        });
    }

    private void fillTableAndMap() {
        personTable.getItems().clear();
        collectionManager.getPersonCollection().forEach(p -> personTable.getItems().add(p));
        mapController.startDraw(collectionManager.getPersonCollection());
    }

    private void fillTable(LinkedHashSet<Person> persons) {
        personTable.getItems().clear();
        persons.forEach(p -> personTable.getItems().add(p));
    }

    public void noArgExecute(ActionEvent event) throws IOException {
        String commandName = ((MenuItem) event.getSource()).getText();
        String result = commandManager.execute(commandName, "", null);
        loadStage("Result", result);
    }

    public void withArgExecute(ActionEvent event) throws IOException {
        String command = ((MenuItem) event.getSource()).getText();
        if (command.equals("execute_script")) {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(new FileChooser
                    .ExtensionFilter("TXT files (*.txt)", "*.txt"));
            File script = chooser.showOpenDialog(okBut.getScene().getWindow());
            if (script != null) {
                loadStage("Result", commandManager.execute(command, script.getAbsolutePath(), null));
            }
        } else {
            removeBut.setDisable(true);
            updateBut.setDisable(true);
            okBut.setDisable(false);
            clearFields();
            activeCommand = command;
        }
    }

    public void logout(ActionEvent event) throws IOException {
        commandManager.setUser(null);
        commandManager.getHelper().setUser(null);
        loadStage("Authorization", null);
        Stage cur = (Stage) personTable.getScene().getWindow();
        cur.close();
    }

    public void confirm(ActionEvent event) throws IOException {
        String result = "";
        Person person;
        switch (activeCommand) {
            case "add":
            case "add_if_min":
            case "remove_greater":
            case "remove_lower":
                person = commandManager.getQap().parsePerson(null, nameFld.getText(), xFld.getText(), yFld.getText(),
                        heightFld.getText(), eyeBox.getValue(), hairBox.getValue(), nationBox.getValue(),
                        locXFld.getText(), locYFld.getText(), locZFld.getText(), locNameFld.getText());
                if (person == null) {
                    result = "errorParsingPerson";
                } else {
                    result = commandManager.execute(activeCommand, "", person);
                }
                break;
            case "filter_starts_with_name_command":
                result = commandManager.execute(activeCommand, nameFld.getText(), null);
                break;
        }
        loadStage("Result", result);
        removeBut.setDisable(false);
        updateBut.setDisable(false);
        okBut.setDisable(true);
        clearFields();
        activeCommand = null;
    }

    public void remove(ActionEvent event) throws IOException {
        String result = commandManager.execute("remove_by_id", idFld.getText(), null);
        loadStage("Result", result);
        clearFields();
    }

    public void update(ActionEvent event) throws IOException {
        Person p = commandManager.getQap().parsePerson(idFld.getText(), nameFld.getText(), xFld.getText(), yFld.getText(),
                heightFld.getText(), eyeBox.getValue(), hairBox.getValue(), nationBox.getValue(),
                locXFld.getText(), locYFld.getText(), locZFld.getText(), locNameFld.getText());
        String result;
        if (p == null) {
            result = "errorParsingPerson";
        } else {
            result = commandManager.execute("update", "", p);
        }
        loadStage("Result", result);
    }

    public void filter(ActionEvent event) {
        if (filterFld.getText().equals("")) fillTableAndMap();
        else {
            LinkedHashSet<Person> filtered = collectionManager.filter(filterBox.getValue(), filterFld.getText());
            if (filtered != null) fillTable(filtered);
        }
    }

    private void setFields(Person p) {
        if (!p.getOwner().getUsername().equals(commandManager.getUser().getUsername())) {
            updateBut.setDisable(true);
            removeBut.setDisable(true);
        } else {
            updateBut.setDisable(false);
            removeBut.setDisable(false);
        }
        idFld.setText(p.getId().toString());
        nameFld.setText(p.getName());
        xFld.setText(String.valueOf(p.getCoordinates().getX()));
        yFld.setText(String.valueOf(p.getCoordinates().getY()));
        dateFld.setText(DateFormat
                .getDateInstance(DateFormat.DEFAULT, commandManager.getLocaleManager().getCurrentLocale())
                .format(Date.from(p.getCreationDate().toLocalDate()
                        .atStartOfDay(ZoneId.systemDefault()).toInstant())));
        heightFld.setText(String.valueOf(p.getHeight()));
        eyeBox.setValue(p.getEyeColor());
        hairBox.setValue(p.getHairColor());
        nationBox.setValue(p.getNationality());
        locXFld.setText(p.getLocation().getX().toString());
        locYFld.setText(String.valueOf(p.getLocation().getY()));
        locZFld.setText(p.getLocation().getZ().toString());
        locNameFld.setText(p.getLocation().getName());
        ownerFld.setText(commandManager.getUser().getUsername());
    }

    private void clearFields() {
        idFld.setText("");
        nameFld.setText("");
        xFld.setText("");
        yFld.setText("");
        dateFld.setText("");
        heightFld.setText("");
        eyeBox.setValue(EColor.BLUE);
        hairBox.setValue(HColor.BLACK);
        nationBox.setValue(Country.RUSSIA);
        locXFld.setText("");
        locYFld.setText("");
        locZFld.setText("");
        locNameFld.setText("");
        ownerFld.setText(commandManager.getUser().getUsername());
    }

    private void loadStage(String name, String result) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../gui/" + name + ".fxml"));
        if (result != null) loader.setController(new ResultController(commandManager, result));
        else loader.setController(new AuthorizationController(collectionManager, commandManager));
        loader.load();
        Stage stage = new Stage();
        stage.setTitle(name);
        stage.setScene(new Scene(loader.getRoot(), stage.getWidth(), stage.getHeight()));
        stage.setResizable(false);
        stage.show();
        stage.setOnHiding(event -> fillTableAndMap());
        stage.setOnCloseRequest(event -> fillTableAndMap());
    }

    private void initLangs() {
        langs.selectToggle(ruItm);
        ruItm.setOnAction(event -> {
            changeLang("ru");
            fillTableAndMap();
        });
        rsItm.setOnAction(event -> {
            changeLang("rs");
            fillTableAndMap();
        });
        huItm.setOnAction(event -> {
            changeLang("hu");
            fillTableAndMap();
        });
        enItm.setOnAction(event -> {
            changeLang("en_CA");
            fillTableAndMap();
        });
    }

    private void changeLang(String locale) {
        commandManager.getLocaleManager().setLocale(locale);
        logoutItm.setText(commandManager.getLocaleManager().localize("logout"));
        commandsMenu.setText(commandManager.getLocaleManager().localize("commands"));
        helpMenu.setText(commandManager.getLocaleManager().localize("help"));
        langMenu.setText(commandManager.getLocaleManager().localize("languages"));
        filterBut.setText(commandManager.getLocaleManager().localize("filter"));
    }

    private void initBoxes() {
        ObservableList<String> fields = FXCollections.observableArrayList("id", "name", "x", "y", "height",
                "eyeColor", "hairColor", "nationality", "location x", "location y", "location z", "location name", "owner");
        filterBox.getItems().addAll(fields);
        filterBox.setValue("id");
        ObservableList<EColor> eyes = FXCollections.observableArrayList(EColor.BLUE, EColor.WHITE, EColor.GREEN,
                EColor.ORANGE);
        eyeBox.getItems().addAll(eyes);
        eyeBox.setValue(EColor.BLUE);
        ObservableList<HColor> hairs = FXCollections.observableArrayList(HColor.BLACK, HColor.GREEN, HColor.WHITE,
                HColor.YELLOW);
        hairBox.getItems().addAll(hairs);
        hairBox.setValue(HColor.BLACK);
        ObservableList<Country> nations = FXCollections.observableArrayList(Country.GERMANY, Country.RUSSIA, Country.THAILAND);
        nationBox.getItems().addAll(nations);
        nationBox.setValue(Country.RUSSIA);
    }

}
