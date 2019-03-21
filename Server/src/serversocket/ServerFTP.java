/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author EISHON
 */
public class ServerFTP {
    
    ServerSocket ss;
    Socket s;
    
    DataInputStream din;
    BufferedInputStream fin;
    BufferedOutputStream fout;
    
    ArrayList<FTPClientManager> ftpClientThreads;
    Dictionary<String, String> usersFtp;
    
    String clientName = "";
    
    public void startServerFTP(int port) throws IOException{
        usersFtp = new Hashtable<>();
        ss = new ServerSocket(port);
        
        System.out.println("Listening to Users Connection...");
        while (true) {
            s = null;
            try {
                s = ss.accept();

                //System.out.println("Client: " + s.getPort());
                din = new DataInputStream(s.getInputStream());
                fin = new BufferedInputStream(s.getInputStream());
                fout = new BufferedOutputStream(s.getOutputStream());
                
                clientName = din.readUTF();
                
                FTPClientManager t = new FTPClientManager(s, fin, fout, clientName);
                ftpClientThreads.add(t);
                //System.out.println(t.getName());
                t.start();

            } catch (IOException e) {
                s.close();
            }
        }
    }
    
    public FTPClientManager getThreadByUsername(String name) {
        name += "_ftp";
        FTPClientManager tmp = null;
        for (FTPClientManager i : ftpClientThreads) {
            if (i.getName().equals(name)) {
                tmp = i;
            }
        }
        return tmp;
    }
    
    public void sendToUser(String uName, String msg) throws IOException {
        for (FTPClientManager i : ftpClientThreads) {
            if(i.getName().equals(uName)){
                Socket tmpSocket = i.getSocket();
                DataOutputStream tmpOS = new DataOutputStream(tmpSocket.getOutputStream());
                tmpOS.writeUTF(msg);
                //System.out.println("Message Sent to " + i.getName());
            }
        }
    }
    
    public void removeThread(FTPClientManager name){
        System.out.println("User FTP Removed From Port: "+usersFtp.remove(name.getName()));
        ftpClientThreads.remove(name);
    }
    
    public void addUser(String cName, int port){
        usersFtp.put(cName, Integer.toString(port));
        System.out.println("FTP Users Connected:");
        for (Enumeration i = usersFtp.keys(); i.hasMoreElements();) {
            String key = (String) i.nextElement();
            System.out.println("Users: " + key + " Port: " + usersFtp.get(key));
            //scanPorts(Integer.parseInt(users.get(key)));
        }
    }
    
}
