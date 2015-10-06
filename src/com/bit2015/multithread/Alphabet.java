package com.bit2015.multithread;

public class Alphabet {
	public void print() {
		for (char c = 'A'; c <= 'Z'; c++) { // c에는 A의 아스키코드값이 저장되어있다.
			System.out.print(c);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
