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
  public String locationInfo( String loc );

  /**
   * Get the start location for new MUD users.
   */
  public String startLocation();

  /**
   * Add a thing to a location; used to enable us to add new users.
   */
  public void addThing( String loc, String thing );

  /**
   * Remove a thing from a location.
   */
  public void delThing( String loc, String thing );

  /**
   * A method to enable a player to move through the MUD (a player
   * is a thing). Checks that there is a route to travel on. Returns
   * the location moved to.
   */
  public String moveThing( String loc, String dir, String thing );
}
