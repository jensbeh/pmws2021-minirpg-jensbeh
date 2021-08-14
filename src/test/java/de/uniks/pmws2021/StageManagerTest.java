package de.uniks.pmws2021;

import de.uniks.pmws2021.model.Dungeon;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class StageManagerTest extends ApplicationTest {
    private Stage stage;
    private StageManager app;

    @Override
    public void start(Stage stage) {
        // start application
        this.stage = stage;
        app = new StageManager();
        app.start(stage);
        this.stage.centerOnScreen();
    }

    @Test
    public void changeViewTest() {

        Assert.assertEquals("MiniRPG - Main Menu", stage.getTitle());

        TextField tf_heroname = lookup("#tf_heroname").query();
        tf_heroname.setText("Sir Slayalot");
        String heroName = tf_heroname.getText();

        clickOn("#button_create_start");

        Assert.assertEquals("MiniRPG - Ingame", stage.getTitle());

        Label label_heroname = lookup("#label_heroname").query();
        Assert.assertEquals(heroName, label_heroname.getText());

        clickOn("#button_leave");

        Assert.assertEquals("MiniRPG - Main Menu", stage.getTitle());

        tf_heroname = lookup("#tf_heroname").query();
        Assert.assertEquals("", tf_heroname.getText());
    }

    @Test
    public void checkViewTest() {
        RPGEditor editor = this.app.getRpgEditor();

        TextField tf_heroname = lookup("#tf_heroname").query();
        tf_heroname.setText("Sir Slayalot");

        clickOn("#button_create_start");
        Dungeon dungeon = editor.getDungeon();

        Label labelDungeonname = lookup("#label_dungeonname").query();
        Assert.assertEquals(labelDungeonname.getText(), dungeon.getName());

        Label labelBattle = lookup("#label_battle").query();
        Assert.assertEquals(labelBattle.getText(), "Battle: " + dungeon.getBattleNr() + " / " + dungeon.getEnemies().size());

        Label labelLpEnemy = lookup("#label_EnemyCurrentHealth").query();
        Assert.assertEquals(labelLpEnemy.getText(), String.valueOf(dungeon.getHero().getAttacking().getLp()));

        Label labelEnemynameStance = lookup("#label_enemyname_stance").query();
        Assert.assertEquals(labelEnemynameStance.getText(), dungeon.getHero().getAttacking().getName() + ": " + dungeon.getHero().getAttacking().getStance());

        Label labelLpHero = lookup("#label_HeroCurrentHealth").query();
        Assert.assertEquals(labelLpHero.getText(), String.valueOf(dungeon.getHero().getLp()));

        Label labelCoins = lookup("#label_coins").query();
        Assert.assertEquals(labelCoins.getText(), String.valueOf(dungeon.getHero().getCoins()));

    }
}