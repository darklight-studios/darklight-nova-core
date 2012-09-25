# Darklight Nova #
==============

Basic Vulnerability Scoring Engine

Part of the Darklight Nova suite

## Live Scores

The new live score system operates by directly writing to a MySQL database. An Internet connection is required for this to succeed.

To use:

1. Ensure that your built jar includes the latest manifest and your res/ folder contains both jar files required for MySQL to connect.
2. Ensure that Engine.java contains an updated sessionid for this session.
3. Ensure that the path set for the nameFile (also located in Engine.java) is sane and can be written to.
4. Ensure that the username and password for the MySQL user are up to date and working.

Caveats:

1. Don't open WireShark.
2. Don't open WireShark.
3. Don't open WireShark.
4. Seriously.