package com.bit2015.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class ChatServer {
	
	private static final int PORT = 30000;
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		List<PrintWriter> listPrintWriters = new ArrayList<PrintWriter>();
		
		try{
			//1. 서버소캣 생성
			serverSocket = new ServerSocket();
			//2. binding
			InetAddress inetAddress = InetAddress.getLocalHost(); //아이넷 어드레스란 도메인 주소를 이용해 ip알아내느것
			String hostAddress =inetAddress.getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
		    
			//3. 연결 요청 기다림
			while(true){
				Socket socket = serverSocket.accept();
				
				Thread thread = new ChatServerProcessThread(socket, listPrintWriters);
				thread.start();
			}
			
		}catch(IOException ex){
			log("error: "+ ex);
		}finally{
			if(serverSocket !=null && serverSocket.isClosed()== false){
				try{
					serverSocket.close();
				}catch(IOException ex){
					log("error:"+ex);
				}
			}
			
		}
		
	}
	public static void log(String log){
		System.out.println("[Chat-server]"+log);
	}
}
