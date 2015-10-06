package com.bit2015.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
   
   private static final int PORT = 10001;
   public static void main(String[] args) {
      // TODO Auto-generated method stub
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
          System.out.println( "[서버] 연결 기다림");
          Socket socket = serverSocket.accept();
          
          
          
          InetSocketAddress inetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress(); //반대편 클라이언트의 소캣정보를 찍어주는것이다
          System.out.println(
                "[서버] 연결됨from : " + 
               inetSocketAddress.getHostName() +
               " : " + 
               inetSocketAddress.getPort());
          //4.데이터 쓰기
         // OutputStream os = socket.getOutputStream();
         // String data = "hello World";
         // os.write(data.getBytes("UTF-8"));
         // os.flush();
          
          //4. 데이터 읽고 쓰기
          OutputStream os = null;
          InputStream is = null;
          try {
        	  os = socket.getOutputStream();
              is = socket.getInputStream();
             
              while (true) {
                byte[] buffer = new byte[128];
                int readByteCount = is.read(buffer); //클라이언트에서 종료를하게되면 이값이 -1되어 종료!! 그리고 break로 와일문빠져나온다
                if(readByteCount < 0){ //클라이언트가 정상적으로 종료시 값이 -1로 바뀌어서 이렇게 걸어놓으면 종료가된다
                   System.out.println("[서버] 클라이언트로 부터 연결 끊김");
                   break;
                }
                String data = new String(buffer, 0, readByteCount, "UTF-8");
                System.out.println("[서버] 데이터 수신:" + data);
                
                os.write(data.getBytes("UTF-8"));
                os.flush();
             }
              is.close();
              os.close();
             if(socket.isClosed()==false){
            	 socket.close();
             }
             } catch (Exception e) {
            System.out.println("[서버] 오류" + e);
            // TODO: handle exception
         }
          
         
          //데이터 소켓 닫기
         
      } catch(IOException e) {
         e.printStackTrace();
      } finally {    // 위에 와일문이 종료되고나면 자동으로 서버도 종료되게 해놓은것임
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