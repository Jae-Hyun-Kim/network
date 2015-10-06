package com.bit2015.network.network.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.bit2015.network.chat.ChatServerProcessThread;

public class HttpServer {
	
	private static final int PORT = 8088;
	
	public static void main(String[] args) {
				ServerSocket serverSocket = null;
		
		try{
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. binding
			InetAddress inetAddress = InetAddress.getLocalHost(); //아이넷 객체로 호스트의 로칼어드래스를 받아오는거다
			String hostAddress= inetAddress.getHostAddress();// 호스트 어드레스가 스트링형이니까 호스트네임을 받아오는것이다
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT)); // 호스트어드래스와 포트값을 입력해서 바인딩해주는거야  
			log("연결 기다림:" +hostAddress+":"+PORT);
			
			//3. 연결 요청 기다림
			while(true){
			Socket socket = serverSocket.accept();
			
			Thread thread = new HttpThread(socket); //쳇서버프로세스쓰레드를 구현해줘야한다.
			thread.start();
			}
			
		}catch(IOException ex){
			log("error: " +ex);
		}finally{
			if(serverSocket !=null && serverSocket.isClosed()==false){
				try{
				serverSocket.close();
			}catch(IOException ex){
				log("error: "+ex);
				
			}
		}
		}

	}
	public static void log(String log){ //log값을 입력하면 로그값을 뿌려주는 메소드
		System.out.println("[http-server] "+log);
	}

}
