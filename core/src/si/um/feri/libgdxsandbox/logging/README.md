# Logging example

## What is happening in the example?

The example is showing usage of the `com.badlogic.gdx.utils.Logger` class. The log level in the example is set to `INFO`. Be careful
that the log level setting with `Gdx.app.setLogLevel(int)` overrides the log level set inside the `Logger`.

Within the example, you can also find commented methods
of libGDX Application's life-cycle. Implementation of each method consists of one log output message. Method render is called 60
times per second by default, so for demonstrating purposes, I implemented it so that the message is printed only once every two seconds.

Try to run the example and see what the console prints. You should see something like this:

```
[LoggingExample] I am a message printed from the 'create' method. :-)
[LoggingExample] I am a message printed from the 'resize' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'resize' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'render' method. :-)
[LoggingExample] I am a message printed from the 'dispose' method. :-)
```


## Useful resources

* [libGDX Wiki (Logging)](https://github.com/libgdx/libgdx/wiki/Logging)

* [libGDX Wiki (The life cycle)](https://github.com/libgdx/libgdx/wiki/The-life-cycle)
