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

    System.out.println(mudGame.toString());
  }
}
