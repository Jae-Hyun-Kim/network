package com.bit2015.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	private static final String SEVER_ADDRESS ="192.168.1.90";
	private static final int SERVER_PORT = 1001;
	  
	   public static void main(String[] args) {
	      // TODO Auto-generated method stub
	      Socket socket = null;
	      Scanner scanner =null;
	      
	      try{
	      scanner = new Scanner(System.in);
	      socket = new Socket();
	      
	     
	      socket.connect( new InetSocketAddress(SEVER_ADDRESS, SERVER_PORT )); //서버와 연결하는 가는것 (시간이 걸림)
	   
	      
	      //쓰고/받기
	      OutputStream os = socket.getOutputStream();
	      InputStream is = socket.getInputStream();
	      
	      
	      while(true){
	    	  
	    	  System.out.print(">>");
		      String data = scanner.nextLine();
		      if("exit".equals(data)==true){
		    	  break;
		      }
		      data +="\n";
		      os.write(data.getBytes("UTF-8"));
		      
		      byte[] buffer = new byte[128];
		      int readByteCount = is.read(buffer);
		      data = new String(buffer, 0, readByteCount, "UTF-8");
		      System.out.print("<<" + data);
		   }
	      os.close();
	      is.close();
	      
	      if ( socket.isClosed() == false) {
	         socket.close();
	         
	      }

	      } catch (IOException e) {
	         System.out.println("<<에러:" + e );
	      }
	      
	      

	   }
}
