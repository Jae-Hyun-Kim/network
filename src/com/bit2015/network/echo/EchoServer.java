package com.bit2015.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	private static final int PORT = 10001;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try{
		//1.서버소켓 생성!!
        serverSocket = new ServerSocket();
        
        //2.바인딩
        InetAddress inetAddress = InetAddress.getLocalHost();
        String hostAddress = inetAddress.getHostAddress();
        serverSocket.bind( new InetSocketAddress( hostAddress , PORT)); //그둘을 붙여준다 호스트어드레스(서버의 아이피주소) 포트는 내가정한값
        System.out.println("[서버] 바인딩" + hostAddress + ":" + PORT);
        
        
        //3.연결요청 대기
        while(true){
        
        System.out.println( "[서버] 연결 기다림");
        Socket socket = serverSocket.accept();
        
        Thread thread = new EchoReceiveThread(socket);
        thread.start(); //EchoReceiveThread 클래스에있는 쓰레드를 만들어준다
        }
        
		}catch(IOException e){
			System.out.println("[서버] 에러 :"+e);
		}
		finally {    // 위에 와일문이 종료되고나면 자동으로 서버도 종료되게 해놓은것임
	         if(serverSocket != null && serverSocket.isClosed() == false){
	            try {
	               serverSocket.close();
	            } catch (IOException e) {
	               // TODO Auto-generated catch block
	               e.printStackTrace();
	            }
	         }
	      }
	}
}
