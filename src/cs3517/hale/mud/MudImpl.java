/***********************************************************************
 * cs3517.solutions.mud.MUD
 ***********************************************************************/

package cs3517.hale.mud;
import cs3517.hale.mud.*;

import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

/**
 * A class that can be used to represent a MUD; essentially, this is a
 * graph.
 */

public class MudImpl extends UnicastRemoteObject implements Mud
{
  /**
   * Private stuff
   */

  // A record of all the vertices in the MUD graph. HashMaps are not
  // synchronized, but we don't really need this to be synchronised.
  private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>();

  private String _startLocation = "";

  /**
   * Add a new edge to the graph.
   */
  private void addEdge( String sourceName,
      String destName,
      String direction,
      String view )
  {
    Vertex v = getOrCreateVertex( sourceName );
    Vertex w = getOrCreateVertex( destName );
    v._routes.put( direction, new Edge( w, view ) );
  }

  /**
   * Create a new thing at a location.
   */
  private void createThing( String loc,
      String thing )
  {
    Vertex v = getOrCreateVertex( loc );
    v._things.add( thing );
  }

  /**
   * Change the message associated with a location.
   */
  private void changeMessage( String loc, String msg )
  {
    Vertex v = getOrCreateVertex( loc );
    v._msg = msg;
  }

  /**
   * If vertexName is not present, add it to vertexMap.  In either
   * case, return the Vertex. Used only for creating the MUD.
   */
  private Vertex getOrCreateVertex( String vertexName )
  {
    Vertex v = vertexMap.get( vertexName );
    if (v == null) {
      v = new Vertex( vertexName );
      vertexMap.put( vertexName, v );
    }
    return v;
  }

  /**
   *
   */
  private Vertex getVertex( String vertexName )
  {
    return vertexMap.get( vertexName );
  }

  /**
   * Creates the edges of the graph on the basis of a file with the
   * following fromat:
   * source direction destination message
   */
  private void createEdges( String edgesfile )
  {
    try {
      FileReader fin = new FileReader( edgesfile );
      BufferedReader edges = new BufferedReader( fin );
      String line;
      while((line = edges.readLine()) != null) {
        StringTokenizer st = new StringTokenizer( line );
        if( st.countTokens( ) < 3 ) {
          System.err.println( "Skipping ill-formatted line " + line );
          continue;
        }
        String source = st.nextToken();
        String dir    = st.nextToken();
        String dest   = st.nextToken();
        String msg = "";
        while (st.hasMoreTokens()) {
          msg = msg + st.nextToken() + " ";
        }
        addEdge( source, dest, dir, msg );
      }
    }
    catch( IOException e ) {
      System.err.println( "Graph.createEdges( String " +
          edgesfile + ")\n" + e.getMessage() );
    }
  }

  /**
   * Records the messages assocated with vertices in the graph on
   * the basis of a file with the following format:
   * location message
   * The first location is assumed to be the starting point for
   * users joining the MUD.
   */
  private void recordMessages( String messagesfile )
  {
    try {
      FileReader fin = new FileReader( messagesfile );
      BufferedReader messages = new BufferedReader( fin );
      String line;
      boolean first = true; // For recording the start location.
      while((line = messages.readLine()) != null) {
        StringTokenizer st = new StringTokenizer( line );
        if( st.countTokens( ) < 2 ) {
          System.err.println( "Skipping ill-formatted line " + line );
          continue;
        }
        String loc = st.nextToken();
        String msg = "";
        while (st.hasMoreTokens()) {
          msg = msg + st.nextToken() + " ";
        }
        changeMessage( loc, msg );
        if (first) {      // Record the start location.
          _startLocation = loc;
          first = false;
        }
      }
    }
    catch( IOException e ) {
      System.err.println( "Graph.recordMessages( String " +
          messagesfile + ")\n" + e.getMessage() );
    }
  }

  /**
   * Records the things assocated with vertices in the graph on
   * the basis of a file with the following format:
   * location thing1 thing2 ...
   */
  private void recordThings( String thingsfile )
  {
    try {
      FileReader fin = new FileReader( thingsfile );
      BufferedReader things = new BufferedReader( fin );
      String line;
      while((line = things.readLine()) != null) {
        StringTokenizer st = new StringTokenizer( line );
        if( st.countTokens( ) < 2 ) {
          System.err.println( "Skipping ill-formatted line " + line );
          continue;
        }
        String loc = st.nextToken();
        while (st.hasMoreTokens()) {
          addThing( loc, st.nextToken());
        }
      }
    }
    catch( IOException e ) {
      System.err.println( "Graph.recordThings( String " +
          thingsfile + ")\n" + e.getMessage() );
    }
  }

  /**
   * All the public stuff. These methods are designed to hide the
   * internal structure of the MUD. Could declare these on an
   * interface and have external objects interact with the MUD via
   * the interface.
   */

  /**
   * A constructor that creates the MUD.
   */
  public MudImpl( String edgesfile, String messagesfile, String thingsfile )
    throws RemoteException
  {
    createEdges( edgesfile );
    recordMessages( messagesfile );
    recordThings( thingsfile );

    System.out.println( "Files read..." );
    System.out.println( vertexMap.size( ) + " vertices\n" );
  }

  public String prettyPrint() throws RemoteException
  {
    String summary = "";
    Iterator iter = vertexMap.keySet().iterator();
    String loc;
    while (iter.hasNext()) {
      loc = (String)iter.next();
      summary = summary + "Node: " + loc;
      summary += (vertexMap.get( loc )).toString();
    }
    summary += "Start location = " + _startLocation;
    return summary;
  }

  public String locationInfo( String loc ) throws RemoteException
  {
    return getVertex( loc ).toString();
  }

  public String startLocation() throws RemoteException
  {
    return _startLocation;
  }

  public void addThing( String loc, String thing ) throws RemoteException
  {
    Vertex v = getVertex( loc );
    v._things.add( thing );
  }

  public void delThing( String loc,
      String thing ) throws RemoteException
  {
    Vertex v = getVertex( loc );
    v._things.remove( thing );
  }

  public String moveThing( String loc, String dir, String thing ) throws RemoteException
  {
    Vertex v = getVertex( loc );
    Edge e = v._routes.get( dir );
    if (e == null)   // if there is no route in that direction
      return loc;  // no move is made; return current location.
    v._things.remove( thing );
    e._dest._things.add( thing );
    return e._dest._name;
  }

  /**
   * A main method that can be used to testing purposes to ensure
   * that the MUD is specified correctly.
   */
  public static void main(String[] args) throws RemoteException
  {
    if (args.length != 3) {
      System.err.println("Usage: java Graph <edgesfile> <messagesfile> <thingsfile>");
      return;
    }
    try {
      MudImpl m = new MudImpl( args[0], args[1], args[2] );
      System.out.println( m.toString() );
    } catch( RemoteException e) { System.out.println(e); }
  }

  public String pickupItem(String player, String item, String location) throws RemoteException
  {
    // TODO
    return "Not yet implemented";
  }
}
