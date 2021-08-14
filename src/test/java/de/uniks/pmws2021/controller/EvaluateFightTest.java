package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.*;
import org.junit.Assert;
import org.junit.Test;

public class EvaluateFightTest {

    RPGEditor controller = new RPGEditor();

    @Test
    public void evaluateFightEnemyDies() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(0).setAtk(5).setDef(7).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.attack);

        controller.enterDungeon(hero);
        controller.evaluateFight(goblin, hero);

        Assert.assertEquals(goblin.getLp(), 0);
        Assert.assertEquals(hero.getCoins(), 35);
    }

    @Test
    public void evaluateFightEnemySurvives() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(10).setAtk(5).setDef(7).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.attack);


        controller.evaluateFight(goblin, hero);

        assert goblin.getLp() > 0;
        Assert.assertEquals(hero.getCoins(), 30);
    }

    @Test
    public void evaluateFightHeroNormalModeHeal() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(0).setAtk(5).setDef(7).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.attack);

        controller.enterDungeon(hero);
        controller.evaluateFight(goblin, hero);

        Assert.assertEquals(goblin.getLp(), 0);
        Assert.assertEquals(hero.getLp(), 100);
    }

    @Test
    public void evaluateFightHeroHardModeHeal() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(0).setAtk(5).setDef(7).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.attack);

        controller.enterDungeon(hero);
        controller.evaluateFight(goblin, hero);

        Assert.assertEquals(goblin.getLp(), 0);
        Assert.assertNull(goblin.getNext());                 //assertNull anstelle von assertequals mit null
        Assert.assertEquals(hero.getLp(), 100);
    }

    @Test
    public void evaluateFightHeroNull() {
        try {
            Hero hero = null;
            Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
            Enemy goblin = new Enemy().setName("Goblin").setLp(30).setAtk(5).setDef(7).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.attack);

            controller.evaluateFight(goblin, hero);

            Assert.fail();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void evaluateFightEnemyNull() {
        try {
            HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
            HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
            Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
            Enemy enemy = null;

            controller.evaluateFight(enemy, hero);

            Assert.fail();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
