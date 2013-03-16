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

    for (String answer = question(); !answer.equals("exit"); answer=question())
    {
      if (answer.equals("help"))
      {
        System.out.println("Type one of the following commands at the prompt");
        System.out.println("\tlook\tShow a description of the current location.");
        System.out.println("\tnorth\tMove north");
        System.out.println("\teast\tMove east");
        System.out.println("\tsouth\tMove south");
        System.out.println("\twest\tMove west");
        System.out.println("\texit\tQuit the game");
        System.out.println("\tpickup <thing>\tAdd an item in the room to your inventory.");
      }
      else if (answer.equals("look"))
      {
        System.out.println(mudGame.locationInfo( loc ));
      }
      else if (answer.equals("north") || answer.equals("east")
          || answer.equals("south") || answer.equals("west"))
      {
        String newLoc = mudGame.moveThing( loc, answer, player );
        if (newLoc.equals(loc))
          System.out.println("Can't do that.");
        else {
          loc = newLoc;
          System.out.println( mudGame.locationInfo( loc ));
        }
      }
      else
      {
        String[] ansParts = answer.split("\\s+");
        if (ansParts.length != 2) continue;
        if (ansParts[0].equals("pickup"))
        {
          System.out.println( mudGame.pickupItem( player, ansParts[1], loc ));
        }
      }
    }

  }

  private static String question()
  {
    return question("Please type an action, or 'help' for help");
  }

  private static String question(String question)
  {
    Scanner userIn = new Scanner( System.in );
    System.out.println(question);
    System.out.print(">> ");
    String answer = userIn.nextLine();
    System.out.println();
    return answer;
  }
}
