package cs3517.hale.mud;

import java.rmi.*;
import java.util.*;
import javax.naming.*;

/**
 * The MUD game client, which invokes a remote method.
 */
public class MudClient
{
  public static void main (String[] args) throws NamingException, RemoteException
  {
    Context namingContext = new InitialContext();

    System.out.println("rmiregistry bindings:");
    Enumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
    while (e.hasMoreElements())
      System.out.println(e.nextElement().getName());

    String url = "rmi://localhost/mud_game";
    Mud mudGame = (Mud) namingContext.lookup(url);

    //System.out.println(mudGame.prettyPrint());


    String player = question("What's your name?");

    String loc = mudGame.startLocation();
    mudGame.addThing( loc, player);
    System.out.println(mudGame.locationInfo( loc ));

    String answer = question("Where would you like to go? ('exit' to exit)");
    while(!answer.equals("exit"))
    {
      if (answer.equals("look"))
      {
        System.out.println(mudGame.locationInfo( loc ));
        answer = question("Where would you like to go? ('exit' to exit)");
        continue;
      }
      String newLoc = mudGame.moveThing( loc, answer, player );
      if (newLoc.equals(loc)) System.out.println("Can't do that.");
      System.out.println( mudGame.locationInfo( loc ));
      loc = newLoc;
      answer = question("Where would you like to go?");
    }

  }

  private static String question( String q )
  {
    Scanner userIn = new Scanner( System.in );
    System.out.println(q);
    System.out.print(">> ");
    String answer = userIn.next();
    System.out.println();
    return answer;
  }
}
