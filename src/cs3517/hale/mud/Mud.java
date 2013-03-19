package cs3517.hale.mud;

import java.rmi.*;

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
   * Remove a thing from a location.
   */
  public void delThing( String loc, String thing ) throws RemoteException;

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
   * players.  Players cannot be picked up. TODO: make a player list, add to it
   * when players join. write a special addPlayer method which adds to the list
   * of players then calls addThing.
   */
  public String pickupItem(String player, String item, String location) throws RemoteException;
}
