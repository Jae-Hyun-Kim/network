package com.bit2015.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;


public class ChatServerProcessThread extends Thread {
	private static final String PROTOCOL_DIVIDER =":";
	private Socket socket;
	private String nickname;
	private List<PrintWriter> listPrintWriters;
	

	//생성
	public ChatServerProcessThread(Socket socket,List<PrintWriter> listPrintWriters){
		this.socket = socket;
		this.listPrintWriters=listPrintWriters;
	}

	//run 오버라이드 구현
	@Override
	public void run() {
		
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		
		try{
		//1. 스트림 얻기
		bufferedReader = 
		new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
		printWriter =
				new PrintWriter(socket.getOutputStream());
		//2. 리모트 호스트 정보 얻기
		InetSocketAddress inetSocketAddress =
				(InetSocketAddress) socket.getRemoteSocketAddress();
		String remoteHostAddress = inetSocketAddress.getHostName();
		int remoteHostPort = inetSocketAddress.getPort();
		ChatServer.log("연결됨 from"+remoteHostAddress+":"+remoteHostPort);
	
		//3. 요청처리
		while(true){
			String request = bufferedReader.readLine();
			if(request ==null){
				ChatServer.log("클라이언트로 부터 연결이 끊김");
				doQuit(printWriter);  //종료하면 삭제시켜라
				break;
			}
			String[] tokens = request.split(PROTOCOL_DIVIDER);
			if("join".equals(tokens[0])){
				doJoin(printWriter, tokens[1]);
			}
			else if("message".equals(tokens[0])){
				
			}
			else if("quit".equals(tokens[0])){
				
			}
		}
	
		//4. 자원정리
		bufferedReader.close();
		printWriter.close();
		if(socket.isClosed()==false){
			socket.close();
		}
		}catch(IOException ex){
			ChatServer.log("Error:"+ex);
			doQuit(printWriter); //비정상종료시에도 종료에하나니까 이걸로 삭제해줘
		}
	
	}
	public void doQuit(PrintWriter printWriter){
		removePrintWriter(printWriter);
	}
	public void doJoin(PrintWriter printWriter,String nickname){
		//1. 닉네임 저장
		this.nickname =nickname;
		//2. 메세지 브로드캐스팅
		String message = nickname+"님이 입장했습니다.";
		broadcast(message);

		//3. 
		addPrintWriter(printWriter);
		
		//4. ack 조인 확인해주는애
		printWriter.println(printWriter);
		printWriter.flush();
	}
	
	public void removePrintWriter(PrintWriter printWriter){
		synchronized(listPrintWriters){
			listPrintWriters.remove(printWriter);
		}
	}
	public void addPrintWriter(PrintWriter printWriter){
		synchronized(listPrintWriters){
			listPrintWriters.add(printWriter);
		}
	}
	public void broadcast(String data){
		synchronized(listPrintWriters){
			int count =listPrintWriters.size();
			for(int i =0; i<count; i++){
				PrintWriter printWriter = listPrintWriters.get(i);
				printWriter.println(data);
				printWriter.flush(); 
			}
		}
		
	}

}
