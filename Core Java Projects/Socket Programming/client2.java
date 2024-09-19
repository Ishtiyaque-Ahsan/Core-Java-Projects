import java.util.*;
import java.io.*;
import java.net.*;

public class client2 {
    public static void main(String[] args) {
        try {
            String serverAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println(serverAddress);
            Socket socket = new Socket(serverAddress, 1234);
           
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner inputFile = new Scanner(new File("FileS.txt"));
            String s=inputFile.next();
            inputFile.close();
            output.println(s);
            

            System.out.print("Enter  number: ");
           
       Scanner sc=new Scanner(System.in);
       int k=sc.nextInt();
           
          
           BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            for(int i=1;i<=k;i++){
               inputFile = new Scanner(new File("File"+i+".txt"));
                String temp;
                while(!(temp=inputFile.next()).equals("0")){
                      output.println(temp);
                     
                }
                
                inputFile.close();
                output.println("EOF");
                String freq=serverInput.readLine();
                System.out.println("File"+i+" has "+freq+ "frequency of word"+s);
            }
            output.println("EF");
            
            String result = serverInput.readLine();
            System.out.println("Server response: " + result);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
