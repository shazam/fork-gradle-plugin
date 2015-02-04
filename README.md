Fork Gradle Plugin
---------------

This is the gradle plugin used to run [Fork](https://github.com/shazam/fork) in your Android projects.

How to run Fork using the plugin
---------------

First, you need to add a build-script dependency:

```groovy
buildscript {
    dependencies {
        classpath 'com.shazam.fork:fork-gradle-plugin:0.10.0'
    }
}
```

Apply the Fork plugin
```groovy
apply plugin: 'fork'
```

You're now done. You can enable smart pooling by adding runtime parameters (*Pooling and other parameters* section). If you had any instrumentation test tasks before, the plugin has added Fork tasks. You can verify by running:

```groovy
gradlew tasks #(and optionally | grep fork)
```
