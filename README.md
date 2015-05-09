[![Build Status](https://travis-ci.org/shazam/fork-gradle-plugin.svg?branch=master)](https://travis-ci.org/shazam/fork-gradle-plugin)

Fork Gradle Plugin
---------------

This is the gradle plugin used to run [Fork](https://github.com/shazam/fork) in your Android projects.

How to run Fork using the plugin
---------------

First, you need to add a build-script dependency:

```groovy
buildscript {
    dependencies {
        classpath 'com.shazam.fork:fork-gradle-plugin:1.0.0-RC1-SNAPSHOT'
    }
}
```

Apply the Fork plugin
```groovy
apply plugin: 'fork'
```

You're now done. You can enable smart pooling by adding runtime parameters ([Pooling and runtime parameters](https://github.com/shazam/fork#pooling-and-runtime-parameters) section). If you had any instrumentation test tasks before, the plugin has added Fork tasks. You can verify by running:

```groovy
gradlew tasks #(and optionally | grep fork)
```

Fork DSL
--------

You can specify runtime configuration for Fork with the Fork DSL. Simply add a block to your build.gradle, _e.g.,_:

```groovy
fork {
    baseOutputDir "/my_custom_dir"
}
```

Property Value     | Default value
------------------ | -------------
baseOutputDir      | "fork"
ignoreFailures     | false
testClassPattern   | ^((?!Abstract).)*Test$
testPackagePattern | // Calculated at runtime if not set to match your instrumentation package
testOutputTimeout  | 60000

*Note:* the Fork runtime parameter _android.test.classes_ is applied _after_ both the ```testClassPattern``` and ```testPackagePattern``` filters have been applied.

