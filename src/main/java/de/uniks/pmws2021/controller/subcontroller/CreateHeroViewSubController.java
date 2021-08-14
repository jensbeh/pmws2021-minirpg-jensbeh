package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.model.Mode;
import de.uniks.pmws2021.util.ResourceManager;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;


public class CreateHeroViewSubController {

    // Variables
    private Parent view;
    private RPGEditor rpgEditor;

    // Interaction
    private Button buttonCreateAndStart;
    private TextField tfHeroname;
    private CheckBox cbHardmode;
    private VBox iconHero1;
    private VBox iconHero2;
    private VBox iconHero3;
    private Label labelHeroExists;

    public CreateHeroViewSubController(Parent view, RPGEditor rpgEditor) {
        this.view = view;
        this.rpgEditor = rpgEditor;
    }

   // ===========================================================================================
   // Controller
   // ===========================================================================================

    public void init() {
        // Load all view references
        buttonCreateAndStart = (Button) view.lookup("#button_create_start");
        tfHeroname = (TextField) view.lookup("#tf_heroname");
        cbHardmode = (CheckBox) view.lookup("#cb_hardmode");
        labelHeroExists = (Label) view.lookup("#label_heroExists");

        iconHero1 = (VBox) view.lookup("#vbox_iconHero1");
        iconHero2 = (VBox) view.lookup("#vbox_iconHero2");
        iconHero3 = (VBox) view.lookup("#vbox_iconHero3");

        // Add action listeners
        buttonCreateAndStart.setOnAction(this::buttonCreateAndStartOnClick);

        iconHero1.setOnMouseClicked(this::selectHeroOne);
        iconHero2.setOnMouseClicked(this::selectHeroTwo);
        iconHero3.setOnMouseClicked(this::selectHeroThree);

        // Interaction
    }

    public void stop() {
        buttonCreateAndStart.setOnAction(null);
    }

    // ===========================================================================================
    // Button Action Methods
    // ===========================================================================================

    private void buttonCreateAndStartOnClick(ActionEvent actionEvent) {
        String heroName = tfHeroname.getText();
        String hardmode;

        if (cbHardmode.isSelected()) {
            hardmode = Mode.hard;
        } else {
            hardmode = Mode.normal;
        }

        rpgEditor.enterDungeon(rpgEditor.haveHero(heroName, hardmode));

        StageManager.showDungeonScreen();
    }

    // ===========================================================================================
    // Additional Methods
    // ===========================================================================================

    private void selectHeroOne(MouseEvent mouseEvent) {
        rpgEditor.setHeroIconNr(1);

        iconHero2.setStyle("");
        iconHero3.setStyle("");

        iconHero1.setStyle("-fx-background-color: #D0F5A9;");
        iconHero2.getStylesheets().add(ResourceManager.loadStyle("styles_SelectHero").toString());
        iconHero3.getStylesheets().add(ResourceManager.loadStyle("styles_SelectHero").toString());
    }

    private void selectHeroTwo(MouseEvent mouseEvent) {
        rpgEditor.setHeroIconNr(2);

        iconHero1.setStyle("");
        iconHero3.setStyle("");


        iconHero1.getStylesheets().add(ResourceManager.loadStyle("styles_SelectHero").toString());
        iconHero2.setStyle("-fx-background-color: #D0F5A9;");
        iconHero3.getStylesheets().add(ResourceManager.loadStyle("styles_SelectHero").toString());
    }

    private void selectHeroThree(MouseEvent mouseEvent) {
        rpgEditor.setHeroIconNr(3);

        iconHero1.setStyle("");
        iconHero2.setStyle("");

        iconHero1.getStylesheets().add(ResourceManager.loadStyle("styles_SelectHero").toString());
        iconHero2.getStylesheets().add(ResourceManager.loadStyle("styles_SelectHero").toString());
        iconHero3.setStyle("-fx-background-color: #D0F5A9;");
    }
}
