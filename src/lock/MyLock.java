package lock;

import java.lang.reflect.Constructor;
import java.util.concurrent.locks.LockSupport;

import sun.misc.Unsafe;

/**
 * Created by yongzhi.dyz on 2017/12/26.
 */
public class MyLock {

	private volatile int state = 0;

	private ThreadList threadList = new ThreadList();

	private static long stateOffset;

	private static Unsafe unsafe;
	static {
		try {
			Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor(new Class<?>[0]);
			constructor.setAccessible(true);
			unsafe = constructor.newInstance(new Object[0]);

			stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state"));
		} catch (Exception e) {
		}

	}

	public void lock() {
		if (compareAndSetState(0, 1)) {
		} else {
			addNodeAndWait();
		}
	}

	public void unlock() {
		compareAndSetState(1, 0);
		Thread thread = threadList.pop();
		if (thread != null) {
			LockSupport.unpark(thread);
		}
	}

	private void addNodeAndWait() {
		// 如果当前只有一个等待线程时，重新获取一下锁，防止永远不被唤醒。
		if (threadList.insert(Thread.currentThread()) && compareAndSetState(0, 1)) {
			return;
		}
		LockSupport.park(this);
		if (compareAndSetState(0, 1)) {
			return;
		} else {
			addNodeAndWait();
		}
	}

	private boolean compareAndSetState(int expect, int update) {
		boolean result = unsafe.compareAndSwapInt(this, stateOffset, expect, update);
		System.out.println("stateOffset ==== " + stateOffset + ", expect ===== " + expect + ", update ===" + update );
		return result;
	}

}
