# Darklight Nova #
==============

Basic Vulnerability Scoring Engine

Part of the Darklight Nova suite

## Getting Started (master) ##

* Start off by making a new subclass of com.ijg.darklightnova.engine.Module in the com.ijg.darklightnova.modules package, and review the ExampleScoreingModule class to see how it's designed.
* Write your Module.fixed() function
	* For the actual logic to check the vulnerability's status, make seperate, private methods that are called in fixed(); as shown in the example module
* Add your module to the module ArrayList in the constructor of com.ijg.darklightnova.engine.AssessmentModule, as the comment explains

**Done!**

# Getting Started with the Unstable Branch #
* Scoring modules are subclassed from com.ijg.darklightnova.core.ScoreModule and are perferably placed in the com.ijg.darklightnova.modules package
* Return fixed issues from the check() method, use private methods to do the actual checking though
	* Use the add and remove ScoreModule methods to add and/or remove issues from the issue list!! Do not override these
		* These functions aren't finalized yet...
* Don't forget to add the modules.add(new MyModule()) statement for all your modules in the AssessmentModule class, **specific to the mode you are building for** (com.ijg.darklightnova.core vs com.ijg.darklightnova.live, etc)
