package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.util.ResourceManager;
import de.uniks.pmws2021.view.AlternateListCellFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.List;


public class ChooseHeroViewSubController {

    // Variables
    private Parent view;
    private RPGEditor rpgEditor;
    private Hero selectedHero;
    private List<Hero> heroes;

    // Interaction
    private Button buttonChooseAndStart;
    private Button buttonDeleteHero;
    private ListView<Hero> heroesListView;

    public ChooseHeroViewSubController(Parent view, RPGEditor rpgEditor) {
        this.view = view;
        this.rpgEditor = rpgEditor;
    }

    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public void init() {
        // Load all view references
        buttonChooseAndStart = (Button) view.lookup("#button_start");
        buttonDeleteHero = (Button) view.lookup("#button_deleteHero");
        heroesListView = (ListView<Hero>) view.lookup("#listview_heroesList");
        heroesListView.setCellFactory(new AlternateListCellFactory());

        // Add action listeners
        buttonChooseAndStart.setOnAction(this::buttonChooseAndStartOnClick);
        buttonDeleteHero.setOnAction(this::buttonDeleteHeroOnClick);
        heroesListView.setOnMouseReleased(this::onHeroListClicked);

        // Interaction
        // Load heroes
        heroes = rpgEditor.getLoadedHeroes();
        // Set ListView Height
        int listMaxHeight;
        if (heroes.size() != 0) {
            listMaxHeight = heroes.size() * 80 + 2;
        } else {
            listMaxHeight = 80;
        }
        this.heroesListView.setMaxHeight(listMaxHeight);

        // Sort list -> newest hero on top -> otherwise sorting was random after saving heroes
        heroes.sort((h1, h2) -> Integer.compare(h2.getHeroId(), h1.getHeroId()));

        // put heroes on ListView
        this.heroesListView.setItems(FXCollections.observableList(heroes));
    }

    public void stop() {
        buttonChooseAndStart.setOnAction(null);
        buttonDeleteHero.setOnAction(null);
    }

    // ===========================================================================================
    // Button Action Methods
    // ===========================================================================================

    private void buttonChooseAndStartOnClick(ActionEvent actionEvent) {
        if (selectedHero != null) {
            String heroName = this.heroesListView.getSelectionModel().getSelectedItem().getName();
            String heroMode = this.heroesListView.getSelectionModel().getSelectedItem().getMode();

            rpgEditor.enterDungeon(rpgEditor.haveHero(heroName, heroMode));
            StageManager.showDungeonScreen();
        }
    }

    private void onHeroListClicked(MouseEvent event) {
        selectedHero = this.heroesListView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {
            if (selectedHero != null) {
                String heroName = this.heroesListView.getSelectionModel().getSelectedItem().getName();
                String heroMode = this.heroesListView.getSelectionModel().getSelectedItem().getMode();

                rpgEditor.enterDungeon(rpgEditor.haveHero(heroName, heroMode));
                StageManager.showDungeonScreen();
            }
        }
    }

    private void buttonDeleteHeroOnClick(ActionEvent actionEvent) {
        if (selectedHero != null) {
            ResourceManager.deleteHero(selectedHero);

            // Load heroes again
            rpgEditor.setLoadedHeroes(ResourceManager.loadAllHeroes());
            heroes = rpgEditor.getLoadedHeroes();
            // Set ListView Height
            int listMaxHeight;
            if (heroes.size() != 0) {
                listMaxHeight = heroes.size() * 80 + 2;
            } else {
                listMaxHeight = 80;
            }
            this.heroesListView.setMaxHeight(listMaxHeight);

            // Sort list -> newest hero on top -> otherwise sorting was random after saving heroes
            heroes.sort((h1, h2) -> Integer.compare(h2.getHeroId(), h1.getHeroId()));

            // put heroes on ListView
            this.heroesListView.setItems(FXCollections.observableList(heroes));
        }
    }

    // ===========================================================================================
    // Additional Methods
    // ===========================================================================================

}
