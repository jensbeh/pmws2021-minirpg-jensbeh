package de.uniks.pmws2021.controller;

import de.uniks.pmws2021.RPGEditor;
import de.uniks.pmws2021.model.Hero;
import org.fulib.FulibTools;
import org.junit.Test;

public class controllerTest {
    @Test
    public void enterControllerTest() {
        RPGEditor controller = new RPGEditor();

        Hero hero = controller.haveHero("Sir Slayalot", "normal");

        controller.enterDungeon(hero);

        FulibTools.objectDiagrams().dumpSVG("diagram/diagram.svg", hero);
    }
}