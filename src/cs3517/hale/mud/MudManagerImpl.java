package cs3517.hale.mud;

import java.rmi.*;
import java.rmi.server.*;
import javax.naming.*;
import java.util.*;

/**
 * Implementation of the MudManager interface.
 *
 * This MudManager is quite ignorant of the MUDs it creates - it knows only
 * their names by querying the naming server and how many it has created
 *
 * @author Philip Hale - 50907446
 */
public class MudManagerImpl extends UnicastRemoteObject implements MudManager
{
  private String edges;
  private String messages;
  private String things;

  /* Set of muds managed by this instance. */
  private HashSet<MudImpl> muds;

  private static final int MAX_MUDS = 3;

  /**
   * Muds created by the same mud manager have the same dungeon.
   *
   * @param edges Pathname for an edges file (*.edg)
   * @param messages Pathname for an edges file (*.msg)
   * @param things Pathname for an edges file (*.thg)
   */
  public MudManagerImpl(String edges, String messages, String things) throws
    RemoteException
  {
    this.edges = edges;
    this.messages = messages;
    this.things = things;
    this.muds = new HashSet<MudImpl>();
  }

  /**
   * Creates a new MUD instance and binds it to the naming service.
   *
   * @param name A unique name for this mud.
   */
  public boolean makeMud(String name) throws RemoteException, NamingException
  {
    if (muds.size() >= MAX_MUDS) { return false; }

    MudImpl mudGame = new MudImpl( edges, messages, things );
    muds.add(mudGame);

    Context namingContext = new InitialContext();
    namingContext.bind("rmi:mud_game_" + name, mudGame);

    System.out.println("Waiting for invocations from clients...");
    return true;
  }

  /**
   * Creates a \n seperated list of MUD names currently bound to the naming
   * service.  The list of muds are not guaranteed to have been all created by
   * this mud manager.
   *
   * @return String containing the names of all bound muds.
   */
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

  /**
   * Fetches a given MUD instance from the naming service given its name.
   *
   * @param name The name of the bound MUD object (full name)
   * @return A MUD object (interface stub).
   */
  public Mud getGame(String name) throws RemoteException, NamingException
  {
    Context namingContext = new InitialContext();
    String url = "rmi://localhost/" + name;
    return (Mud) namingContext.lookup( url );
  }
}
