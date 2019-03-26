package titlescreen;

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
     System.out.println("We're in.");
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
        //System.out.println("The score was: " + line);
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

    //takes the first 4 characters in the string as the file name, the rest of
    //the string is the score data
    String fileName2 =("src/titlescreen/scores/"+scoreData.substring(0,4)+".txt"); 

    //Gets path of files
    //pls note: it is called wow because im too scared to change it
    String wow = new File(fileName2).getAbsolutePath();
    String wow2 = new File(wow).toURI().toString();
    String actualScoreData = scoreData.substring(5,scoreData.length());
    
    // Create a printWriter that appends to the end of the file
    PrintWriter output = new PrintWriter(wow);

    // Write formatted output to the file
    output.println(actualScoreData);


    // Close the file
    output.close();

  }catch(IOException i){
    System.out.println(i);
  }

  }

}
