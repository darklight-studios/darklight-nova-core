# Darklight Nova #
==============

Basic Vulnerability Scoring Engine

Part of the Darklight Nova suite

## Getting Started ##

* Start off by making a new subclass of com.ijg.darklightnova.engine.Module in the com.ijg.darklightnova.modules package, and review the ExampleScoreingModule class to see how it's designed.
* Write your Module.fixed() function
	* For the actual logic to check the vulnerability's status, make seperate, private methods that are called in fixed(); as shown in the example module
* Add your module to the module ArrayList in the constructor of com.ijg.darklightnova.engine.AssessmentModule, as the comment explains

**Done!**