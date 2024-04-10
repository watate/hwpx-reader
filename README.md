# hwpx-reader
This command cleans any previous compiled artifacts, compiles your project, and downloads the necessary dependencies, including hwplib.
```
mvn clean install
```

Packaging into JAR
```
mvn clean package
```

Running the application. This is possible because of this plugin: https://www.mojohaus.org/exec-maven-plugin/usage.html
```
mvn exec:java
```

Packaging the app into a .dmg
```
jpackage --type dmg --input "/Users/waltertay/Documents/Code/hwpx-reader/target" --name "SojuHWPX" --main-jar hwpx-reader-1.0.0-jar-with-dependencies.jar --main-class com.hwpxreader.app.Main --file-associations "/Users/waltertay/Documents/Code/hwpx-reader/assets/mac.properties" --icon "/Users/waltertay/Documents/Code/hwpx-reader/assets/icon.icns"
```

Packaging the app into an .exe
```
jpackage --type exe --input "C:\Users\james\Documents\VSCode\hwpx-reader\target" --name "SojuHWPX" --main-jar hwpx-reader-1.0.0-jar-with-dependencies.jar --main-class com.hwpxreader.app.Main --file-associations "C:\Users\james\Documents\VSCode\hwpx-reader\assets\win.properties" --icon "C:\Users\james\Documents\VSCode\hwpx-reader\assets\icon.ico"
```

Run application with explicit classpath
```
java --module-path "/Users/waltertay/Documents/Code/hwp-reader/mac-intel-javafx-sdk-22" --add-modules javafx.controls,javafx.fxml -cp "target/classes;target/dependency/*" org.openjfx.App
```

```
sudo /Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/jpackage --input /Users/tm/xDataStore/xSign/myFileUploader/Build --name myFileUploader --type dmg --main-jar myFileUploader.jar --main-class com.myCompany.myFileUploader.Main --icon /Users/tm/xDataStore/xSign/myFileUploader/icon.icns --mac-sign --mac-signing-key-user-name 'Developer ID Application: <removed> (<removed>)' --verbose
```

# Libraries used:
- hwpxlib: https://github.com/neolord0/hwpxlib

# Notes
- Discussion on HWPX mime types: https://bugs.astron.com/view.php?id=467
- Currently stuck because "java package" is refusing to package javaFX runtime components. Found this github issue: https://stackoverflow.com/questions/54063041/package-a-non-modular-javafx-application
- I am giving up on JavaFX and switching to Swing. Here's why: try creating a Maven project with the command here: https://openjfx.io/openjfx-docs/#maven. After that, do mvn compile and mvn package, then use jpackage to turn it into a .dmg. You'll find that the application won't even open, even though it works perfectly fine when you're just doing "mvn clean javafx:run".
- This StackOverflow seems to address how to double-click to open the .HWPX: https://stackoverflow.com/questions/10546427/java-swing-how-to-double-click-a-project-file-on-mac-to-open-my-application-an
- This StackOverflow solved the double-click to open problem: https://stackoverflow.com/questions/29101472/pass-parameters-to-javafx-application-by-double-click-on-file
- Solving "Failed to launch JVM" on Windows: https://stackoverflow.com/questions/75578985/failed-to-launch-jvm-after-using-jpackage-to-create-exe