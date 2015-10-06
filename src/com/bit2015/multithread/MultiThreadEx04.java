package com.bit2015.multithread;

public class MultiThreadEx04 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread1 = new DigitThread();
		Thread thread2 = new Thread(new AlphabetRunnableImpl2());
		
		thread1.start();
		thread2.start();
	}

}
