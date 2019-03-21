package serversocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientManager extends Thread 
{
    Socket s;
    
    DataInputStream din;
    DataOutputStream dout;
    BufferedOutputStream fout;
    BufferedInputStream fin;
            
    String msgin="", msgout = "";
    String userName = "server";
    
    public ClientManager(Socket cs, DataInputStream cdis, DataOutputStream cdos, String cname) 
    {
        s = cs;
        din = cdis;
        dout = cdos;
        this.setName(cname);
    }
 
    @Override
    public void run() 
    {
        InputThrd in = new InputThrd(this.getName());
        try{
            msgin = din.readUTF();
            //System.out.println(msgin);
            if(msgin.equals("existing")){
                if(Server.validUserCheck(this.getName())){
                    if(Server.validUserCheck_2(this.getName()))
                        Server.sendToUser(this.getName(), "valid");
                    else
                        Server.sendToUser(this.getName(), "invalid");
                }else{
                    Server.sendToUser(this.getName(), "invalid");
                    Server.removeThread(Server.getThreadByUsername(this.getName()));
                }

                msgin = din.readUTF();
                //System.out.println(msgin);

                if(Server.authCheck(this.getName(), msgin)){
                    Server.sendToUser(this.getName(), "authorized");
                    Server.addUser(this.getName(), s.getPort());
                }else{
                    Server.sendToUser(this.getName(), "unauthorized");
                    s.close();
                }

                in.start();
            } else {
                if(Server.validNewUserCheck(this.getName())){
                    Server.sendToUser(this.getName(), "valid");
                }else{
                    Server.sendToUser(this.getName(), "invalid");
                    Server.removeThread(Server.getThreadByUsername(this.getName()));
                }

                msgin = din.readUTF();
                Server.addNewUser(this.getName(), msgin);
                //System.out.println(msgin);
                Server.sendToUser(this.getName(), "authorized");
                Server.addUser(this.getName(), s.getPort());

                in.start();
            }
        }
        catch(IOException e){
        
        }
    }
    
    public Socket getSocket(){
        return s;
    }
    
    public DataOutputStream getOutputStream(){
        return dout;
    }
    
    public class InputThrd extends Thread{

        private InputThrd(String name) {
            this.setName(name);
        }

        @Override
        public void run() {
            try {
                while(true){
                   msgin = din.readUTF();
                   System.out.println(msgin);
                   
                   String[] msg = msgin.split(">>>");
                   
                   switch(msg[0]){
                        case "system":
                            switch(msg[1]){
                                case "file":
                                    String[] msngr = msg[2].split("&&&");
                                    String fileName = msngr[2];
                                    String receiver = msngr[1];
                                    
                                    File file = new File(fileName);
                                    fin = new BufferedInputStream(s.getInputStream());
                                    //fout = new BufferedOutputStream(new FileOutputStream(file));
                                    fout = new BufferedOutputStream(Server.getThreadByUsername(receiver).getOutputStream());
                                    
                                    System.out.println(msngr[0]+" wants to send file to "+msngr[1]);
                                    dout.writeUTF("system>>>send_file>>>"+fileName);
                                    
                                    byte[] buffer = new byte[1024];
                                    int bytesRead = 0;

                                    try {
                                        while ((bytesRead = fin.read(buffer)) > 0) {
                                            System.out.print("B-"+bytesRead);
                                            fout.write(buffer, 0, bytesRead);
                                            fout.flush();
                                        }

                                        System.out.println("\nFile Successfully Received.");
                                    } catch (IOException ex) {
                                        Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                    Server.sendToUser(receiver, "system>>>receive_file>>>"+fileName);
                                    
                                    
                                    if(fin!=null) fin.close();
                                    if(fout!=null) fout.close();
                                    /*fin = new BufferedInputStream(new FileInputStream(file));
                                    fout = new BufferedOutputStream(Server.getThreadByUsername(receiver).getOutputStream());
                                    
                                    buffer = new byte[1024];
                                    bytesRead = 0;
                                    while ((bytesRead = fin.read(buffer, 0, buffer.length)) > 0) {
                                        System.out.print(".");
                                        fout.write(buffer, 0, buffer.length);
                                        fout.flush();
                                    }
                                    

                                    System.out.println("File "+file.getName()+" Sent to "+receiver);

                                    if(fin!=null) fin.close();
                                    if(fout!=null) fout.close();*/
                                    
                                    break;
                            }
                            break;
                        case "chat":
                            ClientManager tmp = Server.getThreadByUsername(msg[2]);
                            //System.out.println("ThreadName: "+tmp.getName());
                            tmp.getOutputStream().writeUTF(msg[0]+">>>"+msg[3]+">>>"+msg[1]);
                            System.out.println(msg[0]+">>>"+msg[3]+">>>"+msg[1]);
                            //tmp = null;
                            break;
                   }
                   
                   if(msg[1].equals("end")) break;
                   
                   InputThrd.sleep(100);
                }
                s.close();
                System.exit(0);
            } catch (IOException | InterruptedException ex) {
                Server.removeThread(Server.getThreadByUsername(this.getName()));
                System.out.println("Interrupted");
            }
        }
        
    }
}
