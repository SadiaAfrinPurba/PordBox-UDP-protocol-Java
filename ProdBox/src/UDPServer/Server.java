/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPServer;

import UDPClient.LoginForm;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author afrin
 */
public class Server extends Thread{
    public static final int PORT = 9877;
    public static final int FILE_LENGTH = 1024;
    public static final String LOGIN = "login";
    public static final String NEW_FOLDER = "mkdir";
    public static final String CHANGE_DIR = "cd";
    public static final String DOWNLOAD = "download";
    public static final String UPLOAD = "upload";
    public static final String SUCCESS_MESSAGE = "Successful";
    public static final String ERROR_MESSAGE = "Error";
    public String username;
    public InetAddress ipAddress;
    public int clientPort;
    public static DatagramSocket serverSocket;
    public int hasClient = 0;
    public DatagramPacket sendDatagramPacket;
    public DatagramPacket receiveDatagramPacket;


    public  String receivePacket() throws SocketException, IOException{
        
        
        //serverSocket = new DatagramSocket(PORT);
        while(true){
           
            byte[] receiveData = new byte[FILE_LENGTH];
            receiveDatagramPacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receiveDatagramPacket);
           
            ipAddress = receiveDatagramPacket.getAddress();
            clientPort = receiveDatagramPacket.getPort();
            String command = new String(receiveDatagramPacket.getData());
            System.out.println("inside recieve "+command);
            return command;
        }
        
    }
    public  void sendPacket(String sendToClient) throws SocketException, IOException{
    
            byte[] sendData = new byte[FILE_LENGTH];
            sendData = sendToClient.getBytes();
            sendDatagramPacket = new DatagramPacket(sendData, sendData.length, ipAddress, clientPort);
	    serverSocket.send(sendDatagramPacket);
            System.out.println("inside send "+sendToClient);
//        
        
        

        
    }

    
    public  boolean login(String username, String password){


         User userOne = new User("User1","123");
         User userTwo = new User("User2","afrin");
         User userThree = new User("User3","123sadia");
         boolean isSuccess =false;
         
         if(username.equals(userOne.getUsername()) && password.equals(userOne.getPassword())){
             onSuccess();
             isSuccess = true;
            
              
         }
         else if(username.equals(userTwo.getUsername()) && password.equals(userTwo.getPassword())){
             onSuccess();
             isSuccess = true;
             
         }
         else if(username.equals(userThree.getUsername()) && password.equals(userThree.getPassword())){
             onSuccess();
              isSuccess = true;
             
         }
         else{
 
              isSuccess = false;
         }
         
         return isSuccess;
         
         
    }
    public  void onSuccess(){

             hasClient++;
             ServerWelcomeForm serverForm = new ServerWelcomeForm();
             serverForm.LabelStatus.setText("Running..");
             serverForm.LabelStatus.setForeground(Color.green);
             serverForm.LabelConnected.setText(String.valueOf(hasClient));
             serverForm.setVisible(true);
        
    }
    public  boolean createNewFolder(String username,String folderName) throws IOException{
         
                boolean response = false;
               
                String currentDir = System.getProperty("user.dir").toString();
      
                String path = String.join("/",currentDir,"userProfile",username,folderName); 
                System.out.println(path);
                File createFolder = new File(path.trim());

                if(createFolder.exists()){
                    JOptionPane.showMessageDialog(null, "Folder already exists", "Rename the folder", JOptionPane.ERROR_MESSAGE);
                    
                }
                else{
                      boolean success = createFolder.mkdirs();
                      if(success){
                          response = true;
                          
                      }
                      else{
                          response = false;
                      }
                    
                }


   
        return response;
   
        
    }
    
    public boolean uploadFile(String username,String command) throws FileNotFoundException, IOException{
         boolean response;
         String[] commands = command.split(" ");
         String fileDir = commands[1];
         String[] words = fileDir.split("/");
         String fileName = words[words.length - 1];
         FileInputStream readFile = new FileInputStream(fileDir.trim());
         int index = 0;
         byte[] fileByte = new byte[1024];
         while(readFile.available()!=0){
                 fileByte[index]=(byte)readFile.read();
                 index++;
         }
         String currentDir = System.getProperty("user.dir");
         String path = String.join("/",currentDir,"userProfile",username,fileName);
         FileOutputStream writeFile = new FileOutputStream(path.trim());
         writeFile.write(fileByte);
         
        readFile.close();
        writeFile.close();
        response = true;
         
        return response;
    }

    public static void errorMessage(String message){
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void main(String[] args) throws SocketException{
        ServerWelcomeForm welcomeForm = new ServerWelcomeForm();
        welcomeForm.setVisible(true);

         Thread thread = new  Thread(new Server());
         thread.start();
    


}


    public  void jobs() throws SocketException {
        serverSocket = new DatagramSocket(PORT);
        
        try { 
               for(int i = 0;i<=100;i++){
                   
               
               String command = receivePacket();
               System.out.println("Inside run " + command);
               if(command.contains(LOGIN)){
                       String[] commands = command.split(" ");
                       username = commands[0];
                       String password = commands[1];
                       String job = commands[2];
                       boolean success = login(username,password);
                       System.out.println("inside job-login"+username+success);
                       if(success){
                           sendPacket(SUCCESS_MESSAGE);
                        }
                    }
               else if(command.contains(NEW_FOLDER)){
                    System.out.println("inside job-folder"+command);
                    String[] fName = command.split(" ");
                    String folderName = fName[1];
                    boolean success = createNewFolder(username, folderName);
                    System.out.println("inside job-folder"+folderName+success);
                    if(success){
                        sendPacket(SUCCESS_MESSAGE);
                    }
                }
               else if(command.contains(UPLOAD)){
                   boolean success = uploadFile(username,command);
                   if(success){
                       sendPacket(SUCCESS_MESSAGE);
                   }
               }
              
             }
            } 
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        
        }

    public void run(){
        try {
            jobs();
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

}


class User{
    private String username;
    private String password;
    
     User(String username,String password){
         this.username = username;
         this.password = password;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
