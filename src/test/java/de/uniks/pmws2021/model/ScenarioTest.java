package de.uniks.pmws2021.model;
import org.junit.Test;

public class ScenarioTest
{
   @Test
   public void classDiagram()
   {
      Dungeon dungeon = new Dungeon();
      Enemy enemy = new Enemy();
      Hero hero = new Hero();
      enemy.setDungeon(dungeon);
      dungeon.setHero(hero);
   }
}
