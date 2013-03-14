package cs3517.hale.mud;

import java.rmi.*;
import javax.naming.*;

/**
 * This server class instantiates a remote MUD object, registers it with
 * the naming service, and waits for MUD clients to invoke methods.
 */

public class MudServer
{
  public static void main (String[] args) throws RemoteException, NamingException
  {
    if (args.length != 3)
    {
      System.err.println("Usage: java Graph <edgesfile> <messagesfile> <thingsfile>");
      return;
    }
    System.out.println("Constructing MUD object...");
    MudImpl mudGame = new MudImpl( args[0], args[1], args[2] );

    System.out.println("Binding MUD object to rmiregistry");
    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game", mudGame);

    System.out.println("Waiting for invocations from clients...");
  }
}
