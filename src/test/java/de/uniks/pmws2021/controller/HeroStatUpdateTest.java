package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.*;
import org.junit.Assert;
import org.junit.Test;

public class HeroStatUpdateTest {

    RPGEditor controller = new RPGEditor();

    @Test
    public void testHeroStatUpdateNormalBehaviour() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);

        controller.heroStatUpdate(hero.getStats().get(0));

        Assert.assertEquals(hero.getStats().get(0).getLevel(), 3);
        Assert.assertEquals(hero.getCoins(), 25);
    }

    @Test
    public void testHeroStatUpdateNotEnoughCoins() {
        HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(0).withStats(attackStat, defenseStat);

        controller.heroStatUpdate(hero.getStats().get(0));

        Assert.assertEquals(hero.getStats().get(0).getLevel(), 2);
        Assert.assertEquals(hero.getCoins(), 0);
    }

    @Test
    public void testHeroStatUpdateNullStat() {
        try {
            controller.heroStatUpdate(null);

            Assert.fail();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHeroStatUpdateNullHero() {
        try {
            HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(5);

            controller.heroStatUpdate(attackStat);

            Assert.fail();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHeroStatUpdateNegativePrice() {
        try {
            HeroStat attackStat = new AttackStat().setLevel(2).setValue(15).setCost(-5);
            HeroStat defenseStat = new DefenceStat().setLevel(3).setValue(20).setCost(5);
            Hero hero = new Hero().setName("Sir Slayalot").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat, defenseStat);

            controller.heroStatUpdate(hero.getStats().get(0));

            Assert.fail();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}