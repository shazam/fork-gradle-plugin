/*
 * Copyright 2014 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.shazam.fork.gradle

/**
 * Fork extension.
 */
class ForkExtension {

    /** Debug logging flag. */
    boolean debug

    /** Fail if no device is connected flag. */
    boolean failIfNoDeviceConnected

    /** Ignore test failures flag. */
    boolean ignoreFailures

    /** Fully qualified name of the test class to be run (e.g. com.example.foo.test.MyTestCase). */
    String className

    /** Test method to be run. Used when `className` is provided. */
    String methodName

    /** Devices to run the tests on (specified with serial numbers). */
    Set<String> devices

    /** Whether or not animations are enabled, useful for slow machines or projects with many screenshots */
    boolean noAnimations

    /** Output directory for the spoon report files. If empty, the default dir will be used */
    File baseOutputDir
}
