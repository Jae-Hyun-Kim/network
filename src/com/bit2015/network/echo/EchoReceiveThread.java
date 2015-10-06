package com.bit2015.network.echo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoReceiveThread extends Thread {
	private Socket socket=null ;
	
	public EchoReceiveThread(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		
         BufferedReader reader = null;
         PrintWriter printWriter = null;
        		 
         try {
             reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
             printWriter = new PrintWriter(socket.getOutputStream());
             
             while (true) {
               byte[] buffer = new byte[128];
               String data =reader.readLine();
               if(data ==null){
            	   break;
               }
               System.out.println("[서버] 데이터 수신:" + data);
               
               
               printWriter.println(data);
               printWriter.flush();
            }
            
            printWriter.close();
            if(socket.isClosed()==false){
           	 socket.close();
            }
            } catch (Exception e) {
           System.out.println("[서버] 오류" + e);
           // TODO: handle exception
        }
		
	}
	

	

}
