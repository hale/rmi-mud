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
  private HashSet<MudImpl> muds;
  private static final int MAX_MUDS = 3;
  private HashSet<String> players;
  private static final int MAX_PLAYERS = 2;

  public MudManagerImpl(String edges, String messages, String things) throws
    RemoteException
  {
    this.edges = edges;
    this.messages = messages;
    this.things = things;
    this.muds = new HashSet<MudImpl>();
    this.players = new HashSet<String>();
  }

  public boolean makeMud(String name) throws RemoteException, NamingException
  {
    if (muds.size() >= MAX_MUDS) { return false; }

    System.out.println("Constructing MUD object " + name);
    MudImpl mudGame = new MudImpl( edges, messages, things );
    muds.add(mudGame);

    System.out.println("Binding MUD object to rmiregistry");
    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game_" + name, mudGame);

    System.out.println("Waiting for invocations from clients...");
    return true;
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

  public boolean addPlayer( String player )
  {
    if (players.contains( player )) return false;
    if (player.equals("")) return false;
    if (players.size() >= MAX_PLAYERS) return false;

    players.add( player );
    return true;
  }

  public Mud joinMud( String player, String name) throws RemoteException, NamingException
  {
    Context namingContext = new InitialContext();
    String url = "rmi://localhost/" + name;
    Mud mud = (Mud) namingContext.lookup( url );
    mud.addPlayer( mud.startLocation(), player );
    return mud;
  }

  public void delPlayer( String player ) throws RemoteException
  {
    players.remove( player );
  }
}
