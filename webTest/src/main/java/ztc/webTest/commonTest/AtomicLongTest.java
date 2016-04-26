package ztc.webTest.commonTest;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

public class AtomicLongTest {

	@Test
	public void getAtomicLong() {
		AtomicLong atomicLong = new AtomicLong();
		System.out.println(atomicLong.get());
		System.out.println(atomicLong.get());
		System.out.println(atomicLong.get());
		System.out.println(atomicLong.get());
		System.out.println(atomicLong.get());
	}

	@Test
	public void test() {
		int i = 1;
		i <<= 1;// Ïàµ±ÓÚ i=i*2
		System.out.println(i);
	}
}
