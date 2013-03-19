package cs3517.hale.mud;

import java.rmi.*;
import java.util.ArrayList;

/**
 * The remote interface for the MUD.
 * @author Philip Hale
 */
public interface Mud extends Remote
{
  /**
   * A method to provide a string describing a particular location.
   */
  public String locationInfo( String loc ) throws RemoteException;

  /**
   * Get the start location for new MUD users.
   */
  public String startLocation() throws RemoteException;

  /**
   * Add a thing to a location; used to enable us to add new users.
   */
  public void addThing( String loc, String thing ) throws RemoteException;

  /**
   * Add a new player to the game (at a certain location). Adds the player to a
   * set of players currently in the game.  Used to differentiate between items
   * and players.
   *
   * @return true if the player is added to the game, false if the player's
   * name has already been taken.
   */
  public boolean addPlayer( String loc, String thing ) throws RemoteException;

  /**
   * Remove a thing from a location.
   */
  public void delThing( String loc, String thing ) throws RemoteException;

  /**
   * Remove a player from the game.  In addition to removing the thing, also
   * puts the inventory back in the game world and removes the player from the
   * list of players.
   */
  public void delPlayer( String loc, String thing ) throws RemoteException;

  /**
   * A method to enable a player to move through the MUD (a player
   * is a thing). Checks that there is a route to travel on. Returns
   * the location moved to.
   */
  public String moveThing( String loc, String dir, String thing ) throws RemoteException;

  /**
   * This method enables us to display the entire MUD (mostly used
   * for testing purposes so that we can check that the structure
   * defined has been successfully parsed.
   */
  public String prettyPrint() throws RemoteException;

  /**
   * This method attempt to add the item to a player's list of items. It
   * removes the item from the game world, and adds it to a hashmap of items to
   * players.  Players cannot be picked up.
   */
  public String pickupItem(String player, String item, String location) throws RemoteException;

  /**
   * Returns a list of items picked up by the player.
   */
  public ArrayList<String> getInventory( String player ) throws RemoteException;
}
