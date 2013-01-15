Darklight Nova: Core
====

The Darklight Nova Core installation is an open source security vulnerability simulation platform. It's similar to CyberNEXS or the CyberPatriot Scoring System, though open source & free.

The server is designed to operate on a smaller scale, potentially any number of clients sending updates to NodeJS powered web backend, which then parses the results and displays them in a variety of formats.

In addition, the client informs the user of the current state of the simulation through a Java graphical interface & generated HTML file, independently of the server. This can allow a server module to be entirely non-existent, while maintaining critical functionality.

### SDK Usage

If you are here looking to develop a module or a plugin for the Darklight Core engine, just download the SDK jar [here](https://github.com/nicatronTg/darklight-nova-core).

To start working with the Darklight SDK all you need to do is add DarklightSDK.jar to your buildpath, and you have access to the entire codebase.

##### Scoring Modules

All scoring modules you create must be in a package called com.darklight.core.scoring, are subclassed from com.ijg.darklight.sdk.core.ScoreModule, and the class name must follow the convention:

(function of the module)Module

An example of a valid module name is FirewallModule.

So, to create a scoring module:

1. Create a subclass of com.ijg.darklight.sdk.core.ScoreModule with a valid module name, in the com.darklight.core.scoring package
2. Write the your issue declarations at the top of the class
3. In your constructor you should add all of your issues to the issues ArrayList by simply calling issues.add(<issueName>)
4. Write methods to check if issues have been fixed or not
5. Write your check method using the methods you wrote above (see below, issue1IsFixed() is an example method written in step 4)
```
public ArrayList<Issue> check() {
	if (issue1IsFixed()) {
		add(issue1);
	} else {
		remove(issue1);
	}
}
```
6. Compile as a jar, with the same name as your module

##### Plugins

All plugins must be in a package called com.darklight.core.plugins, are subclassed from com.ijg.darklight.sdk.core.Plugin, and have no naming conventions.

To create a plugin:

1. Create a subclass of com.ijg.darklight.sdk.core.Plugin in the com.darklight.core.plugins package
2. Your plugin inherits protected void start() and protected void kill() from the Plugin superclass, the start method is what should initiate your plugin, and you should put anything needed to safely kill your plugin in the kill method
3. Compile as a jar, with the same name as your plugin

### Code Contributions

All code added and removed must be submitted via Pull Request. In addition, submitted code must follow the following:

* We use tabs, not spaces.
* We attempt to follow the [Java Coding Conventions](http://www.oracle.com/technetwork/java/codeconv-138413.html) as specified by Oracle.
* We prefer our functions(like this) not functions (like this) or functions ( like this ).
* Vulnerability modules should not be submitted to the core, instead, they should be linked to on the wiki on a separate Github repository.
* Added code should be documented using [JavaDoc](http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html).

We will not accept code that provides no useful benefit or is implemented in an exceptionally roundabout way that could be improved.

### Support

For the time being, submit support questions & inquiries through Github Issues, though in the future we'll be around via IRC during odd hours of the day for generic questions & getting started.