package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.model.Stance;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class HeroViewSubController {

    private PropertyChangeListener lpListener = this::onLpChanged;
    private PropertyChangeListener attackingListener = this::onAttackingChanged;
    private PropertyChangeListener coinsListener = this::onCoinsChanged;

    // Variables
    private RPGEditor rpgEditor;
    private Parent view;
    private Hero model;

    // Interaction
    private Label labelHeroname;
    private Label labelCoins;
    private Label labelHeroCurrentHealth;
    private Label labelHeroMaxHealth;
    private Button buttonAttack;
    private Button buttonDefence;
    private ProgressBar pbHealthbarHero;

    public HeroViewSubController(Hero model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.rpgEditor = editor;
    }

   // ===========================================================================================
   // Controller
   // ===========================================================================================

    public void init() {
        // Load all view references
        labelHeroname = (Label) view.lookup("#label_heroname");
        labelCoins = (Label) view.lookup("#label_coins");
        labelHeroCurrentHealth = (Label) view.lookup("#label_HeroCurrentHealth");
        labelHeroMaxHealth = (Label) view.lookup("#label_HeroMaxHealth");
        pbHealthbarHero = (ProgressBar) view.lookup("#pb_healthbar_hero");

        buttonAttack = (Button) view.lookup("#button_atk");
        buttonDefence = (Button) view.lookup("#button_def");

        // Add action listeners
        if (!(model.getAttacking().getLp() == 0) || !(model.getLp() == 0)) { // last enemy died -> disable button for now
            buttonAttack.setOnAction(this::buttonAttackOnClick);
            buttonDefence.setOnAction(this::buttonDefenceOnClick);
        }

        // Clear
        labelHeroname.setText("");
        labelCoins.setText("");
        labelHeroCurrentHealth.setText("");
        labelHeroMaxHealth.setText("");

        // Interaction
        // Name
        labelHeroname.setText(model.getName());

        // Coins
        labelCoins.setText(String.valueOf(model.getAttacking().getAttacking().getCoins()));

        // Health
        labelHeroCurrentHealth.setText(String.valueOf(model.getLp()));
        labelHeroMaxHealth.setText("/ 100");
        updatePb();

        // Put PCL here
        lpListener = this::onLpChanged;
        attackingListener = this::onAttackingChanged;
        coinsListener = this::onCoinsChanged;

        this.model.addPropertyChangeListener(Hero.PROPERTY_LP, this.lpListener);
        this.model.addPropertyChangeListener(Hero.PROPERTY_ATTACKING, this.attackingListener);
        this.model.addPropertyChangeListener(Hero.PROPERTY_COINS, this.coinsListener);
    }

    public void stop() {
        buttonAttack.setOnAction(null);
        buttonDefence.setOnAction(null);

        this.model.removePropertyChangeListener(Hero.PROPERTY_LP, this.lpListener);
        this.model.removePropertyChangeListener(Hero.PROPERTY_ATTACKING, this.attackingListener);
        this.model.removePropertyChangeListener(Hero.PROPERTY_COINS, this.coinsListener);
    }

   // ===========================================================================================
   // Button Action Methods
   // ===========================================================================================

    private void buttonAttackOnClick(ActionEvent actionEvent) {
        if ((model.getAttacking().getLp() != 0) && (model.getLp() != 0) && (!model.getDoAction())) {

            model.setIsAttackingThisRound(true);    //for Animation
            model.setDoAction(true);    //set doAction, so the imageViewController knows that it should only act after pressing atk/def button

            rpgEditor.heroEngagesFight(Stance.attack, model);
            rpgEditor.evaluateFight(model.getAttacking(), model);
        }
    }

    private void buttonDefenceOnClick(ActionEvent actionEvent) {
        if ((model.getAttacking().getLp() != 0) && (model.getLp() != 0) && (!model.getDoAction())) {

            model.setIsAttackingThisRound(false);   //for Animation
            model.setDoAction(true);    //set doAction, so the imageViewController knows that it should only act after pressing atk/def button

            rpgEditor.heroEngagesFight(Stance.defence, model);
            rpgEditor.evaluateFight(model.getAttacking(), model);
        }
    }

    private void onLpChanged(PropertyChangeEvent event) {
        updatePb();
    }

    private void onAttackingChanged(PropertyChangeEvent event) {
        System.out.println("Enemy changed!");
    }

    private void onCoinsChanged(PropertyChangeEvent event) {
        labelCoins.setText(String.valueOf(model.getAttacking().getAttacking().getCoins()));
    }

    public void updatePb(){
        labelHeroCurrentHealth.setText(String.valueOf(model.getLp()));
        labelHeroMaxHealth.setText("/ 100");

        float viewLp = (float)(model.getLp())/100;
        int health = model.getLp();

        pbHealthbarHero.getStyleClass().clear();
        pbHealthbarHero.getStyleClass().add("progress-bar");

        if (health > 90) {
            pbHealthbarHero.getStyleClass().add("bar_100");
        } else if (health > 80) {
            pbHealthbarHero.getStyleClass().add("bar_90");
        } else if (health > 70) {
            pbHealthbarHero.getStyleClass().add("bar_80");
        } else if (health > 60) {
            pbHealthbarHero.getStyleClass().add("bar_70");
        } else if (health > 50) {
            pbHealthbarHero.getStyleClass().add("bar_60");
        } else if (health > 40) {
            pbHealthbarHero.getStyleClass().add("bar_50");
        } else if (health > 30) {
            pbHealthbarHero.getStyleClass().add("bar_40");
        } else if (health > 20) {
            pbHealthbarHero.getStyleClass().add("bar_30");
        } else if (health > 0) {
            pbHealthbarHero.getStyleClass().add("bar_20");
        }
        pbHealthbarHero.setProgress(viewLp);
    }

}
