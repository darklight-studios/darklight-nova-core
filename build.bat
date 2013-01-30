javac -sourcepath src -cp res/gson-2.2.2.jar -d bin src\com\ijg\darklight\sdk\core\CoreEngine.java
jar cvfm Darklight.jar Manifest.MF -C bin/ .