package lock;

public class MyLockTest implements Runnable {

	public static MyLock lock = new MyLock();

	int i = 0; 
	@Override
	public void run() {
		
		while (true) {
			if (++i == 1) {
				System.out.println("111");
			}
			lock.lock();
			try {
				Thread.sleep(500);
				System.out.println(Thread.currentThread().getName() + " get lock");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		MyLockTest rtld = new MyLockTest();
		Thread thread1 = new Thread(rtld);
		Thread thread2 = new Thread(rtld);
		thread1.start();
		thread2.start();
	}
}
