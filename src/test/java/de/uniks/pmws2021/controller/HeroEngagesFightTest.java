package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.*;
import org.junit.Assert;
import org.junit.Test;

public class HeroEngagesFightTest {

    RPGEditor controller = new RPGEditor();

    @Test
    public void testHeroEngagesFightBothDefend() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(45).setAtk(5).setDef(5).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.defence);

        controller.heroEngagesFight(Stance.defence, hero);

        Assert.assertEquals(goblin.getStance(), Stance.defence);
        Assert.assertEquals(hero.getLp(), 100);
        Assert.assertEquals(goblin.getLp(), 45);
    }

    @Test
    public void testHeroEngagesFightBothAttack() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(45).setAtk(25).setDef(5).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.attack);

        controller.heroEngagesFight(Stance.attack, hero);

        Assert.assertEquals(goblin.getStance(), Stance.attack);
        Assert.assertEquals(hero.getLp(), 75);      //Defence war mit eingerechnet, obwohl beide in Attackpose sind
        Assert.assertEquals(goblin.getLp(), 30);     //Defence war mit eingerechnet, obwohl beide in Attackpose sind
    }

    @Test
    public void testHeroEngagesFightEnemyDefendHeroAttack() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(45).setAtk(25).setDef(5).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.defence);

        controller.heroEngagesFight(Stance.attack, hero);

        Assert.assertEquals(goblin.getStance(), Stance.defence);
        Assert.assertEquals(hero.getLp(), 100);
        Assert.assertEquals(goblin.getLp(), 35);
    }

    @Test
    public void testHeroEngagesFightEnemyAttackHeroDefend() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);
        Dungeon dungeon = new Dungeon().setName("Dungeon").setHero(hero);
        Enemy goblin = new Enemy().setName("Goblin").setLp(45).setAtk(25).setDef(5).setCoins(5).setDungeon(dungeon).setAttacking(hero).setStance(Stance.attack);

        controller.heroEngagesFight("Defence", hero);

        Assert.assertEquals(goblin.getStance(), "Attack");
        Assert.assertEquals(hero.getLp(), 95);
        Assert.assertEquals(goblin.getLp(), 45);
    }

    @Test
    public void testHeroEngagesFightNullHero() {
        try {
            Hero hero = null;

            controller.heroEngagesFight(null, hero);

            Assert.fail();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHeroEngagesFightUnknownStance() {
        try {
            HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
            HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
            Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);

            controller.heroEngagesFight("", hero);

            Assert.fail();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}