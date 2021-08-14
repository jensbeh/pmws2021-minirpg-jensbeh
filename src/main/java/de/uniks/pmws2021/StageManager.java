package de.uniks.pmws2021;

import de.uniks.pmws2021.controller.DungeonScreenController;
import de.uniks.pmws2021.controller.HeroScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager extends Application {
    //Variables
    private static Stage stage;
    private static RPGEditor rpgEditor;

    // Controller
    private static HeroScreenController heroCtrl;
    private static DungeonScreenController dungeonCtrl;

    @Override
    public void start(Stage primaryStage) {
        // start application
        stage = primaryStage;
        showHeroScreen();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        cleanup();
    }

    public static void showHeroScreen() {
        cleanup();
        // show hero screen
        try {
            //Editor
            rpgEditor = new RPGEditor();

            //View
            Parent root = FXMLLoader.load(StageManager.class.getResource("HeroScreen.fxml"));
            Scene scene = new Scene(root);

            //Controller
            heroCtrl = new HeroScreenController(root, rpgEditor);
            heroCtrl.init();

            //display
            stage.setTitle("MiniRPG - Main Menu");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
        } catch (Exception e) {
            System.err.println("error on showing HeroScreen");
            e.printStackTrace();
        }
    }

    public static void showDungeonScreen() {
        cleanup();
        // show dungeon screen
        try {
            //View
            Parent root = FXMLLoader.load(StageManager.class.getResource("DungeonScreen.fxml"));
            Scene scene = new Scene(root);

            //Controller
            dungeonCtrl = new DungeonScreenController(rpgEditor.getDungeon(), root, rpgEditor);
            dungeonCtrl.init();

            stage.setTitle("MiniRPG - Ingame");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setResizable(false);
        } catch (Exception e) {
            System.err.println("error on showing DungeonScreen");
            e.printStackTrace();
        }
    }

    private static void cleanup() {
        // call cascading stop
        if (heroCtrl != null) {
            heroCtrl.stop();
            heroCtrl = null;
        }

        if (dungeonCtrl != null) {
            dungeonCtrl.stop();
            dungeonCtrl = null;
        }
    }

    public RPGEditor getRpgEditor() {
        return rpgEditor;
    }
}