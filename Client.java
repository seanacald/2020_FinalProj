// A Java program for a Client
import java.net.*;
import java.io.*;

public class Client
{
  // initialize socket and input output streams
  private Socket socket            = null;
  private DataInputStream  input   = null;
  private DataOutputStream out     = null;


  // constructor to put ip address port and score
  public Client(String address, int port, String score)
  {
    // make a connection
    try
    {
      socket = new Socket(address, port);
      System.out.println("Connected");


      // sends output to the socket
      out = new DataOutputStream(socket.getOutputStream());
    }
    catch(UnknownHostException u)
    {
      System.out.println(u);
    }
    catch(IOException i)
    {
      System.out.println(i);
    }

    try
    {

      out.writeUTF(score);
    }
    catch(IOException i)
    {
      System.out.println(i);
    }

    // close the connection
    try
    {
      out.close();
      socket.close();
    }
    catch(IOException i)
    {
      System.out.println(i);
    }
  }



  public static void main(String args[])
  {

  }
}
