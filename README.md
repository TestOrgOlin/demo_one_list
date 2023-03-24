### Steps for running the tests

1.  Setting up android env with android studio:

    Espresso is a user interface-testing framework for testing android application developed in Java / Kotlin language using Android SDK. Therefore, espresso’s only requirement is to develop the application using Android SDK in either Java or Kotlin and it is advised to have the latest Android Studio.

    - The list of items to be configured properly before we start working in espresso framework is as follows −

    - Install latest Java JDK and configure JAVA_HOME environment variable.

    - Install latest Android Studio (version 3.2. or higher).

    - Install latest Android SDK using SDK Manager and configure ANDROID_HOME environment variable.

    - Install latest Gradle Build Tool and configure GRADLE_HOME environment variable.

2.  Start a emulator with Android studio:

    - Open device manager
    - Create new device
      - Select Pixel 2
      - Select Image S (Android 12)
    - Start emulator
      - Enter to emulator settings/about emulated devices/build number 10 rapid clicks
      - Enter system /Developer options, and scroll down to disabled:
        - Window animation scale
        - Transition animation scale
        - Animator duration scale

3.  Steps to run the tests on the emulator:

    - On the project view open app/srs/androiTests/java/new_list/New_List.kt
    - Right click and select 'run new_List()'

4.  Sample report:

    - Once the test ends running, you can find the test report in the directory: \demo_one_list\app\build\reports\androidTests\connected and open Index.html
