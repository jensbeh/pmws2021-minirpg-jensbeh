package de.uniks.pmws2021.controller.subcontroller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.Dungeon;
import de.uniks.pmws2021.model.Enemy;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.model.Stance;
import de.uniks.pmws2021.util.ResourceManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ImageHeroEnemyViewSubController {

    private PropertyChangeListener actionListener = this::onActionChanged;
    private PropertyChangeListener attackingListener = this::onAttackingChanged;

    // Variables
    private RPGEditor rpgEditor;
    private Dungeon model;
    private Parent view;

    private double initEnemyX;
    private double maxEnemyX;
    private double initHeroX;
    private double maxHeroX;
    private boolean goBack = false;
    private boolean enemyIsAttacking;
    private boolean heroIsAttacking;
    private boolean bothAttacking;
    private boolean doneEnemyAnimation;
    private boolean doneHeroAnimation;

    // Interaction
    private ImageView enemyImageView;
    private ImageView heroImageView;
    private Hero currentHero;
    private Enemy currentEnemy;

    public ImageHeroEnemyViewSubController(Dungeon model, Parent view, RPGEditor editor) {
        this.model = model;
        this.view = view;
        this.rpgEditor = editor;
    }

    // ===========================================================================================
    // Controller
    // ===========================================================================================

    public void init() {
        // Load all view references
        currentEnemy = model.getHero().getAttacking();
        currentHero = model.getHero();

        this.enemyImageView = (ImageView) view.lookup("#imageview_enemy");
        this.heroImageView = (ImageView) view.lookup("#imageview_hero");

        // Interaction
        //Load Icons
        this.enemyImageView.setImage(ResourceManager.loadIcon(currentEnemy.getName()));
        this.heroImageView.setImage(ResourceManager.loadIcon("Hero" + currentHero.getIconNr()));

        //Get Movement Values
        initEnemyX = this.enemyImageView.getX();
        maxEnemyX = heroImageView.getX() - enemyImageView.getFitWidth() + 150;

        initHeroX = this.heroImageView.getX();
        maxHeroX = enemyImageView.getX() + enemyImageView.getFitWidth() - 150;

        actionListener = this::onActionChanged;
        attackingListener = this::onAttackingChanged;
        this.currentHero.addPropertyChangeListener(Hero.PROPERTY_ACTION, this.actionListener);
        this.currentHero.addPropertyChangeListener(Hero.PROPERTY_ATTACKING, this.attackingListener);
    }

    public void stop() {
        this.currentHero.removePropertyChangeListener(Hero.PROPERTY_ACTION, this.actionListener);
    }

    private void onAttackingChanged(PropertyChangeEvent event) {
        currentEnemy = model.getHero().getAttacking();
        this.enemyImageView.setImage(ResourceManager.loadIcon(currentEnemy.getName()));
    }

    private void onActionChanged(PropertyChangeEvent event) {
        //Get who is attacking
        heroIsAttacking = currentHero.getIsAttackingThisRound();
        enemyIsAttacking = currentEnemy.getOldStance().equals(Stance.attack);

        if (!(currentHero.getLp() == 0) && (heroIsAttacking || enemyIsAttacking)) {
            attackEffect();
        } else {
            currentHero.setDoAction(false);
        }
    }

    public void attackEffect() {
        new AnimationTimer() {

            @Override
            public void handle(long l) {
                // both attacking -> set first enemy
                if (enemyIsAttacking && heroIsAttacking) {
                    bothAttacking = true;
                    heroIsAttacking = false;
                    doneEnemyAnimation = false;
                    doneHeroAnimation = false;
                }

                // enemy attacking
                if (enemyIsAttacking && !heroIsAttacking) {
                    if (goBack == false) { // Way to
                        enemyImageView.setX(enemyImageView.getX() + 4);
                        if (enemyImageView.getX() >= maxEnemyX) {
                            goBack = true;
                        }
                    } else if (goBack == true) { // Way back
                        enemyImageView.setX(enemyImageView.getX() - 4);
                        if (enemyImageView.getX() <= initEnemyX) {
                            goBack = false;
                            doneEnemyAnimation = true;
                        }
                    }
                }

                // hero attacking
                if (!enemyIsAttacking && heroIsAttacking) {
                    if (goBack == false) { // Way to
                        heroImageView.setX(heroImageView.getX() - 4);
                        if (heroImageView.getX() <= maxHeroX) {
                            goBack = true;
                        }
                    } else if (goBack == true) { // Way back
                        heroImageView.setX(heroImageView.getX() + 4);
                        if (heroImageView.getX() >= initHeroX) {
                            goBack = false;
                            doneHeroAnimation = true;
                        }
                    }
                }

                // both -> now set hero
                if (bothAttacking && doneEnemyAnimation) {
                    heroIsAttacking = true;
                    enemyIsAttacking = false;
                }

                // to stop the animation
                if ((bothAttacking && doneEnemyAnimation && doneHeroAnimation) || (!bothAttacking && doneEnemyAnimation && !doneHeroAnimation) || (!bothAttacking && !doneEnemyAnimation && doneHeroAnimation)) {
                    bothAttacking = false;
                    doneEnemyAnimation = false;
                    doneHeroAnimation = false;
                    currentHero.setDoAction(false);
                    stop();
                }
            }
        }.start();
    }
}
