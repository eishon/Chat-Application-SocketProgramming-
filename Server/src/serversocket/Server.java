package serversocket;

import java.net.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Server {
    
    static Socket s;
    static ServerSocket ss;

    static DataInputStream din;
    static DataOutputStream dout;
    static BufferedReader br;

    static String msgin = "", msgout = "";
    static String userName = "server", clientName = "";

    static ArrayList<ClientManager> clientThreads;
    static Dictionary<String, String> users;
    static Dictionary<String, String> userAuth;
    
    static OnlineUserThrd onlineUserThrd;

    public static void main(String[] args) throws IOException {
        users = new Hashtable<>();
        userAuth = new  Hashtable<>();
        clientThreads = new ArrayList<>();
        ss = new ServerSocket(1024);
        
        syncPassword();
        
        onlineUserThrd = new OnlineUserThrd();
        onlineUserThrd.start();
        
        System.out.println("Listening to Users Connection...");
        while (true) {
            s = null;
            try {
                s = ss.accept();

                //System.out.println("Client: " + s.getPort());
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                clientName = din.readUTF();
                
                ClientManager t = new ClientManager(s, din, dout, clientName);
                clientThreads.add(t);
                //System.out.println(t.getName());
                t.start();

            } catch (IOException e) {
                s.close();
            }
        }

    }
    
    public static void addUser(String cName, int port){
        users.put(cName, Integer.toString(port));
        System.out.println("Users Connected:");
        for (Enumeration i = users.keys(); i.hasMoreElements();) {
            String key = (String) i.nextElement();
            System.out.println("Users: " + key + " Port: " + users.get(key));
            //scanPorts(Integer.parseInt(users.get(key)));
        }
    }
    
    public static void addNewUser(String cName, String pass) throws FileNotFoundException, IOException{
        FileOutputStream outputStream = new FileOutputStream("Auth.txt", true);
        String text = "&&"+cName+"&&"+pass;
        outputStream.write(text.getBytes());
        outputStream.flush();
        outputStream.close();
        
        userAuth = new  Hashtable<>();
        syncPassword();
    }

    public static void scanPorts(int port) {
        if (port >= 1 && port <= 65535) {
            try {
                ss = new ServerSocket(port);
            } catch (IOException e) {
                System.out.println("Port " + port + " is open!");
            }
        }
    }

    public static ClientManager getThreadByUsername(String name) {
        ClientManager tmp = null;
        for (ClientManager i : clientThreads) {
            if (i.getName().equals(name)) {
                tmp = i;
            }
        }
        return tmp;
    }

    public static void sendToAll(String msg) throws IOException {
        if(!clientThreads.isEmpty()){
            for (ClientManager i : clientThreads) {
                Socket tmpSocket = i.getSocket();
                DataOutputStream tmpOS = new DataOutputStream(tmpSocket.getOutputStream());
                tmpOS.writeUTF(msg);
                //System.out.println(msg);
                
            }
            //System.out.println("Message Sent to ALL");
        }
    }
    
    public static void sendToUser(String uName, String msg) throws IOException {
        for (ClientManager i : clientThreads) {
            if(i.getName().equals(uName)){
                Socket tmpSocket = i.getSocket();
                DataOutputStream tmpOS = new DataOutputStream(tmpSocket.getOutputStream());
                tmpOS.writeUTF(msg);
                //System.out.println("Message Sent to " + i.getName());
            }
        }
    }
    
    public static void syncPassword(){
        String text = "";
        try {
            FileInputStream inputStream = new FileInputStream("Auth.txt");
            
            int data = inputStream.read();    
            while(data != -1) {
                text += (char) data;
                data = inputStream.read();
            }
            inputStream.close();
        }
        catch(IOException e) {
            System.out.println(e.toString());
        }
        
        String[] info = text.split("&&");
        for(int i=0; i<info.length/2; i++){
            userAuth.put(info[2*i],info[(2*i)+1]);
        }
    }
    
    public static boolean authCheck(String uname, String password){
        return userAuth.get(uname).equals(password);
    }
    
    public static boolean validUserCheck(String uname){
        return userAuth.get(uname)!= null;
    }
    
    public static boolean validUserCheck_2(String uname){
        return users.get(uname)== null;
    }
    
    public static boolean validNewUserCheck(String uname){
        return userAuth.get(uname)== null && users.get(uname)== null;
    }
    
    public static String onlineUserNames(){
        String tmp = "";
        for (Enumeration i = users.keys(); i.hasMoreElements();) {
            if(tmp != "") tmp+="&&&";
            tmp += (String) i.nextElement();
        }
        
        //System.out.println(tmp);
        return tmp;
    }
    
    public static void removeThread(ClientManager name){
        System.out.println("User Removed From Port: "+users.remove(name.getName()));
        clientThreads.remove(name);
    }

}
