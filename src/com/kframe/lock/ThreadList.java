package com.kframe.lock;

import java.lang.reflect.Constructor;

import sun.misc.Unsafe;

/**
 * Created by yongzhi.dyz on 2017/12/27.
 */
public class ThreadList {

	private volatile Node head = null;
	
	private static long headOffset;

	private static Unsafe unsafe;
	static {
		try {
			Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor(new Class<?>[0]);
			constructor.setAccessible(true);
			unsafe = constructor.newInstance(new Object[0]);

			headOffset = unsafe.objectFieldOffset(ThreadList.class.getDeclaredField("head"));

		} catch (Exception e) {
		}

	}

	/**
	 *
	 * @param thread
	 * @return 是否只有当前一个线程在等待
	 */
	public boolean insert(Thread thread) {
		Node node = new Node(thread);
		for (;;) {
			Node first = getHead();
			node.setNext(first);
			if (unsafe.compareAndSwapObject(this, headOffset, first, node)) {
				return first == null ? true : false;
			}
		}
	}

	public Thread pop() {
		Node first = null;
		for (;;) {
			first = getHead();
			Node next = null;
			if (first != null) {
				next = first.getNext();
			}
			if (unsafe.compareAndSwapObject(this, headOffset, first, next)) {
				break;
			}
		}
		return first == null ? null : first.getThread();
	}

	private Node getHead() {
		return this.head;
	}

	private static class Node {
		
		volatile Node next;
		
		volatile Thread thread;

		public Node(Thread thread) {
			this.thread = thread;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public Node getNext() {
			return next;
		}

		public Thread getThread() {
			return this.thread;
		}

	}
}