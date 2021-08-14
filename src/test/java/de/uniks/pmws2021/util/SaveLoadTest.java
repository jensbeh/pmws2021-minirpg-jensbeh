package de.uniks.pmws2021.util;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SaveLoadTest {
    private static final Path HERO_FOLDER = Path.of("saves");
    private static final Path HERO_FILE = HERO_FOLDER.resolve("heroes.yaml");

    @Test
    public void saveLoadTest() {
        // Add test here
        try {
            Files.deleteIfExists(HERO_FILE);
        } catch (Exception e) {
            System.err.println("Error while loading " + HERO_FILE);
            e.printStackTrace();
        }

        RPGEditor rpgEditor = new RPGEditor();

        HeroStat attackStat1 = new AttackStat().setLevel(2).setValue(15).setCost(5);
        HeroStat defenseStat1 = new DefenceStat().setLevel(3).setValue(20).setCost(5);
        Hero hero1 = new Hero().setName("Hero 1").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat1, defenseStat1).setIconNr(1);

        HeroStat attackStat2 = new AttackStat().setLevel(5).setValue(30).setCost(8);
        HeroStat defenseStat2 = new DefenceStat().setLevel(4).setValue(25).setCost(8);
        Hero hero2 = new Hero().setName("Hero 2").setMode(Mode.normal).setLp(100).setCoins(30).withStats(attackStat2, defenseStat2).setIconNr(1);

        ResourceManager.saveHero(hero1);
        ResourceManager.saveHero(hero2);

        Assert.assertTrue(Files.exists(HERO_FILE));

        List<Hero> heroes = ResourceManager.loadAllHeroes();

        Assert.assertEquals(heroes.size(), 2);

        Hero heroNew1;
        Hero heroNew2;

        if (hero1.getName().equals(heroes.get(0).getName())) {
            heroNew1 = heroes.get(0);
            heroNew2 = heroes.get(1);
        } else {
            heroNew1 = heroes.get(1);
            heroNew2 = heroes.get(0);
        }

        Assert.assertEquals(hero1.getName(), heroNew1.getName());
        Assert.assertEquals(hero1.getMode(), heroNew1.getMode());
        Assert.assertEquals(hero1.getLp(), heroNew1.getLp());
        Assert.assertEquals(hero1.getCoins(), heroNew1.getCoins());
        Assert.assertEquals(hero1.getStats().get(0).getLevel(), heroNew1.getStats().get(0).getLevel());
        Assert.assertEquals(hero1.getStats().get(0).getCost(), heroNew1.getStats().get(0).getCost());
        Assert.assertEquals(hero1.getStats().get(0).getValue(), heroNew1.getStats().get(0).getValue());
        Assert.assertEquals(hero1.getStats().get(1).getLevel(), heroNew1.getStats().get(1).getLevel());
        Assert.assertEquals(hero1.getStats().get(1).getCost(), heroNew1.getStats().get(1).getCost());
        Assert.assertEquals(hero1.getStats().get(1).getValue(), heroNew1.getStats().get(1).getValue());


        Assert.assertEquals(hero2.getName(), heroNew2.getName());
        Assert.assertEquals(hero2.getMode(), heroNew2.getMode());
        Assert.assertEquals(hero2.getLp(), heroNew2.getLp());
        Assert.assertEquals(hero2.getCoins(), heroNew2.getCoins());
        Assert.assertEquals(hero2.getStats().get(0).getLevel(), heroNew2.getStats().get(0).getLevel());
        Assert.assertEquals(hero2.getStats().get(0).getCost(), heroNew2.getStats().get(0).getCost());
        Assert.assertEquals(hero2.getStats().get(0).getValue(), heroNew2.getStats().get(0).getValue());
        Assert.assertEquals(hero2.getStats().get(1).getLevel(), heroNew2.getStats().get(1).getLevel());
        Assert.assertEquals(hero2.getStats().get(1).getCost(), heroNew2.getStats().get(1).getCost());
        Assert.assertEquals(hero2.getStats().get(1).getValue(), heroNew2.getStats().get(1).getValue());
    }
}
