package com.kframe;
import java.lang.reflect.Constructor;

import com.kframe.lock.MyLock;

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
		//		unsafe.putObjectVolatile(8);
	}
}
