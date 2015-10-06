package com.bit2015.network.time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {

	private static final int PORT =50000;
	private static final int BUFFER_SIZE = 1024;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss a" );
		String data = format.format( new Date() );


		DatagramSocket datagramSocket = null;
		
		
		
		try{
		//1. UDP서버 소캣 생성
		datagramSocket = new DatagramSocket(PORT);
		
		 log("packet 수신대기");

	
		//2. 데이터보내기
		 DatagramPacket senPacket =
				 new DatagramPacket(
						
						 );
		
			
		}catch(IOException ex){
			log("error : "+ex);
			
		}finally{
			
		}
		
		
		
		
		
		//4.데이터 보내기
		
		
	}
	public static void log(String log){
		
		System.out.println("[udp-echo-server]"+log);
	}

}
