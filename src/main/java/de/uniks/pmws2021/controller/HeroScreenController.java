package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.controller.subcontroller.ChooseHeroViewSubController;
import de.uniks.pmws2021.controller.subcontroller.CreateHeroViewSubController;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.util.ResourceManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import java.util.List;

public class HeroScreenController {
    // Variables
    private final Parent view;
    private RPGEditor rpgEditor;
    List<Hero> heroes;

    // Controller
    private CreateHeroViewSubController createHeroViewCtrl;
    private ChooseHeroViewSubController chooseHeroViewCtrl;

    // Container
    private HBox createHeroContainer;
    private HBox chooseHeroContainer;

    public HeroScreenController(Parent view, RPGEditor rpgEditor) {
        this.view = view;
        this.rpgEditor = rpgEditor;
    }

    // Controller
    public void init() {
        // Load all view references
        createHeroContainer = (HBox) view.lookup("#hbox_create_and_start_hero");
        chooseHeroContainer = (HBox) view.lookup("#hbox_create_hero");

        heroes = ResourceManager.loadAllHeroes();
        this.rpgEditor.setLoadedHeroes(heroes);

        // Initialisation
        this.initCreateHeroSubView();
        this.initChooseHeroSubView();
    }

    public void stop() {
        // Clear references
        // Clear action listeners

        createHeroViewCtrl.stop();
        chooseHeroViewCtrl.stop();
    }

    // ===========================================================================================
    // Additional Methods
    // ===========================================================================================

    private void initCreateHeroSubView() {
        // Clear
        this.createHeroContainer.getChildren().clear();

        try {
            // View
            Parent view = FXMLLoader.load(StageManager.class.getResource("subviews/CreateNewHero.fxml"));

            // Controller
            createHeroViewCtrl = new CreateHeroViewSubController(view, rpgEditor);
            createHeroViewCtrl.init();

            // Add to Box
            this.createHeroContainer.getChildren().add(view);

        } catch (Exception e) {
            System.err.println("error on showing CreateHeroSubViewScreen");
            e.printStackTrace();
        }
    }

    private void initChooseHeroSubView() {
        // Clear
        this.chooseHeroContainer.getChildren().clear();

        try {
            // View
            Parent view = FXMLLoader.load(StageManager.class.getResource("subviews/ChooseExistingHero.fxml"));

            // Controller
            chooseHeroViewCtrl = new ChooseHeroViewSubController(view, rpgEditor);
            chooseHeroViewCtrl.init();

            // Add to Box
            this.chooseHeroContainer.getChildren().add(view);

        } catch (Exception e) {
            System.err.println("error on showing ChooseHeroSubViewScreen");
            e.printStackTrace();
        }
    }
}