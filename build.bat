javac -sourcepath src -cp res/gson-2.2.2.jar -d bin src\com\ijg\darklight\sdk\core\CoreEngine.java
jar cvfm DarklightSDK.jar Manifest.MF -C bin/ .
jar cvf DarklightSDK-javadoc.jar -C doc/ .
javac -sourcepath src -cp res/gson-2.2.2.jar -d bin src\com\ijg\darklight\build\DarklightInstaller.java
jar cvfm DarklightInstaller.jar InstallerManifest.MF -C bin/ .