package de.uniks.pmws2021.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;

public class Hero
{
   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_MODE = "mode";
   public static final String PROPERTY_DUNGEON = "dungeon";
   public static final String PROPERTY_ATTACKING = "attacking";
   public static final String PROPERTY_STATS = "stats";
   public static final String PROPERTY_LP = "lp";
   public static final String PROPERTY_COINS = "coins";
   public static final String PROPERTY_ACTION = "action";
   private Hero copy;
   private String name;
   private String mode;
   private Dungeon dungeon;
   private Enemy attacking;
   protected PropertyChangeSupport listeners;
   private List<HeroStat> stats;
   private int lp;
   private int coins;
   private int heroIconNr;
   private int heroId;
   private boolean isAttackingThisRound;
   private boolean doAction;

   public Hero() {}

   public Hero (Hero hero) {
      this.copy = hero;
   }

   public String getName()
   {
      return this.name;
   }

   public Hero setName(String value)
   {
      if (Objects.equals(value, this.name))
      {
         return this;
      }

      final String oldValue = this.name;
      this.name = value;
      this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      return this;
   }

   public String getMode()
   {
      return this.mode;
   }

   public Hero setMode(String value)
   {
      if (Objects.equals(value, this.mode))
      {
         return this;
      }

      final String oldValue = this.mode;
      this.mode = value;
      this.firePropertyChange(PROPERTY_MODE, oldValue, value);
      return this;
   }

   public Dungeon getDungeon()
   {
      return this.dungeon;
   }

   public Hero setDungeon(Dungeon value)
   {
      if (this.dungeon == value)
      {
         return this;
      }

      final Dungeon oldValue = this.dungeon;
      if (this.dungeon != null)
      {
         this.dungeon = null;
         oldValue.setHero(null);
      }
      this.dungeon = value;
      if (value != null)
      {
         value.setHero(this);
      }
      this.firePropertyChange(PROPERTY_DUNGEON, oldValue, value);
      return this;
   }

   public Enemy getAttacking()
   {
      return this.attacking;
   }

   public Hero setAttacking(Enemy value)
   {
      if (this.attacking == value)
      {
         return this;
      }

      final Enemy oldValue = this.attacking;
      if (this.attacking != null)
      {
         this.attacking = null;
         oldValue.setAttacking(null);
      }
      this.attacking = value;
      if (value != null)
      {
         value.setAttacking(this);
      }
      this.firePropertyChange(PROPERTY_ATTACKING, oldValue, value);
      return this;
   }

   public List<HeroStat> getStats()
   {
      return this.stats != null ? Collections.unmodifiableList(this.stats) : Collections.emptyList();
   }

   public Hero withStats(HeroStat value)
   {
      if (this.stats == null)
      {
         this.stats = new ArrayList<>();
      }
      if (!this.stats.contains(value))
      {
         this.stats.add(value);
         value.setHero(this);
         this.firePropertyChange(PROPERTY_STATS, null, value);
      }
      return this;
   }

   public Hero withStats(HeroStat... value)
   {
      for (final HeroStat item : value)
      {
         this.withStats(item);
      }
      return this;
   }

   public Hero withStats(Collection<? extends HeroStat> value)
   {
      for (final HeroStat item : value)
      {
         this.withStats(item);
      }
      return this;
   }

   public Hero withoutStats(HeroStat value)
   {
      if (this.stats != null && this.stats.remove(value))
      {
         value.setHero(null);
         this.firePropertyChange(PROPERTY_STATS, value, null);
      }
      return this;
   }

   public Hero withoutStats(HeroStat... value)
   {
      for (final HeroStat item : value)
      {
         this.withoutStats(item);
      }
      return this;
   }

   public Hero withoutStats(Collection<? extends HeroStat> value)
   {
      for (final HeroStat item : value)
      {
         this.withoutStats(item);
      }
      return this;
   }

   public int getLp()
   {
      return this.lp;
   }

   public Hero setLp(int value)
   {
      if (value == this.lp)
      {
         return this;
      }

      final int oldValue = this.lp;
      this.lp = value;
      this.firePropertyChange(PROPERTY_LP, oldValue, value);
      return this;
   }

   public int getCoins()
   {
      return this.coins;
   }

   public Hero setCoins(int value)
   {
      if (value == this.coins)
      {
         return this;
      }

      final int oldValue = this.coins;
      this.coins = value;
      this.firePropertyChange(PROPERTY_COINS, oldValue, value);
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      this.listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (this.listeners != null)
      {
         this.listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getName());
      result.append(' ').append(this.getMode());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setDungeon(null);
      this.setAttacking(null);
      this.withoutStats(new ArrayList<>(this.getStats()));
   }

   public int getIconNr() {
      return this.heroIconNr;
   }

   public Hero setIconNr(int heroIconNr) {
      this.heroIconNr = heroIconNr;
      return this;
   }

   public boolean getIsAttackingThisRound() {
      return this.isAttackingThisRound;
   }

   public void setIsAttackingThisRound(boolean isAttackingThisRound) {
      this.isAttackingThisRound = isAttackingThisRound;
   }

   public boolean getDoAction() {
      return this.doAction;
   }

   public Hero setDoAction(boolean value) {
      if (value == this.doAction)
      {
         return this;
      }
      if (value) {
         final boolean oldValue = this.doAction;
         this.doAction = true;
         this.firePropertyChange(PROPERTY_ACTION, oldValue, value);
      } else {
         this.doAction = false;
      }
      return this;
   }

   public int getHeroId() {
      return heroId;
   }

   public Hero setHeroId(int heroId) {
      this.heroId = heroId;
      return this;
   }

   public Hero getCopy() {
      return copy;
   }

}
