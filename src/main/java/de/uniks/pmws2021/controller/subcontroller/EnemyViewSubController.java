package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.model.Hero;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EnemyViewSubController {

    private PropertyChangeListener nameListener = this::onNameChanged;
    private PropertyChangeListener lpListener = this::onLpChanged;
    private PropertyChangeListener stanceListener = this::onStanceChanged;
    private PropertyChangeListener attackingListener = this::onAttackingChanged;

    // Variables
    private RPGEditor rpgEditor;
    private Enemy model;
    private Parent view;

    // Interaction
    private Label labelEnemynameStance;
    private Label labelEnemyCurrentHealth;
    private Label labelEnemyMaxHealth;
    private ProgressBar pbHealthbarEnemy;

    public EnemyViewSubController(Enemy model, Parent view, RPGEditor rpgEditor) {
        this.model = model;
        this.view = view;
        this.rpgEditor = rpgEditor;
    }

   // ===========================================================================================
   // Controller
   // ===========================================================================================

    public void init() {
        // Load all view references
        labelEnemynameStance = (Label) view.lookup("#label_enemyname_stance");
        labelEnemyCurrentHealth = (Label) view.lookup("#label_EnemyCurrentHealth");
        labelEnemyMaxHealth = (Label) view.lookup("#label_EnemyMaxHealth");
        pbHealthbarEnemy = (ProgressBar) view.lookup("#pb_healthbar_enemy");


        // Interaction
        // Name
        labelEnemynameStance.setText(model.getName() + ": " + model.getStance());

        // Health
        updatePb();

        // PCL
        nameListener = this::onNameChanged;
        lpListener = this::onLpChanged;
        stanceListener = this::onStanceChanged;
        attackingListener = this::onAttackingChanged;
        this.model.addPropertyChangeListener(Enemy.PROPERTY_NAME, this.nameListener);
        this.model.addPropertyChangeListener(Enemy.PROPERTY_LP, this.lpListener);
        this.model.addPropertyChangeListener(Enemy.PROPERTY_STANCE, this.stanceListener);
        this.model.getAttacking().addPropertyChangeListener(Hero.PROPERTY_ATTACKING, this.attackingListener);
    }

    public void stop() {

        this.model.removePropertyChangeListener(Enemy.PROPERTY_NAME, this.nameListener);
        this.model.removePropertyChangeListener(Enemy.PROPERTY_LP, this.lpListener);
        this.model.removePropertyChangeListener(Enemy.PROPERTY_STANCE, this.stanceListener);
    }

    private void onNameChanged(PropertyChangeEvent event) {
        System.out.println("Enemy name changed!");

        labelEnemynameStance.setText(model.getName() + ": " + model.getStance());
    }

    private void onLpChanged(PropertyChangeEvent event) {
        updatePb();
    }

    private void onStanceChanged(PropertyChangeEvent event) {
        model.setOldStance(model.getStance()); //for animation safe the old Stance
        labelEnemynameStance.setText(model.getName() + ": " + model.getStance());
    }

    private void onAttackingChanged(PropertyChangeEvent event) {
        stop();
        model = model.getNext();
        if (model != null) {
            this.model.addPropertyChangeListener(Enemy.PROPERTY_LP, this.lpListener);
            this.model.addPropertyChangeListener(Enemy.PROPERTY_STANCE, this.stanceListener);

            labelEnemynameStance.setText(model.getName() + ": " + model.getStance());
            updatePb();
        }
    }

    public void updatePb(){
        labelEnemyCurrentHealth.setText(String.valueOf(model.getLp()));
        labelEnemyMaxHealth.setText("/ "+ model.getMaxLp());

        float viewLp = (float)(model.getLp())/ model.getMaxLp();
        float health = (float)(model.getLp()) / (float)(model.getMaxLp());

        pbHealthbarEnemy.getStyleClass().clear();
        pbHealthbarEnemy.getStyleClass().add("progress-bar");

        if (health > 0.9) {
            pbHealthbarEnemy.getStyleClass().add("bar_100");
        } else if (health > 0.8) {
            pbHealthbarEnemy.getStyleClass().add("bar_90");
        } else if (health > 0.7) {
            pbHealthbarEnemy.getStyleClass().add("bar_80");
        } else if (health > 0.6) {
            pbHealthbarEnemy.getStyleClass().add("bar_70");
        } else if (health > 0.5) {
            pbHealthbarEnemy.getStyleClass().add("bar_60");
        } else if (health > 0.4) {
            pbHealthbarEnemy.getStyleClass().add("bar_50");
        } else if (health > 0.3) {
            pbHealthbarEnemy.getStyleClass().add("bar_40");
        } else if (health > 0.2) {
            pbHealthbarEnemy.getStyleClass().add("bar_30");
        } else if (health > 0.0) {
            pbHealthbarEnemy.getStyleClass().add("bar_20");
        }
        pbHealthbarEnemy.setProgress(viewLp);
    }

}
