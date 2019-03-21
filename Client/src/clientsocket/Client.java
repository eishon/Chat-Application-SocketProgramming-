package clientsocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Client {
    static Socket s;
    
    static DataInputStream din;
    static DataOutputStream dout;
    static BufferedOutputStream fout;
    static BufferedInputStream fin;
            
    static String msgin="", msgout = "";
    static String userName = "", pass = "";
    static String ipAddress = "";
    
    static loginForm loginForm;
    static ClientChatForm chatForm;
    
    static File file;
            
    public static void main(String[] args) {
        loginForm = new loginForm();
        loginForm.setVisible(true);
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        InputThrd in = new InputThrd();
        in.start();
    }
    
    public static void clientSubmitBtn(){
        userName = loginForm.getUserNameTxtFld().getText();
        pass = loginForm.getPasswordTxtFld().getText();
        ipAddress = loginForm.getIpAddressTxtFld().getText();
        System.out.println(ipAddress);
        
        try{
            s = new Socket(ipAddress, 1024);
            
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            
            dout.writeUTF(userName);
            if(loginForm.getNewUserRadionBtn().isSelected()){
                dout.writeUTF("new");
            }else{
                dout.writeUTF("existing");
            }
            msgin = din.readUTF();
            System.out.println(msgin);
            if(msgin.equals("valid")){
                dout.writeUTF(pass);
                msgin = din.readUTF();
                System.out.println(msgin);
                if(!msgin.equals("authorized")){
                    loginForm.setLoginLabelTxt("Unauthorized! Connection to SERVER closed !");
                }else{
                    chatForm = new ClientChatForm();
                    chatForm.setVisible(true);
                    chatForm.setTitle(userName);
                    chatForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    chatForm.setOnlineUsersList(new String[1]);

                    loginForm.setVisible(false);
                    //loginForm.dispose();
                }
            }else{
                loginForm.setLoginLabelTxt("User is not valid !");
            }
            
        }
        catch(IOException e){
            System.out.println(e.toString());
        }
    }
    
    public static void sendMsg() throws IOException{
        if( !chatForm.user.isEmpty() && !chatForm.getSendMsgTxtFld().getText().isEmpty()){
            msgout = "chat";
            msgout += ">>>"+chatForm.getSendMsgTxtFld().getText();
            msgout += ">>>"+chatForm.getSelectedUser();
            msgout += ">>>"+userName;

            dout.writeUTF(msgout);
            System.out.println("Message Sent...");
            chatForm.getSendMsgTxtFld().setText("");
        }else{
            chatForm.setChatFormMsg("Please select a User or Type Something");
        }
    }
    
    public static void sendFileMsg(File f) throws IOException{
        file = f;
        msgout = "system";
        msgout += ">>>file";
        msgout += ">>>"+userName;
        msgout += "&&&"+chatForm.getSelectedUser();
        msgout += "&&&"+file.getName();

        dout.writeUTF(msgout);
        chatForm.setChatFormMsg(userName+" wants to send file to "+chatForm.getSelectedUser());
    }
    
    public static String[] removeSelfFromUsers(String[] user){
        String[] tmp = new String[user.length-1];
        for(int i=0,j=0; i<user.length; i++){
            if(user[i].equals(userName)){
                continue;
            }else{
                tmp[j] = user[i];
                j++;
            }
        }
        
        return tmp;
    }
    
    public static class InputThrd extends Thread{
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
                                        
        @Override
        public void run() {
            try {
                while(true){
                    if(din!=null){
                        msgin = din.readUTF();
                        System.out.println(msgin);
                        String[] msg = msgin.split(">>>");

                        switch(msg[0]){
                            case "system":
                                switch(msg[1]){
                                    case "end":
                                        System.exit(0);
                                        break;
                                    case "users":
                                        String[] usr = msg[2].split("&&&");
                                        usr = removeSelfFromUsers(usr);
                                        chatForm.setOnlineUsersList(usr);
                                        break;
                                    case "send_file":
                                            System.out.println("Uploading File: "+file.getName());

                                            fin = new BufferedInputStream(new FileInputStream(file.getAbsoluteFile()));

                                            buffer = new byte[1024];
                                            bytesRead = 0;
                                            try {
                                                while ((bytesRead = fin.read(buffer)) > 0) {
                                                    System.out.print("B-"+bytesRead);
                                                    fout.write(buffer, 0, bytesRead);
                                                    fout.flush();
                                                }
                                            } catch (IOException ex) {
                                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                            System.out.println("File "+file.getName()+" Sent.");
                                            chatForm.setChatFormMsg("File "+file.getName()+" Sent.");

                                            if(fin!=null) fin.close();
                                            if(fout!=null) fout.close();
                                        break;
                                    case "receive_file":
                                        String fileName = msg[2];
                                        
                                        File file = new File("C:\\Users\\EISHON\\Desktop\\"+userName+"\\"+fileName);
                                        fin = new BufferedInputStream(s.getInputStream());
                                        fout = new BufferedOutputStream(new FileOutputStream(file));
                                        
                                        System.out.println("File Receiving: "+ fileName);

                                        buffer = new byte[1024];
                                        bytesRead = 0;

                                        while ((bytesRead = fin.read(buffer)) > 0) {
                                            System.out.print("B-"+bytesRead);
                                            fout.write(buffer, 0, bytesRead);
                                            fout.flush();
                                        }

                                        System.out.println("\nFile Successfully Received.");
                                        if(fin!=null) fin.close();
                                        if(fout!=null) fout.close();
                                        break;
                                }
                                break;
                            case "chat":
                                chatForm.setRcvMsgTxtAreaTxt(msg[1]+": "+msg[2]);
                                System.out.println(msg[1]+": "+msg[2]);
                                break;

                        }
                        
                        if(msg[2].equals("end")) break;
                    }
                    InputThrd.sleep(100);
                }
                System.out.println("DIN null");
                if(s != null){
                    s.close();
                    System.exit(0);
                }
            } catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
    }
}