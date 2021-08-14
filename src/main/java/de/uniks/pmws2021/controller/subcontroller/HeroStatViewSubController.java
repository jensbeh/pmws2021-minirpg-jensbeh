package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.HeroStat;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HeroStatViewSubController {

    private PropertyChangeListener costListener = this::onCostChanged;
    private PropertyChangeListener levelListener = this::onLevelChanged;
    private PropertyChangeListener valueListener = this::onValueChanged;

    // Variables
    private RPGEditor rpgEditor;
    private Parent view;
    private HeroStat model;

    // Interaction
    private Label statLvl;
    private Label statVal;
    private Label statCost;
    private Label statName;
    private Button buttonUpgradeStats;
    private HBox hbox;

    public HeroStatViewSubController(HeroStat model, Parent view, RPGEditor editor) {
        this.rpgEditor = editor;
        this.view = view;
        this.model = model;
    }

    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public void init() {
        // Load all view references
        statLvl = (Label) view.lookup("#label_stat_lvl");
        statVal = (Label) view.lookup("#label_stat_val");
        statCost = (Label) view.lookup("#label_stat_cost");
        statName = (Label) view.lookup("#label_stat_name");
        hbox = (HBox) view.lookup("#hbox_stat");


        buttonUpgradeStats = (Button) view.lookup("#button_upgrade_stat");

        // Add action listeners
        buttonUpgradeStats.setOnAction(this::buttonUpgradeStatsOnClick);

        // Clear
        statName.setText("");
        statLvl.setText("");
        statVal.setText("");
        statCost.setText("");

        // Interaction
        if (model == model.getHero().getStats().get(0)) {
            statName.setText("Attack:");
        } else {
            statName.setText("Defence:");
        }
        statCost.setText("Cost: " + model.getCost());
        statLvl.setText("Lvl: " + model.getLevel());
        statVal.setText("Val: " + model.getValue());

        costListener = this::onCostChanged;
        levelListener = this::onLevelChanged;
        valueListener = this::onValueChanged;
        this.model.addPropertyChangeListener(HeroStat.PROPERTY_COST, this.costListener);
        this.model.addPropertyChangeListener(HeroStat.PROPERTY_LEVEL, this.levelListener);
        this.model.addPropertyChangeListener(HeroStat.PROPERTY_VALUE, this.valueListener);

    }

    public void stop() {
        buttonUpgradeStats.setOnAction(null);

        this.model.removePropertyChangeListener(HeroStat.PROPERTY_COST, this.costListener);
        this.model.removePropertyChangeListener(HeroStat.PROPERTY_LEVEL, this.levelListener);
        this.model.removePropertyChangeListener(HeroStat.PROPERTY_VALUE, this.valueListener);
    }

   // ===========================================================================================
   // Button Action Methods
   // ===========================================================================================

    private void buttonUpgradeStatsOnClick(ActionEvent actionEvent) {
        rpgEditor.heroStatUpdate(model);
    }

    private void onCostChanged(PropertyChangeEvent event) {
        statCost.setText("Cost: " + model.getCost());
    }


    private void onLevelChanged(PropertyChangeEvent event) {
        statLvl.setText("Lvl: " + model.getLevel());
    }

    private void onValueChanged(PropertyChangeEvent event) {
        statVal.setText("Val: " + model.getValue());
    }
}
