Darklight Nova: Core
====================

The Darklight Nova Core installation is an open source security vulnerability simulation platform. It's similar to [CyberNEXS](https://www.saic.com/cyberNEXS/) or the CyberPatriot Competition System, though open source & free.

The client informs the user of the current state of the simulation through a Java (Swing/AWT) graphical interface & generated HTML file.

There is also a NodeJS server that the clients can be configured interface with, in order to be displayed on a leaderboard for whoever would like to see the status. For more information on that, see [this](https://github.com/nicatronTg/darklight-nova) repository.

# Engine Structure

The scoring is based on the status of issues. An issue is a subclass of [com.ijg.darklight.sdk.core.Issue](https://github.com/nicatronTg/darklight-nova-core/blob/master/src/com/ijg/darklight/sdk/core/Issue.java), and their status is determined by the isFixed() function. When an issue is written, the isFixed method will determine the status of something, considered a vulnerability in the OS. For example if you're writing a Hosts file issue, the issue's isFixed method would check whether or not the Hosts file contains bad redirects, if it does, it would return false, if the bad redirects have been removed it would return true.

# SDK Usage

If you are here looking to develop an issue or a plugin, first download the SDK jar [here](https://github.com/nicatronTg/darklight-nova-core).

To start working with the Darklight SDK all you need to do is add DarklightSDK.jar to your classpath, and you have access to the entire codebase. For information on specific methods and classes, take a look at the javadoc.

# Issues

This section is in progress...

# Plugins

This section is in progress...

# Code Contributions

All code added and removed must be submitted via Pull Request. In addition, submitted code must follow the following:

* We use tabs, not spaces. (Tab size = 4)
* We attempt to follow the [Java Coding Conventions](http://www.oracle.com/technetwork/java/codeconv-138413.html) as specified by Oracle.
* We prefer our functions(like this) not functions (like this) or functions ( like this ).
* Issues and plugins should not be submitted to the core, instead, they should be linked to on the wiki on a separate Github repository.
* Added code should be documented using [JavaDoc](http://www.oracle.com/technetwork/java/javase/documentation/index-137868.html).

Any help is highly appreciated, though if submitted code appears very roundabout or innefficient, we will not hesitate to voice our concerns, or offer suggestions to improve it before merging it into the master branch.

# Support

For the time being, submit support questions & inquiries through Github Issues, though in the future we'll be around via IRC during odd hours of the day for generic questions & getting started.

# Credit
Darklight Nova Core Copyright (C) 2013 Isaac Grant

Isaac Grant is the primary author.

### Contributors
* Lucas Nicodemus