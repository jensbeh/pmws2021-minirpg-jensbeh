package de.uniks.pmws2021.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HeroStat
{
   public static final String PROPERTY_LEVEL = "level";
   public static final String PROPERTY_VALUE = "value";
   public static final String PROPERTY_COST = "cost";
   public static final String PROPERTY_HERO = "hero";
   private int level;
   private int value;
   private int cost;
   private Hero hero;
   protected PropertyChangeSupport listeners;

   public int getLevel()
   {
      return this.level;
   }

   public HeroStat setLevel(int value)
   {
      if (value == this.level)
      {
         return this;
      }

      final int oldValue = this.level;
      this.level = value;
      this.firePropertyChange(PROPERTY_LEVEL, oldValue, value);
      return this;
   }

   public int getValue()
   {
      return this.value;
   }

   public HeroStat setValue(int value)
   {
      if (value == this.value)
      {
         return this;
      }

      final int oldValue = this.value;
      this.value = value;
      this.firePropertyChange(PROPERTY_VALUE, oldValue, value);
      return this;
   }

   public int getCost()
   {
      return this.cost;
   }

   public HeroStat setCost(int value)
   {
      if (value == this.cost)
      {
         return this;
      }

      final int oldValue = this.cost;
      this.cost = value;
      this.firePropertyChange(PROPERTY_COST, oldValue, value);
      return this;
   }

   public Hero getHero()
   {
      return this.hero;
   }

   public HeroStat setHero(Hero value)
   {
      if (this.hero == value)
      {
         return this;
      }

      final Hero oldValue = this.hero;
      if (this.hero != null)
      {
         this.hero = null;
         oldValue.withoutStats(this);
      }
      this.hero = value;
      if (value != null)
      {
         value.withStats(this);
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

   public void removeYou()
   {
      this.setHero(null);
   }
}
