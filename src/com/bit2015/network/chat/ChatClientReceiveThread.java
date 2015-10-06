package com.bit2015.network.chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ChatClientReceiveThread extends Thread {

	private BufferedReader bufferedReader;
	public ChatClientReceiveThread(BufferedReader bufferedReader){
		this.bufferedReader = bufferedReader;
	}

	@Override  //오버라이드에서 super는 부모껄 사용한다는 뜻 서버에서 스타트를하면 얘가 실행된다
	public void run() {
		try{
			while(true){
				String data = bufferedReader.readLine();
				if(data ==null){
					break;
				}
				System.out.println(data);
			}
		}catch(IOException ex ){
			ChatClient.log( "error:" + ex );
		}

	}
	
}	

