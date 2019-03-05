/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDPServer;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author afrin
 */
public class Server {
    public static final int PORT = 9877;
    public static final int FILE_LENGTH = 1024;
    public static final String LOGIN = "login";
    public static final String NEW_FOLDER = "mkdir";
    public static final String CHANGE_DIR = "cd";
    public static final String DOWNLOAD = "download";
    public static final String UPLOAD = "upload";
    public static String username, password,command = "";
    
    static byte[] sendData = new byte[FILE_LENGTH];
    public static int hasClient = 0;
    //constructor
//    public Server(String username,String password){
//        this.username = username;
//        this.password = password;
//        System.out.println("inside server cons "+command);
//    }
//     public Server(String command){
//     
//        this.command = command;
//        System.out.println("inside server "+command);
//    }
    
    public static String receivePacket() throws SocketException, IOException{
        byte[] receiveData = new byte[FILE_LENGTH];
        DatagramSocket serverSocket = new DatagramSocket(PORT);
        DatagramPacket receiveDatagramPacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receiveDatagramPacket);
        command = new String(receiveDatagramPacket.getData());
        System.out.println("inside recieve "+command);
        return command;
    }
    
    public static void login(String username, String password){


         User userOne = new User("purba","123");
         User userTwo = new User("sadia","afrin");
         User userThree = new User("afrin","123sadia");
       
         if(username.equals(userOne.getUsername()) && password.equals(userOne.getPassword())){
             JOptionPane.showMessageDialog(null,"Login Successful!!");
             hasClient = 1;
             System.out.println("hellp");
             ServerWelcomeForm form = new ServerWelcomeForm();
             form.LabelStatus.setFont(new java.awt.Font("Cantarell", 1, 14)); // NOI18N

              form.LabelStatus.setText("Running");
              form.LabelStatus.setForeground(Color.green);
              form.kGradientPanel1.add(form.LabelStatus);
              form.LabelStatus.setBounds(150, 290, 90, 15);
              
         }
         else if(username.equals(userTwo.getUsername()) && password.equals(userTwo.getPassword())){
             JOptionPane.showMessageDialog(null,"Login Successful!!");
             hasClient = 1;
             
         }
         else if(username.equals(userThree.getUsername()) && password.equals(userThree.getPassword())){
             JOptionPane.showMessageDialog(null,"Login Successful!!");
             hasClient = 1;
             
         }
         else{
             errorMessage("Invalid login.User is not avaiable in the server!");
             hasClient = 0;

         }
         
         
    }
    public static void errorMessage(String message){
        final JPanel panel = new JPanel();
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    public static void main(String[] args){
        ServerWelcomeForm welcomeForm = new ServerWelcomeForm();
        welcomeForm.setVisible(true);
        
        
        try{

             //task
            String message = receivePacket();
            System.out.println("inside main "+ message);
            String[] words=message.split(" ");
            username = words[0];
            password = words[1];
            command = words[2];
            System.out.println("inside word "+ username);
            
            
             if(message.contains("login")){
                 System.out.println("inside main-if "+ username);
                 login(username,password);
                 
             }
//             else if(command.contains(NEW_FOLDER)){
//                 
//             }
//             else if(command.contains(CHANGE_DIR)){
//                 
//             }
//             else if(command.contains(DOWNLOAD)){
//                 
//             }
//             else if(command.contains(UPLOAD)){
//                 
//             }
             else{
                 errorMessage("Invalid command");
             }
    
        }  
        catch(Exception ex){
            System.out.println(ex.toString());
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
