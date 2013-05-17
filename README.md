CS3517 MUD Assesment - Philip Hale - 50907446
=======

Multi-User Dungeon (MUD) game using Java RMI.

I have used Maven to build the files, and these instructions assume the use of
Maven.  However, the files should also compile through other means (e.g. using
an IDE), but the paths in the usage instructions below will change.

## File List

    .
    ├── README.md
    ├── mymud.edg
    ├── mymud.msg
    ├── mymud.thg
    ├── pom.xml
    └── src
      └── cs3517
        └── hale
          └── mud
            ├── Edge.java
            ├── Mud.java
            ├── MudClient.java
            ├── MudImpl.java
            ├── MudManager.java
            ├── MudManagerImpl.java
            ├── MudServer.java
            └── Vertex.java

    4 directories, 13 files

## Commit History

    * (HEAD, origin/master, origin/HEAD, master) javadoc and code formatting
    * (origin/cas18, cas18) limit number of users connected per MUD instance
    * Revert "limit number of users connected to any dungeon"
    * (origin/universal-user-limit, universal-user-limit) limit number of users connected to any dungeon
    * (server-mud-limit) restrict the number of MUDs running at a time
    * improved and fix javadoc
    * players prompted to create a new mud when connecting
    * simplified ask-for-name loop
    * mudmanager now responsible for fetching the remote mud object
    * new mudmanager class to handle mud game creation
    * better mudGame names
    * removed done TODO
    * (origin/cas15, cas15) client asked to choose which MUD to join on the server
    * server creates multiple MUDs on start
    * (origin/cas12, cas12) implemented pick up item
    * fixed case sensitive naming issue in git
    * added structure for the pickupItem() method
    * improved the game loop, added help text
    * type look for a description of the current location
    * (origin/cas9, cas9) Player can move around the sample dungeon
    * added script to compile, run rmiregistry, run server, run client
    * updated readme with instructions how to run server
    * made all public methods on the remote object interface throw remoteexeception
    * setup the MUD to use RMI - boilerplate structure from Core Java
    * fixed compiler warnings: removed redundant casts
    * fixed formatting
    * added given starting files + pom
    * Initial commit

## Usage

    # compile the sources
    mvn clean compile
    # generate javadoc
    mvn javadoc:javadoc
    # run rmiregistry
    CLASSPATH=target/classes rmiregistry
    # run the server in a seperate terminal
    cd target/classes
    java cs3517.hale.mud.MudServer \
    ../../mymud.edg \
    ../../mymud.msg \
    ../../mymud.thg
    # run the client in a seperate terminal
    java cs3517.hale.mud.MudClient

## Architecture

I considered splitting the server and client side code into separate packages.
However, I stuck with the simplicity of having classes in the same package
since I knew the client and server code would be run from the same host.
Similarly, I decided to use rmiregistry along with a CLASSPATH environment
variable so that hosting the classes on a separate HTTP server was not
necessarily.  Were this project to be deployed in a production environment, it
would be necessary for the naming server to dynamically retrieve the required
classes from a URL rather than have them autoloaded in the classpath.

In order to keep the system as distributed and therefore scalable as possible,
I wanted to avoid creating data structures to hold the MUD instances.  When a
client joins a MUD, it does this by proxy - in other words, it requests the
MudManager to fetch a reference to a MUD which is also a remote object. This
allows the MudManager class to be kept simple.

The MudManager class was introduced in order to fulfill the requirement that
clients can create new MUD instances at runtime.  Prior to this feature's
implementation, clients connected directly to the remote MUD object.

Implementing the MudManager was a simple case of moving the MUD-connecting code
out of the client, and replacing it with a connection to the MudManager.

The basic structure of the RMI classes was taken from Core Java Volume 2:
Advanced Features by Cay S. Horstmann and Gary Cornell, 8th ed.

The basic structure of the MUD game was taken from the supplied files.  The
Vertex and Edge classes were unchanged, as were the dungeon definition files.

## Functionality

Specified requirements up to and including those in the CAS18-20 bracket were
implemented.  Namely:

* Client can invoke methods on the remote object.
* Player can make at least one move in at least one direction
* Display information about the change of location

* Users can move around in any direction in your MUD world
* Users can see other users in the MUD
* Users can pick up things in the MUD

* Server generates more than one instance of the remote MUD object
* Users can see which muds are running on the server.
* Users can join a specific mud instance.

* Users can create a new mud instance at runtime.
* Server restricts the total number of Muds running.
* Mud instance restricts number of logged-on players.

In addition, the following features were implemented:

* Display help text to users with possible actions they can take.
* When quitting the game, items the user picked up are returned to the game.
* Users cannot pick up other players or themselves.
* Users can see a list of items they have picked up.
* Users cannot have the same name.

## Limitations

* Users cannot drop items they have picked up unless they leave the game.
* The game does not persist user state after they disconnect.
* Uses must quit cleanly (by typing `exit`) for the game to recollect their items.

## Next steps

Before fulfilling any new requirements, I would first like to abstract the
user-interaction functionality into a separate class.  In order to support more
complex functionality, the codebase would benefit from separating the game
logic from the user interaction.

Assuming I was in control of setting the requirements, an obvious next step
would be to handle unclean disconnects from the server - for example, if the
network connection is interrupted.
