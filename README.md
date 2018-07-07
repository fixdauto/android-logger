# Logger

> FIXD Automotive's custom logger implementation

## Install

```groovy
allprojects {
    repositories {
        maven { url 'https://git.fixdapp.com/open-source/android-logger/raw/master/lib/repo' }
    }
}

dependencies {
    implementation 'com.fixdapp.android:logger:1.0.0'
}
```

## Usage

Use `LogManager` to configure and acquire loggers.
You can either build an instance yourself or use the built-in Singleton
`LogManager.getInstance()`.

You need to configure your `LogManager` with `LoggerEndpoint`s - places
logs actually go. There's `DebugLoggerEndpoint` for logging to Android
logcat.

You will need to add endpoints before you can use the logger.

```kotlin
val consoleEndpoint = DebugLoggerEndpoint(Log.DEBUG, DateFormat.getTimeInstance(DateFormat.SHORT), arrayOf("ignored-tag"))
LogManager.getInstance()
    .addEndpoints(consoleEndpoint, MyCustomServerEndpoint())
```

You can use any of the normal log levels on a logger.

```kotlin
val logger = LogManager.getInstance().getLogger()

logger.d("some debug message")
logger.e("an error occured!")
```

No need to do string formatting or interpolation, just list out items
as arguments and they will all be lazily converted to strings and
space separated.

```kotlin
logger.d("there were", count, "errors, but main cause was", myBrokenClass) // there were 7 errors, but the main cause was MyBrokenClass@6d6b15e2
```

If you have an exception, you can pass that as the first argument.

```kotlin
try {
    dangerous()
} catch (e: Exception) {
    logger.w(e, "an error occurred")
}
```

Log formats are handled on a per-endpoint basis, allowing the format to vary by destination.
Endpoints are given the tag, log level, datetime string, thread name, message, and exception. 

By default, a logger will use the file name of the code that called logger as the tag name.
You can change the tag of the logger with the `tag()` function. No need to worry about Android
tag name limit, it will truncate it if necessary.

```kotlin
val myTaggedLogger = LogManager.getInstance().getLogger().tag('custom-tag')
```

If you need to pass a logger not connected to any endpoints, there's `NoOpLogger`.

## Build

        # don't forget to upate the version first!
        ./gradlew clean build publish
