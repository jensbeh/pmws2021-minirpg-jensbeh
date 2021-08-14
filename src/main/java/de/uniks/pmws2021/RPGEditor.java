package de.uniks.pmws2021;

import de.uniks.pmws2021.model.*;

import java.util.*;


public class RPGEditor {
   //Variables
   private static final int RAISE_FACTOR = 2;
   private int heroIconNr = 1;

   // Editor lookup lists
   private List<Hero> heroes = new ArrayList<>();
   
   // Connection to model root object
   private Dungeon dungeon;


   // ===========================================================================================
   // Hero
   // ===========================================================================================

   // Create Hero
   public Hero haveHero(String heroName, String hardMode) {
      int maxId = 0;

      if (!this.heroes.isEmpty()) {
         for (Hero hero : heroes) {
            if (hero.getName().equals(heroName)) {
               return hero;
            }
            if (hero.getHeroId() > maxId) {maxId = hero.getHeroId();}
         }
      }

      HeroStat attack = new AttackStat().setLevel(1).setValue(8).setCost(3);
      HeroStat defence = new DefenceStat().setLevel(1).setValue(10).setCost(3);
      Hero newHero = new Hero().setName(heroName).setMode(hardMode).setLp(100).setCoins(100).withStats(attack, defence).setIconNr(heroIconNr);
      maxId++;
      newHero.setHeroId(maxId);
      heroes.add(newHero);

      return newHero;
   }

   // ===========================================================================================
   // Enemy
   // ===========================================================================================
   
   // If you want you can change this method and its parameter.
   // For example: you can implement a random mechanism that generates enemies by a defined set of enemies
   // Or you keep it as fixed as it is now

   // Create Enemy
   public Enemy haveEnemy(String name, int lp, int coins, int atk, int def, String stance) {
      return new Enemy().setName(name).setLp(lp).setCoins(coins).setAtk(atk).setDef(def).setStance(stance).setOldStance(stance).setMaxLp(lp);
   }

   // ===========================================================================================
   // Dungeon
   // ===========================================================================================

   // create Dungeon
   public Dungeon haveDungeon(String dungeonName, Hero hero, List<Enemy> enemies) {
      return new Dungeon().setName(dungeonName).setHero(hero).withEnemies(enemies);
   }


   // ===========================================================================================
   // Game Logic
   // ===========================================================================================

   public void enterDungeon(Hero hero) {

      // Generate random enemies
      Random rand = new Random();
      List<Enemy> enemiesList = new ArrayList<>();
      String[] enemyNameArray = {"Dragon", "Bat", "Lion", "Wolf"};
      String[] stanceArray = {Stance.attack, Stance.defence};

      for (int i = 0; i < rand.nextInt(6) + 2; i++) { // 2- 8 enemies
         String enemyName = enemyNameArray[rand.nextInt(enemyNameArray.length)];
         int lp = rand.nextInt(60)+15;    // 15 - 75
         int coins = rand.nextInt(10)+5;  // 5 -15
         int atk = rand.nextInt(20)+5;    //5 - 25
         int def = rand.nextInt(20)+5;   //5 - 25
         String stance = stanceArray[rand.nextInt(stanceArray.length)];

         enemiesList.add(haveEnemy(enemyName, lp, coins, atk, def, stance));
      }

      // Give every enemy its previously
      hero.setAttacking(enemiesList.get(0));
      for (int i = 1; i < enemiesList.size(); i++) {
         enemiesList.get(i).setPrevious(enemiesList.get(i-1));
      }

      // Create complete Dungeon
      dungeon = haveDungeon("Chamber of R'leyh", hero, enemiesList);
   }


   // Update HeroStats
   public void heroStatUpdate(HeroStat heroStat) {

      // Check if Hero/Enemy == null
      Objects.requireNonNull(heroStat);
      Objects.requireNonNull(heroStat.getHero());

      // Variables
      Hero hero = heroStat.getHero();
      int coins = hero.getCoins();

      // Upgrade Stats
      if (coins > heroStat.getCost()) {
         hero.setCoins(coins - heroStat.getCost());
         heroStat.setLevel(heroStat.getLevel() + 1);
         heroStat.setValue(heroStat.getValue() * RAISE_FACTOR);
         heroStat.setCost(heroStat.getCost() * RAISE_FACTOR);
      }

      // Costs negative
      if (heroStat.getCost() < 0) {
         throw new IllegalArgumentException("Costs are negative!");
      }
   }


   // Calculating values in fight
   public void heroEngagesFight(String heroStance, Hero hero) {

      // Check if Hero/HeroStance == null
      Objects.requireNonNull(hero, heroStance);

      // Variables
      Optional<HeroStat> heroStatAtk = hero.getStats().stream().filter(stat -> stat instanceof AttackStat).findFirst();
      AttackStat attackStat = (AttackStat) heroStatAtk.get();

      Optional<HeroStat> heroStatDef = hero.getStats().stream().filter(stat -> stat instanceof DefenceStat).findFirst();
      DefenceStat defenceStat = (DefenceStat) heroStatDef.get();

      Enemy enemy = hero.getAttacking();

      // If Hero still alive
      if (hero.getLp() > 0) {
         if (!(heroStance.equals(Stance.attack)) && !(heroStance.equals(Stance.defence))) {                  //Stringvergleich mir equals falsch gemacht und nun korrigiert
            throw new IllegalArgumentException("heroStance has wrong Argument");
         } else {
            if ((heroStance.equals(Stance.attack)) && (enemy.getStance().equals(Stance.attack))) {          //Stringvergleich mir equals falsch gemacht und nun korrigiert
               enemy.setLp(enemy.getLp() - attackStat.getValue());
               hero.setLp(hero.getLp() - enemy.getAtk());                                                   //Defence von Hero war mit eingerechnet
            } else if ((heroStance.equals(Stance.attack)) && (enemy.getStance().equals(Stance.defence))) {  //Stringvergleich mir equals falsch gemacht und nun korrigiert
               if (attackStat.getValue() - enemy.getDef() > 0) {
                  enemy.setLp(enemy.getLp() - attackStat.getValue() + enemy.getDef());
               }
            } else if ((heroStance.equals(Stance.defence)) && (enemy.getStance().equals(Stance.attack))) {  //Stringvergleich mir equals falsch gemacht und nun korrigiert
               if (enemy.getAtk() - defenceStat.getValue() > 0) {
                  hero.setLp(hero.getLp() + defenceStat.getValue() - enemy.getAtk());
               }
            }
         }
      }

      // if Hero is dead
      if (hero.getLp() < 0) {
         hero.setLp(0);
         dungeon.setDungeonEnds(true);
      }

      // if Enemy is dead
      if (enemy.getLp() < 0) {
         enemy.setLp(0);
      }
   }


   // Calculating next Step
   public void evaluateFight(Enemy enemy, Hero hero) {

      // Check if Enemy/Hero == null
      Objects.requireNonNull(hero);
      Objects.requireNonNull(enemy);

      // Both still alive
      if ((hero.getLp() != 0) && (enemy.getLp() != 0)) {
         Random random = new Random();
         if (!random.nextBoolean()) {
            enemy.setStance(Stance.defence);
         } else {
            enemy.setStance(Stance.attack);
         }
         // Enemy is dead
      } else if ((hero.getLp() != 0) && (enemy.getLp() == 0)) {
         hero.setCoins(hero.getCoins() + enemy.getCoins());

         // Still enemies remaining -> next Enemy
         if (enemy.getNext() != null) {
            dungeon.setBattleNr(dungeon.getBattleNr() + 1);
            hero.setAttacking(enemy.getNext());
         }
         // heal in normal mode
         if (hero.getMode().equals(Mode.normal)) {
            hero.setLp(100);
         }
         // end - hero survived
         if (enemy.getNext() == null) {
            hero.setLp(100);
            dungeon.setDungeonEnds(true);
         }
      }
   }

   public Dungeon getDungeon() {
      return this.dungeon;
   }

   public void setLoadedHeroes(List<Hero> heroes) {
      this.heroes = heroes;
   }

   public List<Hero> getLoadedHeroes() {
      return heroes;
   }

   public void setHeroIconNr(int heroIconNr) {
      this.heroIconNr = heroIconNr;
   }


}
