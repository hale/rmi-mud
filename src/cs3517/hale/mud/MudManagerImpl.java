package cs3517.hale.mud;

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;
import java.util.ArrayList;

/**
 * Implementation of the MudManager interface.
 */
public class MudManagerImpl extends UnicastRemoteObject implements MudManager
{
  public MudManagerImpl() throws RemoteException { }

  public void makeMud(String name, String edges, String messages, String things )
    throws RemoteException, NamingException
  {
    System.out.println("Constructing MUD object " + name);
    MudImpl mudGame = new MudImpl( edges, messages, things );

    System.out.println("Binding MUD object to rmiregistry");
    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game_" + name, mudGame);

    System.out.println("Waiting for invocations from clients...");
  }
}
