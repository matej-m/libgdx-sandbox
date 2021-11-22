# Tic Tac Toe game

The [TicTacToeGame](TicTacToeGame.java) class extends the `Game` class instead of `ApplicationAdapter`. In the following sections, we will briefly
explain why we are using the `Game` class, and what are `AssetManager`, `TextureAtlas`, and `Skin`.

## Scene2d

In our game, we use [Stage](https://github.com/libgdx/libgdx/wiki/Scene2d#stage) together with various
[Widgets](https://github.com/libgdx/libgdx/wiki/Scene2d#stage). In [IntroScreen](screen/IntroScreen.java)
class, we use the [Actions](https://github.com/libgdx/libgdx/wiki/Scene2d#actions) class to animate the image.

You can read more about Actors that we used in our game on links that we provided. Things are nicely and briefly
explained along with examples.

## Game, ScreenAdapter

Usually, when you do not need more screens in your game, you extend your class from the `ApplicationAdapter` class. In
this game, we use more screens so we had to extend our class from the `Game` class. The `Game` class has two extra
methods `setScreen(Screen)` and `getScreen()` whose are used to work with screens in the game.

A class that represents the screen has to extend the `ScreenAdapter` class. It has `show()` method instead of `create()`
method and is called when the screen is set. **Be careful** that the `dispose()` method is not called when you change the
screen. Instead, the method `hide()` is called. So if you have to dispose resources manually, you have to **manually**
call the `dispose()` method inside the `hide()` method.

## TextureAtlas

The `TextureAtlas` is a text file that describes images packed on a page or big image that consists of smaller images. It
is generated with [TexturePacker](../../../../../../../README.md#texturepacker).

Example of how you can use images from `TextureAtlas`. The Example below assumes that you use `AssetManager` for assets
along with `AssetDescriptors`.

```
TextureAtlas textureAtlas;
textureAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

TextureRegion backgroundRegion = textureAtlas.findRegion(RegionNames.BACKGROUND);
```


## Skin

The most convenient way to use some of the Scene2d widgets is using `Skin` class. You can find the example in `MenuScreen` class
on how to use skins. In our example we use default libGDX skin. But you can try any of the skin from this
[repository](https://github.com/czyzby/gdx-skins).

You can find more about skins on [libGDX Wiki](https://github.com/libgdx/libgdx/wiki/Skin).

## AssetManager

`AssetManager`'s task is to load all of your assets. You need **only one** AssetManager in the game. Loading of most resources is
done asynchronously. So be careful to wait until all the resources are loaded. In our example, we could implement a loading screen
if we would use many assets. But because we use only some of them, we only call method `finishLoading()` on the instance of `AssetManager`.
It blocks until all assets are loaded. If you do not wait until all assets are loaded, you will probably get `NullPointerException`.

## Useful resources

* [libGDX Wiki (Scene2d.ui)](https://github.com/libgdx/libgdx/wiki/Scene2d.ui)

* [libGDX Wiki (Scene2d)](https://github.com/libgdx/libgdx/wiki/Scene2d)

* [libGDX Wiki (TextureAtlas)](https://github.com/libgdx/libgdx/wiki/Texture-packer#textureatlas)

* [libGDX skins](https://github.com/czyzby/gdx-skins)

* [libGDX Wiki (AssetManager)](https://github.com/libgdx/libgdx/wiki/Managing-your-assets)
