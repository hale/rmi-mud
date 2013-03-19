package cs3517.hale.mud;

import java.rmi.*;
import javax.naming.*;

/**
 * This server class instantiates a remote MUD object, registers it with
 * the naming service, and waits for MUD clients to invoke methods.
 */

public class MudServer
{
  private static final int GAMES_N = 3;
  public static void main (String[] args) throws RemoteException, NamingException
  {
    if (args.length != 3)
    {
      System.err.println("Usage: java Graph <edgesfile> <messagesfile> <thingsfile>");
      return;
    }

    for (int i = 0; i < GAMES_N; i++)
    {
      makeMud(i, args[0], args[1], args[2]);
    }
  }

  private static void makeMud( int i, String edges, String messages, String things )
    throws RemoteException, NamingException
  {
    System.out.println("Constructing MUD object " + i);
    MudImpl mudGame = new MudImpl( edges, messages, things );

    System.out.println("Binding MUD object to rmiregistry");
    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game_" + i, mudGame);

    System.out.println("Waiting for invocations from clients...");
  }
}
