package cs3517.hale.mud;

import java.rmi.*;
import javax.naming.*;

/**
 * This server class instantiates a Mud Manager object and registers it with
 * the naming service. Responsibility for generating MUDs themselves is
 * delegated to the MudManager.
 *
 * @author Philip Hale - 50907446
 */

public class MudServer
{
  /**
   * Runs the server.
   *
   * @param args Usage: java Graph <edgesfile> <messagesfile> <thingsfile>")
   */
  public static void main (String[] args) throws RemoteException, NamingException
  {
    if (args.length != 3)
    {
      System.err.println("Usage: java Graph <edgesfile> <messagesfile> <thingsfile>");
      return;
    }

    MudManagerImpl mudManager = new MudManagerImpl(args[0], args[1], args[2]);
    mudManager.makeMud( "easy" );
    mudManager.makeMud( "hard" );

    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game_mud_manager", mudManager);

    System.out.println("Waiting for invocations from clients...");
  }

}
