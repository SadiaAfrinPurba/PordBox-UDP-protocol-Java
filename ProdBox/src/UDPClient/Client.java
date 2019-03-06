/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPClient;

import UDPServer.Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author afrin
 */
    public class Client {
    public static String username,password,command;
    public static final int PORT = 9877;
    public static final int FILE_LENGTH = 1024;
    public static byte [] receiveByte = new byte[1024];
    public static byte[] sendByte = new byte[1024];
    public static LoginForm loginForm;
    public Client(){
        
    }
    public Client(String username,String password,String command) throws IOException{
        this.username =  username;
        this.password = password;
        this.command = command;
        System.out.println("cons"+username);
        
    }
    public static void doTask(String command) throws IOException{
            String message = username + " " + password + " "+command;
            boolean isSent = sendPacket(message);
           
            if(command.contains("login") && isSent){
                String responseFromServer = receivePacket();
                if(responseFromServer.equals("Successfully login")){
                    loginForm.setVisible(false);
                    
                }
             
               

           
       }
    }
    public static boolean sendPacket(String command) throws IOException{
        DatagramSocket clientUDPSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
       
        String sentence = command;
        System.out.println(sentence);
	sendByte = sentence.getBytes();
	DatagramPacket sendDatagramPacket = new DatagramPacket(sendByte, sendByte.length, IPAddress,PORT);
	clientUDPSocket.send(sendDatagramPacket);
        return true;
//			DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveByte, receiveByte.length);
//			String response = new String(receiveDatagramPacket.getData());
//			System.out.println(response);
//			clientUDPSocket.close();
    }
    public static String receivePacket(){
        DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveByte, receiveByte.length);
	String response = new String(receiveDatagramPacket.getData());
        return response;
    }
  
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException,NullPointerException{
        loginForm = new LoginForm();
        loginForm.setDefaultCloseOperation(loginForm.DISPOSE_ON_CLOSE);
        loginForm.setVisible(true);
        
        
        //doTask(command);

      

    }
    
  }

//    Client(String command, String folderName){
//        
//    }
    

