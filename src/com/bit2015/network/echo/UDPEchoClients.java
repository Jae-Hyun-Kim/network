package com.bit2015.network.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClients {
	private static final String SERVER_IP= "192.168.1.90";
	private static final int SERVER_PORT = 60000;
	private static final int BUFFER_SIZE = 1024;
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in, "euc-kr");
		DatagramSocket datagramSocket = null;
		try{
		//1. UDP클라이언트 소캣 생성
		datagramSocket  = new DatagramSocket();
		
		//2. 페킷보내기
		while(true){
			
			System.out.println(">>");
			String message = scanner.nextLine();
			if(message.equals("quit")){
				break;
			}
			byte[] data = message.getBytes();  //메세지를 보내려면 일단 바이트로 바꿔주고
		
			DatagramPacket sendPacket = 
				new DatagramPacket(data, data.length,new InetSocketAddress(SERVER_IP,SERVER_PORT));//실제 데이터를 넣고 받는사람 주소작성
			
				datagramSocket.send(sendPacket);
		
		//3. 데이터 받기
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE],BUFFER_SIZE);
				datagramSocket.receive(receivePacket);
		
		//4. 데이터 출력
			message =
				new String(receivePacket.getData(), 0, receivePacket.getLength(),"UTF-8");
				System.out.println("UDP-echo-client: "+message);
		
			}
		
		}catch(IOException ex){
			log("error "+ex);
			
		}finally{
			//5. 자원정리
			datagramSocket.close();
			scanner.close();
		}
	}
	public static void log(String log){
		System.out.println("[UDP-echo-Client] "+log);
	}

}
