package com.kframe;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable {
	
	// 公平锁
	private static ReentrantLock lock = new ReentrantLock(true);

	@Override
	public void run() {
		while (true) {
			/**
			 * 可重入锁 多加锁
			 */
			lock.lock();
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + " get lock");
				Thread.sleep(1000);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				/**
				 * 可重入锁 多次解锁
				 */
				lock.unlock();
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
//		testFailSyn();//公平锁
		testInterupt();//可中断锁
	}
	
	/**
	 * 公平锁
	 */
	public static void testFailSyn() {
		ReentrantLockDemo rtld = new ReentrantLockDemo();
		Thread thread1 = new Thread(rtld);
		Thread thread2 = new Thread(rtld);
		thread1.start();
		thread2.start();
	}
	/**
	 * 可中断
	 */
	public static void testInterupt() {
		
		Thread thread1 =new Thread(() -> {
			lock.lock();
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println(Thread.currentThread().getName() + "===线程中断");
					lock.unlock();
					break;
				}
				System.out.println(Thread.currentThread().getName() + "=====继续执行");
			}
			
		}) ;
		thread1.start();
		
		Thread thread2 =new Thread(() -> {
			lock.lock();
			System.out.println("线程  ===" + Thread.currentThread().getName());
			lock.unlock();
			
		}) ;
		thread2.start();
		thread1.interrupt();
	}
}
