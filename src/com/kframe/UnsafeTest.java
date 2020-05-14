package com.kframe;

import java.lang.reflect.Constructor;

import sun.misc.Unsafe;

public class UnsafeTest {

	private static Unsafe unsafe;
	static {
		try {
			Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor(new Class<?>[0]);
			constructor.setAccessible(true);
			unsafe = constructor.newInstance(new Object[0]);
		} catch (Exception e) {
		}

	}

	public static void main(String[] args) {
		System.out.println(unsafe.addressSize());
		Object o = null;
		unsafe.putObjectVolatile((o = new ReentrantLockDemo()), 1000l, new ReentrantLockDemo());
		ReentrantLockDemo ot = (ReentrantLockDemo) unsafe.getObject(o, 1000l);
		System.out.println(ot.hashCode());
		System.out.println(ot);
	}
}
