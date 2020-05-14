package com.kframe;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable ,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6632860908027331048L;
	private static ReentrantLock lock = new ReentrantLock(true);

	@Override
	public void run() {
		while (true) {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " get lock");
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		ReentrantLockDemo rtld = new ReentrantLockDemo();
		lock.lock();
		Thread thread1 = new Thread(rtld);
		Thread thread2 = new Thread(rtld);
		thread1.start();
//		thread2.start();
	}
}
