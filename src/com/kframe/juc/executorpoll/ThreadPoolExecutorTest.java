package com.kframe.juc.executorpoll;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
	
	public static void main(String[] args) {
		int corePoolSize = 10;
		int maximumPoolSize =100000000;
		long keepAliveTime = 3000;
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
					maximumPoolSize,
					keepAliveTime,
					TimeUnit.SECONDS,
					new LinkedBlockingDeque<Runnable>(10), new ThreadFactory() {
						
						@Override
						public Thread newThread(Runnable r) {
							Thread t = new Thread(r);
							t.setDaemon(true);
							return t;
						}
					}, new RejectedExecutionHandler() {
						
						@Override
						public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
							System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");
						}
					});
		for (int i = 0;i<10000;i++) {
			executor.submit(() -> {
				
				System.out.println(Thread.currentThread().getName());
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}
		
	}

}
