/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serversocket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EISHON
 */
public class FTPClientManager extends Thread{
    Socket s;
    
    BufferedOutputStream fout;
    BufferedInputStream fin;
            
    String msgin="", msgout = "";
    String userName = "server";
    
    public FTPClientManager(Socket cs, BufferedInputStream cdis, BufferedOutputStream cdos, String cname) 
    {
        s = cs;
        fin = cdis;
        fout = cdos;
        this.setName(cname+"_ftp");
    }
 
    @Override
    public void run() 
    {
        
    }
    
    public Socket getSocket(){
        return s;
    }
    
    public BufferedOutputStream getOutputStream(){
        return fout;
    }
}
