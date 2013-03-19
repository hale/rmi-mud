package cs3517.hale.mud;

import java.rmi.*;
import java.util.*;
import javax.naming.*;

/**
 * The remote interface for the MudManager.  The mud manager is responsible for
 * joining, listing, creating and deleting MUDs.
 */
public interface MudManager extends Remote
{

  /**
   * Creates a new MUD.
   *
   * @param name     Uniquely identifies the game.
   * @param edges    Path to an edges file.
   * @param messages Path to an messages file.
   * @param things   Path to an things file.
   */
  public void makeMud(String name, String edges, String messages, String
      things) throws RemoteException, NamingException;

}
