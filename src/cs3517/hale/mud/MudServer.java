package cs3517.hale.mud;

import java.rmi.*;
import javax.naming.*;

/**
 * This server class instantiates a Mud Manager object and registers it with
 * the naming service.
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

    MudManagerImpl mudManager = new MudManagerImpl();
    mudManager.makeMud( "easy", args[0], args[1], args[2] );
    mudManager.makeMud( "hard", args[0], args[1], args[2] );

    System.out.println("Binding MUD object to rmiregistry");
    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game_mud_manager", mudManager);

    System.out.println("Waiting for invocations from clients...");
  }

}
