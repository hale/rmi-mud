package cs3517.hale.mud;

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;
import java.util.*;

/**
 * Implementation of the MudManager interface.
 */
public class MudManagerImpl extends UnicastRemoteObject implements MudManager
{
  private String edges;
  private String messages;
  private String things;

  public MudManagerImpl(String edges, String messages, String things) throws
    RemoteException
  {
    this.edges = edges;
    this.messages = messages;
    this.things = things;
  }

  public void makeMud(String name) throws RemoteException, NamingException
  {
    System.out.println("Constructing MUD object " + name);
    MudImpl mudGame = new MudImpl( edges, messages, things );

    System.out.println("Binding MUD object to rmiregistry");
    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game_" + name, mudGame);

    System.out.println("Waiting for invocations from clients...");
  }

  public String printableMudList() throws RemoteException, NamingException
  {
    String list = "";
    Context namingContext = new InitialContext();
    Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
    while (e.hasMoreElements())
    {
      String mudName = e.nextElement().getName();
      if (!mudName.equals("mud_game_mud_manager"))
        list += "\t" + mudName + "\n";
    }
    return list;
  }

  public Mud getGame(String name) throws RemoteException, NamingException
  {
    Context namingContext = new InitialContext();
    String url = "rmi://localhost/" + name;
    return (Mud) namingContext.lookup( url );
  }
}
