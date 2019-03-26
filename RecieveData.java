// A Java program for a RecieveData
import java.net.*;
import java.io.*;

public class RecieveData
{
  //initialize socket and input stream
  private Socket          socket   = null;
  private ServerSocket    Server   = null;
  private DataInputStream in       =  null;


  public static void main(String args[])
  {
    RecieveData RecieveData = new RecieveData(5000);
  }

  // constructor with port as an argument
  //the constructor waits until it recieves a string of data from the SendData
  //class before writing that data into a file determined by the first four
  //letters of the string
  public RecieveData(int port)
  {
    String scoreData = "";
    // starts RecieveData and waits for a connection
    try
    {
      Server = new ServerSocket(port);
      socket = Server.accept();

      // takes input from the client socket
      in = new DataInputStream(
      new BufferedInputStream(socket.getInputStream()));

      String line = "";

      // reads message from client until "Over" is sent

      try
      {
        line = in.readUTF();
      //  System.out.println("The score was: " + line);
        scoreData = line;

      }
      catch(IOException i)
      {
        System.out.println(i);
      }

      // close connection
      socket.close();
      in.close();
    }catch(IOException i)
    {
      System.out.println(i);
    }

  try
  {

    //the filename that the score data is written to is determined by
    //the first four letters of the score data string
    //Hang is hangman data in hang.txt
    //four is 24 data in four.txt
    //jack is blackjack data in jack.txt

    String fileName = "scores/" + scoreData.substring(0,4) + ".txt";
    String actualScoreData = scoreData.substring(5,scoreData.length());
    //takes the first 4 characters in the string as the file name, the rest of
    //the string is the score data



    //Set true for appending to the text file instead of adding it to the line
    //appends to the bototm
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

}
