package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.StageManager;
import de.uniks.pmws2021.controller.subcontroller.EnemyViewSubController;
import de.uniks.pmws2021.controller.subcontroller.HeroStatViewSubController;
import de.uniks.pmws2021.controller.subcontroller.HeroViewSubController;
import de.uniks.pmws2021.controller.subcontroller.ImageHeroEnemyViewSubController;
import de.uniks.pmws2021.model.Dungeon;
import de.uniks.pmws2021.model.Hero;
import de.uniks.pmws2021.model.HeroStat;
import de.uniks.pmws2021.util.ResourceManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class DungeonScreenController {
    private PropertyChangeListener endListener = this::onDungeonEnds;
    private PropertyChangeListener attackingListener = this::onAttackingChanged;

    // Variables
    private Dungeon model;
    private Parent view;
    private RPGEditor rpgEditor;
    private ArrayList<HeroStatViewSubController> heroStatsMemberController;

    // Controller
    private HeroViewSubController heroViewCtrl;
    private EnemyViewSubController enemyViewCtrl;
    private HeroStatViewSubController heroStatViewCtrl;
    private ImageHeroEnemyViewSubController imageHeroEnemyViewCtrl;

    // Container
    private HBox enemyViewContainer;
    private HBox heroViewContainer;
    private HBox imagesViewContainer;
    private VBox heroStatsViewContainer;

    // Interaction
    private Button buttonLeave;
    private Label labelBattleNr;
    private ImageView endImage;
    private Line lineBetweenHeroStats;

    public DungeonScreenController(Dungeon model, Parent view, RPGEditor rpgEditor) {
        this.model = model;
        this.view = view;
        this.rpgEditor = rpgEditor;
        this.heroStatsMemberController = new ArrayList<>();
    }

    public void init() {
        // Load all view references
        buttonLeave = (Button) view.lookup("#button_leave");

        enemyViewContainer = (HBox) view.lookup("#hbox_EnemyViewContainer");
        heroViewContainer = (HBox) view.lookup("#hbox_HeroViewContainer");
        heroStatsViewContainer = (VBox) view.lookup("#vbox_heroStats");
        imagesViewContainer = (HBox) view.lookup("#hbox_imagesContainer");

        labelBattleNr = (Label) view.lookup("#label_battle");
        endImage = (ImageView) view.lookup("#imageview_endImage");

        lineBetweenHeroStats = (Line) view.lookup("#line_heroStats");

        // Add action listeners
        buttonLeave.setOnAction(this::buttonLeaveOnClick);

        // Initialisation
        this.initEnemySubView();
        this.initHeroSubView();
        this.initHeroStatsSubViews();
        this.initImagesHeroEnemy();

        // Interaction
        endImage.setVisible(false);

        int currentBattleNr = model.getBattleNr();
        int totalBattleNr = model.getEnemies().size();
        labelBattleNr.setText("Battle: " + currentBattleNr + " / " + totalBattleNr);

        endListener = this::onDungeonEnds;
        attackingListener = this::onAttackingChanged;
        this.model.addPropertyChangeListener(Dungeon.PROPERTY_END, this.endListener);
        this.model.getHero().addPropertyChangeListener(Hero.PROPERTY_ATTACKING, this.attackingListener);
    }

    public void stop() {
        // Clear references
        // Clear action listeners
        buttonLeave.setOnAction(null);

        for (HeroStatViewSubController controller : this.heroStatsMemberController) {
            controller.stop();
        }
        heroViewCtrl.stop();
        enemyViewCtrl.stop();
        imageHeroEnemyViewCtrl.stop();
        this.model.removePropertyChangeListener(Dungeon.PROPERTY_END, this.endListener);
        this.model.getHero().removePropertyChangeListener(Hero.PROPERTY_ATTACKING, this.attackingListener);
    }

    // ===========================================================================================
    // Button Action Methods
    // ===========================================================================================

    private void buttonLeaveOnClick(ActionEvent actionEvent) {
        model.setBattleNr(0);

        StageManager.showHeroScreen();
    }

    // ===========================================================================================
    // Additional Methods
    // ===========================================================================================

    private void initHeroSubView() {
        // Clear
        this.heroViewContainer.getChildren().clear();

        try {
            // View
            Parent view = FXMLLoader.load(StageManager.class.getResource("subviews/HeroView.fxml"));

            // Controller
            heroViewCtrl = new HeroViewSubController(model.getHero(), view, rpgEditor);
            heroViewCtrl.init();

            // Add to Container
            this.heroViewContainer.getChildren().add(view);

        } catch (Exception e) {
            System.err.println("error on showing HeroViewSubScreen");
            e.printStackTrace();
        }
    }

    private void initEnemySubView() {
        // Clear
        this.enemyViewContainer.getChildren().clear();

        try {
            // View
            Parent view = FXMLLoader.load(StageManager.class.getResource("subviews/EnemyView.fxml"));

            // Controller
            enemyViewCtrl = new EnemyViewSubController(model.getHero().getAttacking(), view, rpgEditor);
            enemyViewCtrl.init();

            // Add to Container
            this.enemyViewContainer.getChildren().add(view);

        } catch (Exception e) {
            System.err.println("error on showing EnemyViewSubScreen");
            e.printStackTrace();
        }
    }

    private void initHeroStatsSubViews() {
        // Clear
        this.heroStatsViewContainer.getChildren().clear();

        int sizeOfStatList =this.model.getHero().getStats().size();
        int counter = 0;

        for (HeroStat stats : this.model.getHero().getStats()) {
            try {
                counter++;
                // View
                Parent view = FXMLLoader.load(StageManager.class.getResource("subviews/HeroStatView.fxml"));

                // Controller
                heroStatViewCtrl = new HeroStatViewSubController(stats, view, rpgEditor);
                heroStatViewCtrl.init();

                // Add to Container
                this.heroStatsViewContainer.getChildren().add(view);

                if (counter < sizeOfStatList) {
                    this.heroStatsViewContainer.getChildren().add(lineBetweenHeroStats);
                }

                // Put on list to stop
                this.heroStatsMemberController.add(heroStatViewCtrl);

            } catch (Exception e) {
                System.err.println("error on showing HeroStatsViewSubScreens");
                e.printStackTrace();
            }
        }
    }

    private void initImagesHeroEnemy() {
        // Clear
        this.imagesViewContainer.getChildren().clear();

        try {
            // View
            Parent view = FXMLLoader.load(StageManager.class.getResource("subviews/ImagesHeroEnemy.fxml"));

            // Controller
            imageHeroEnemyViewCtrl = new ImageHeroEnemyViewSubController(model, view, rpgEditor);
            imageHeroEnemyViewCtrl.init();

            // Add to Container
            this.imagesViewContainer.getChildren().add(view);

        } catch (Exception e) {
            System.err.println("error on showing ImageHeroEnemyViewSubScreen");
            e.printStackTrace();
        }
    }

    public void showHeroSurvivedDungeon(boolean heroSurvived) {
        // "victory" Animation!

        if (heroSurvived) {
            endImage.setImage(ResourceManager.loadIcon("victory"));
        } else {endImage.setImage(ResourceManager.loadIcon("dead"));}

        endImage.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(4000), endImage);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
        ft.setOnFinished(event -> StageManager.showHeroScreen());
    }

    private void onDungeonEnds(PropertyChangeEvent event) {
        if(model.getHero().getLp() == 0) {
            ResourceManager.deleteHero(model.getHero());
            showHeroSurvivedDungeon(false);
        }

        if ((model.getHero().getAttacking().getNext() == null) && (model.getHero().getAttacking().getLp() == 0)) {
            ResourceManager.saveHero(model.getHero());
            showHeroSurvivedDungeon(true);
        }
    }

    private void onAttackingChanged(PropertyChangeEvent event) {
        int currentBattleNr = model.getBattleNr();
        int totalBattleNr = model.getEnemies().size();
        labelBattleNr.setText("Battle: " + currentBattleNr + " / " + totalBattleNr);
    }
}
