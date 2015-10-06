package com.bit2015.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println(inetAddress.getHostName()); //겟 호스트네임
			System.out.println(inetAddress.getHostAddress());

			byte[] addresses = inetAddress.getAddress();
			for(int i =0; i <addresses.length; i++){
				int address =  addresses[i] & 0xff; // 바이트로 되있는 애들 부호없는거로 바꿀때
				System.out.println(address);
			}
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
	}
}
