#ClassDiagram

There is a Dungeon.
There is a Enemy.
There is a Hero.

Every Dungeon has a name of type String.
Every Enemy has a name of type String.
Every Enemy has a lp of type int.
Every Enemy has a coins of type int.
Every Enemy has a atk of type int.
Every Enemy has a def of type int.
Every Enemy has a stance of type String.
Every Hero has a name of type String.
Every Hero has a lp of type int.
Every Hero has a coins of type int.
Every Hero has a mode of type String.

Enemy has dungeon and is one of the enemies of Dungeon.
Dungeon has hero and is dungeon of Hero.
Enemy has next and is previous of a Enemy.
Hero has attacking and is attacking of a Enemy.