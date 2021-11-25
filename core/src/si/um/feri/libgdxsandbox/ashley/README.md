# Ashley Framework

**Short and concise:**

* It is a libGDX extension.

* It uses Entity Component System (ECS) and avoids large class hierarchy (easy to get lost inside
  code).

* You build entities through composition instead of inheritance.

* Using "has" relationship instead of "is".

* All game objects are the same type (Entity class).

* Easier maintainable code using ECS.

* Entity is a composition of components.

* Three core classes: Entity, Component (interface without any methods), EntitySystem.

* Major benefits over object-oriented: easy to add new and complex entities and define new entities
  in data.

* Components are used for tagging and storing data. But they don't operate on that data. We describe
  some parameters of game objects with them. It means that the game object has something. Each
  component are separate practically meaningless, but with use of entities, they are very powerful.

* Entity is game object.

* Systems are used for game logic.

* Entities of some components are grouped into a family (Family class).

* Components implicitly define behaviour of our game objects.

* Engine class: center of the framework; only one instance per game.

* Entity can has only one component of the same type.

* Utility/Void systems can be used in any game. They don't do any logic.

* IteratingSystem: Family, override processEntity method (scheduled operation), override update
  method.

* Tag components/Components without data are used for tagging or marking entities.

* Order of systems is important. Otherwise, you must set priority to systems.

* IntervalSystem: execute logic every interval; override updateInterval method.

* PooledEngine: everything is pooled automatically.

* ECS is like puzzle.

* [Read Ashley's wiki for a more detailed explanation.](https://github.com/libgdx/ashley/wiki)