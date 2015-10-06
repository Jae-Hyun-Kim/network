package com.bit2015.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer {
	
	private static final int PORT = 60000;
	private static final int BUFFER_SIZE = 1024;  //사이즈가 고정되어있다.
	
	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		try{
	
		//1. UDP서버 소캣 생성
		datagramSocket = new DatagramSocket(PORT);
		log("packet 수신 대기 ");
		while(true){	
		//2. 수신대기(데이터받기)
		
		DatagramPacket receivePacket = 
				new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE); //이 버퍼안에 클라이언트가 보낸 데이터가 셋팅된다
		datagramSocket.receive(receivePacket);
		
		//3. 수신 데이터 출력
		String message = new String(receivePacket.getData(),0,receivePacket.getLength(),"UTF-8"); //데이터를 0부터 페킷의 길이만큼
		log("Packet 수신 : "+message);
		
		//4. 데이터 보내기(답장)
		DatagramPacket sendPacket = 
				new DatagramPacket(
						receivePacket.getData(),
						receivePacket.getLength(),
						receivePacket.getAddress(),
						receivePacket.getPort());
		datagramSocket.send(sendPacket);
		
		}
		
		}catch(IOException ex){
			log("error"+ex);
		}finally{
			//5. 자원정리
			datagramSocket.close();
		}
	}
	public static void log(String log){
		System.out.println("[udp-echo-server] "+log);
	}

}
