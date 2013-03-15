#/usr/bin/sh
echo "Compiling classes..."
mvn -q compile

cd target/classes
echo $(pwd)

echo "Starting RMI Registry..."
CLASSPATH=./ rmiregistry &
sleep 1

echo "Starting the server..."
java -Djava.rmi.server.logCalls=true cs3517.hale.mud.MudServer ../../mymud.edg ../../mymud.msg ../../mymud.thg &
sleep 1

echo "Running the client..."
java cs3517.hale.mud.MudClient



