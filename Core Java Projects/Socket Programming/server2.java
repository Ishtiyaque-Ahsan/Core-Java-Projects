import java.io.*;
import java.net.*;

public class server2 {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server waiting for client on port 1234");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String s = input.readLine();
            
            

            int temp=0;
            int result=0;
            String line;
            while (!(line = input.readLine()).equals("EF")) {
               
               if(line.equals("EOF")){
                output.println(temp);
                result+=temp;
                temp=0;
               }
                if((line.equals(s))){
                    temp++;
                   
                }
            }
            output.println("Result: " + result);

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
