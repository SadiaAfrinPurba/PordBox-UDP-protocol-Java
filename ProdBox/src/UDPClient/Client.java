/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPClient;


import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

/**
 *
 * @author afrin
 */
    public class Client{
    public String username;
    public boolean isLogin;
    public static final int PORT = 9877;
    public static final int FILE_LENGTH = 1024;
    public static final String SUCCESS_MESSAGE = "Successful";


    
    public  DatagramSocket clientUDPSocket;

    public  String receivePacket() throws IOException{
       
        
        byte [] receiveByte = new byte[1024];
        DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveByte, receiveByte.length);
        clientUDPSocket.receive(receiveDatagramPacket);
	String response = new String(receiveDatagramPacket.getData());
        System.out.println("inside client receive "+response);
        return response;
    }
    public  void sendPacket(String command) throws IOException{
        clientUDPSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        
        byte[] sendByte = new byte[1024];
      
            
            sendByte = command.getBytes();
        
	DatagramPacket sendDatagramPacket = new DatagramPacket(sendByte, sendByte.length, IPAddress,PORT);
	clientUDPSocket.send(sendDatagramPacket);
        

    }

    public  void clientLogin(String command) throws IOException{
                    System.out.println("hello");
                     sendPacket(command);
                     String responseFromServer = receivePacket();
                     System.out.println("inside client login "+responseFromServer);
                     if(responseFromServer.trim().equals(SUCCESS_MESSAGE)){
                
                         System.out.println("world");
                         JOptionPane.showMessageDialog(null,"Login Successful!!");
                        
                         ClientDirectoryForm clientDir = new ClientDirectoryForm();
                         clientDir.setVisible(true);
                         
                    
                    
                }
                else{
                   JOptionPane.showMessageDialog(null, "Invalid login", "Error", JOptionPane.ERROR_MESSAGE);
               }
                

               
                
                
    }
    public  void clientNewFolder(String command) throws IOException{
               
                isLogin = true;
                if(isLogin){
                    sendPacket(command);
                    String responseFromServer = receivePacket();
                    if(responseFromServer.trim().equals(SUCCESS_MESSAGE)){
                          JOptionPane.showMessageDialog(null,"New folder create.");
                    
                    }
                   else{
                         JOptionPane.showMessageDialog(null, "Can't create new folder on server", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
                else{
                       JOptionPane.showMessageDialog(null, "Please login first to create new folder", "Error", JOptionPane.ERROR_MESSAGE);

                    

                }   
                
                
    }
      public  void clientUploadDownloadFile(String command) throws IOException{
                    
                    
                    sendPacket(command);
                    String responseFromServer = receivePacket();
                    if(responseFromServer.trim().equals(SUCCESS_MESSAGE)){
                         if(command.contains("upload")){
                             JOptionPane.showMessageDialog(null,"Upload Successfully");
                         }
                         else{
                             JOptionPane.showMessageDialog(null,"Download Successfully");
                         }

                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Try again!", "Error", JOptionPane.ERROR_MESSAGE);

                    }

            
    }
  
    public static void main(String[] args) {
            
            ClientWelcomeForm cWelcomeForm = new ClientWelcomeForm();
            cWelcomeForm.setVisible(true);   
           
    }
 
      
  }


    

