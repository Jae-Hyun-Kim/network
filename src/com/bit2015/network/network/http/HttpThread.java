package com.bit2015.network.network.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HttpThread extends Thread {
   private static final String WEB_ROOT="C:\\bit2015\\workspace\\network\\web-root"; //웹루트폴더만들고 그 주소를 여기다 써놓는다
   private Socket socket;
   
   public HttpThread(Socket socket){
      this.socket = socket;
   }

   @Override
   public void run() {
      // TODO Auto-generated method stub
      BufferedReader bufferedReader = null;

       OutputStream outputStream = null;

         try{

            // 1. 스트림 얻기

            bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            outputStream = socket.getOutputStream();

            //2. Remote Host Information

            InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

            HttpServer.log( "??? from " + inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort()); 

            //3. 요청처리

            String request = bufferedReader.readLine();

            if( request != null ) {

               HttpServer.log( "request:" + request );
                 //header

                outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes() );

                outputStream.write( "Content-Type:text/html; charset=UTF-8\r\n".getBytes() );

                outputStream.write( "\r\n".getBytes());

                //body

                outputStream.write( "<h1>Hello World</h1>".getBytes() );

                //flush

                outputStream.flush();

            }

             //4. 연결 끊기

             bufferedReader.close();

             outputStream.close();

             socket.close();

        } catch( IOException ex ) {

             HttpServer.log( "error:" + ex );

        }
   }
         private void sendStaticResource( OutputStream outputStream, String path, String protocol ) throws IOException {

        	    if( "/".equals( path ) ) {
        	        path = "/index.html"; 
        	    }

        	    String extension = path.substring( path.lastIndexOf( "." ) );
        	    if( ".html".equals( extension ) == false && ".htm".equals( extension ) == false ) {
        	    	sendError404( outputStream, protocol );
        	       return;
        	    }

        	    File file = new File( WEB_ROOT, path );

        	    if( file.exists() == false ) {
        	       sendError404( outputStream, protocol );
        	       return;

        	    }

        	}

        	private void sendError404( OutputStream outputStream, String protocol ) throws IOException {

        	    // header
        	    outputStream.write( ( protocol + " 404 File Not Found\r\n" ).getBytes() );
        	    outputStream.write( "Content-Type:text/html; charset=UTF-8\r\n".getBytes() );
        	    outputStream.write( "\r\n".getBytes() );

        	    //body
        	    outputStream.write( "<h1>File Not Found</h1>".getBytes() );
        	    outputStream.flush();

        	}
   

      
  }