package de.uniks.pmws2021.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;

public class Dungeon
{
   public static final String PROPERTY_NAME = "name";
   public static final String PROPERTY_ENEMIES = "enemies";
   public static final String PROPERTY_HERO = "hero";
   public static final String PROPERTY_END = "end";
   private String name;
   private List<Enemy> enemies;
   private Hero hero;
   private int currentBattleNr = 1;

   private boolean dungeonEnds = false;
   protected PropertyChangeSupport listeners;

   public String getName()
   {
      return this.name;
   }

   public Dungeon setName(String value)
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

   public List<Enemy> getEnemies()
   {
      return this.enemies != null ? Collections.unmodifiableList(this.enemies) : Collections.emptyList();
   }

   public Dungeon withEnemies(Enemy value)
   {
      if (this.enemies == null)
      {
         this.enemies = new ArrayList<>();
      }
      if (!this.enemies.contains(value))
      {
         this.enemies.add(value);
         value.setDungeon(this);
         this.firePropertyChange(PROPERTY_ENEMIES, null, value);
      }
      return this;
   }

   public Dungeon withEnemies(Enemy... value)
   {
      for (final Enemy item : value)
      {
         this.withEnemies(item);
      }
      return this;
   }

   public Dungeon withEnemies(Collection<? extends Enemy> value)
   {
      for (final Enemy item : value)
      {
         this.withEnemies(item);
      }
      return this;
   }

   public Dungeon withoutEnemies(Enemy value)
   {
      if (this.enemies != null && this.enemies.remove(value))
      {
         value.setDungeon(null);
         this.firePropertyChange(PROPERTY_ENEMIES, value, null);
      }
      return this;
   }

   public Dungeon withoutEnemies(Enemy... value)
   {
      for (final Enemy item : value)
      {
         this.withoutEnemies(item);
      }
      return this;
   }

   public Dungeon withoutEnemies(Collection<? extends Enemy> value)
   {
      for (final Enemy item : value)
      {
         this.withoutEnemies(item);
      }
      return this;
   }

   public Hero getHero()
   {
      return this.hero;
   }

   public Dungeon setHero(Hero value)
   {
      if (this.hero == value)
      {
         return this;
      }

      final Hero oldValue = this.hero;
      if (this.hero != null)
      {
         this.hero = null;
         oldValue.setDungeon(null);
      }
      this.hero = value;
      if (value != null)
      {
         value.setDungeon(this);
      }
      this.firePropertyChange(PROPERTY_HERO, oldValue, value);
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
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutEnemies(new ArrayList<>(this.getEnemies()));
      this.setHero(null);
   }

   public int getBattleNr() {
      return currentBattleNr;
   }

   public void setBattleNr(int currentBattleNr) {
      this.currentBattleNr = currentBattleNr;
   }

   public boolean setDungeonEnds(boolean value) {
      if (this.dungeonEnds == value)
      {
         return this.dungeonEnds;
      }

      final boolean oldValue = this.dungeonEnds;
      this.dungeonEnds = value;
      if (value == true) {
         this.firePropertyChange(PROPERTY_END, oldValue, value);
      }
      return this.dungeonEnds;
   }
}
