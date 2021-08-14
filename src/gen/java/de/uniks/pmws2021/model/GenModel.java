package de.uniks.pmws2021.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.classmodel.Clazz;
import org.fulib.builder.Type;

public class GenModel implements ClassModelDecorator
{
	@Override
	public void decorate(ClassModelManager mm)
	{
		Clazz dungeon = mm.haveClass("Dungeon");
		Clazz hero = mm.haveClass("Hero");
		Clazz enemy = mm.haveClass("Enemy");
		Clazz heroStat = mm.haveClass("HeroStat");
		Clazz attackStat = mm.haveClass("AttackStat");
		Clazz defenceStat = mm.haveClass("DefenceStat");

		mm.haveAttribute(heroStat, "level", Type.INT);
		mm.haveAttribute(heroStat, "value", Type.INT);
		mm.haveAttribute(heroStat, "cost", Type.INT);

		mm.associate(heroStat, "hero", 1, hero, "stats", Type.MANY);

		mm.haveSuper(attackStat, heroStat);
		mm.haveSuper(defenceStat, heroStat);

	}
}
