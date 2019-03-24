// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server
{
  //initialize socket and input stream
  private Socket          socket   = null;
  private ServerSocket    server   = null;
  private DataInputStream in       =  null;

  // constructor with port
  public Server(int port)
  {
    String scoreData = "";
    // starts server and waits for a connection
    try
    {
      server = new ServerSocket(port);
      System.out.println("Server started");


      socket = server.accept();
      System.out.println("Client accepted");

      // takes input from the client socket
      in = new DataInputStream(
      new BufferedInputStream(socket.getInputStream()));

      String line = "";

      // reads message from client until "Over" is sent

      try
      {
        line = in.readUTF();
        System.out.println("The score was: " + line);
        scoreData = line;

      }
      catch(IOException i)
      {
        System.out.println(i);
      }
      System.out.println("Closing connection");

      // close connection
      socket.close();
      in.close();
    }catch(IOException i)
    {
      System.out.println(i);
    }

  try
  {

    String fileName = "scores/" + scoreData.substring(0,4) + ".txt";
    String actualScoreData = scoreData.substring(5,scoreData.length());
    //takes the first 4 characters in the string as the file name, the rest of
    //the string is the score data
    System.out.println(fileName);


    //Set true for append mode
    FileWriter fileWriter = new FileWriter(fileName, true);

    // Create a printWriter that appends to the end of hte file
    java.io.PrintWriter output = new java.io.PrintWriter(fileWriter);

    // Write formatted output to the file
    output.println(actualScoreData);


    // Close the file
    output.close();




  }catch(IOException i){
    System.out.println(i);
  }











  }

  public static void main(String args[])
  {
    Server server = new Server(5000);
  }
}
