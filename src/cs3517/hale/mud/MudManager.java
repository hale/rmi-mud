package cs3517.hale.mud;

import java.rmi.*;
import java.util.*;
import javax.naming.*;
//import cs3517.hale.mud.*;

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
   * @return true if the mud is created, false if the mud limit is reached.
   */
  public boolean makeMud(String name) throws RemoteException, NamingException;

  /**
   * Generates a nicely formatted list of muds managed by this MudManager.
   *
   * @return A \n seperated string of the names of the muds.
   */
  public String printableMudList() throws RemoteException, NamingException;

  /**
   * Adds a player to a MUD.
   *
   * @param player The player to add.
   * @param mud Name of the mud to join.
   * @return The joined mud (interface / stub).
   */
  public Mud joinMud( String player, String name ) throws RemoteException, NamingException;

  /**
   * Adds a player to the universal player list. This list is used to limit the
   * number of users in any MUD, and also to ensure unique names between
   * different mud instanced.
   *
   * @param player Name of the player to add
   * @return true if the player was added, false otherwise.
   */
  public boolean addPlayer( String player ) throws RemoteException;

  /**
   * Delete a player from the universal player list.
   *
   * @param player The player to remove.
   */
  public void delPlayer( String player ) throws RemoteException;


}
