package de.uniks.pmws2021;

import de.uniks.pmws2021.model.Dungeon;
import de.uniks.pmws2021.model.Stance;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class PropertyChangeTest extends ApplicationTest {
    private static Stage stage;
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
    public void propertyChangeTest() {
        // Add test here
        RPGEditor rpgEditor = app.getRpgEditor();

        TextField tf_heroname = lookup("#tf_heroname").query();
        tf_heroname.setText("Sir Slayalot");

        clickOn("#button_create_start");
        Dungeon dungeon = rpgEditor.getDungeon();

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

        Label statLvl = lookup("#label_stat_lvl").query();
        Assert.assertEquals(statLvl.getText(), "Lvl: " + dungeon.getHero().getStats().get(0).getLevel());

        Label statVal = lookup("#label_stat_val").query();
        Assert.assertEquals(statVal.getText(), "Val: " + dungeon.getHero().getStats().get(0).getValue());

        Label statCost = lookup("#label_stat_cost").query();
        Assert.assertEquals(statCost.getText(), "Cost: " + dungeon.getHero().getStats().get(0).getCost());

        Platform.runLater(() -> {
            dungeon.getHero().setLp(45);
            dungeon.getHero().setCoins(32);
            dungeon.getHero().getAttacking().setLp(32);
            dungeon.getHero().getAttacking().setStance(Stance.attack);
            dungeon.getHero().getAttacking().setStance(Stance.defence);
            dungeon.getHero().getAttacking().setName("Goblin");
            dungeon.getHero().getStats().get(0).setLevel(2);
            dungeon.getHero().getStats().get(0).setValue(15);
            dungeon.getHero().getStats().get(0).setCost(6);
        });
        WaitForAsyncUtils.waitForFxEvents();

        Assert.assertEquals(labelLpHero.getText(), String.valueOf(dungeon.getHero().getLp()));
        Assert.assertEquals(labelCoins.getText(), String.valueOf(dungeon.getHero().getCoins()));
        Assert.assertEquals(labelLpEnemy.getText(), String.valueOf(dungeon.getHero().getAttacking().getLp()));
        Assert.assertEquals(labelEnemynameStance.getText(), dungeon.getHero().getAttacking().getName() + ": " + dungeon.getHero().getAttacking().getStance());
        Assert.assertEquals(statLvl.getText(), "Lvl: " + dungeon.getHero().getStats().get(0).getLevel());
        Assert.assertEquals(statVal.getText(), "Val: " + dungeon.getHero().getStats().get(0).getValue());
        Assert.assertEquals(statCost.getText(), "Cost: " + dungeon.getHero().getStats().get(0).getCost());


        Platform.runLater(() -> dungeon.getHero().setAttacking(dungeon.getHero().getAttacking().getNext()));
        WaitForAsyncUtils.waitForFxEvents();

        Assert.assertEquals(labelLpEnemy.getText(), String.valueOf(dungeon.getHero().getAttacking().getLp()));
        Assert.assertEquals(labelEnemynameStance.getText(), dungeon.getHero().getAttacking().getName() + ": " + dungeon.getHero().getAttacking().getStance());
    }
}
