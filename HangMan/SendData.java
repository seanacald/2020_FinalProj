// A Java program for a SendData
import java.net.*;
import java.io.*;

public class SendData
{

  // initialize socket and input output streams
  private Socket socket            = null;
  private DataInputStream  input   = null;
  private DataOutputStream out     = null;



    public static void main(String args[])
    {
      //SendData SendData = new SendData("127.0.0.1", 5000);
    }

  // constructor to put ip address and port
  //the constructor also sends the data
  public SendData(String address, int port, String score)
  {
    // make a connection
    try
    {
      socket = new Socket(address, port);
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
      //sends the score to the server
      //the score is passed as an argument to the SendData constructor
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

}
