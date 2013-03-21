package cs3517.hale.mud;

import java.rmi.*;
import java.util.ArrayList;

/**
 * The remote interface for a single MUD instance. MUDs are bound to the naming
 * server and accessed from the client.  MUDs are instantiated and bound by the
 * MudManager object.
 *
 * This class was adapted from the provided file MUD.java.
 *
 * @author Philip Hale - 50907446
 */
public interface Mud extends Remote
{
  /**
   * A method to provide a string describing a particular location.
   *
   * @param loc Location to describe
   * @return Text description of the location.
   */
  public String locationInfo( String loc ) throws RemoteException;

  /**
   * Get the start location for new MUD users.
   *
   * @return Starting location for this game world.
   */
  public String startLocation() throws RemoteException;

  /**
   * Add a thing to a location.
   *
   * @param loc Location to add the thing.
   * @param thing Thing to add.
   */
  public void addThing( String loc, String thing ) throws RemoteException;

  /**
   * Add a new player to the game (at a certain location). Adds the player to a
   * set of players currently in the game.  Used to differentiate between items
   * and players.
   *
   * @param loc Location name where to put the player.
   * @param player Player name to add.
   * @return true if the player is added to the game
   *         false if
   *           * The name has already been taken.
   *           * The name is the empty string.
   *           * There is no more room in the server.
   * @see Mud#addItem( String, String )
   */
  public boolean addPlayer( String loc, String player ) throws RemoteException;

  /**
   * Remove a thing from a location.
   *
   * @param loc Where to find the thing.
   * @param thing Thing to remove.
   */
  public void delThing( String loc, String thing ) throws RemoteException;

  /**
   * Remove a player from the game.  In addition to removing the thing, also
   * puts the inventory back in the game world and removes the player from the
   * list of players.
   *
   * @see Mud#delThing( String, String )
   */
  public void delPlayer( String loc, String thing ) throws RemoteException;

  /**
   * A method to enable a player to move through the MUD (a player
   * is a thing). Checks that there is a route to travel on. Returns
   * the location moved to.
   *
   * @param loc Current location
   * @param dir Direction of travel (north, east, south, west).
   * @param thing Thing to move.
   * @return loc if movement not possible, new location otherwise.
   */
  public String moveThing( String loc, String dir, String thing ) throws
    RemoteException;

  /**
   * This method enables us to display the entire MUD (mostly used
   * for testing purposes so that we can check that the structure
   * defined has been successfully parsed.
   *
   * @return String representation of the entire MUD.
   */
  public String prettyPrint() throws RemoteException;

  /**
   * This method attempt to add the item to a player's list of items. It
   * removes the item from the game world, and adds it to a hashmap of items to
   * players.  Players cannot be picked up. Does not guarantee that the item is
   * picked up.
   *
   * @param player Player subject.
   * @param item Item object to be picked up.
   * @param location Location of the player.
   * @return Message appropriate for the action.
   */
  public String pickupItem(String player, String item, String location) throws
    RemoteException;

  /**
   * Returns a list of items picked up by the player.
   *
   * @param player Who's inventory to retrieve.
   * @return List of items held by the player.
   */
  public ArrayList<String> getInventory( String player ) throws RemoteException;
}
