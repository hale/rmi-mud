rmi-mud
=======

Multi-User Dungeon (MUD) game using Java RMI.

## Usage

For testing purposes, we can load classes from the classpath rather than
instructing RMI Registry to load them from a web server.

To run the server, therefore:

    mvn clean compile
    CLASSPATH=target/classes rmiregistry &
    mvn exec:java


