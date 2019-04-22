package com.xyq.test;

import org.junit.Test;

import com.xyq.text.Hello;

public class TestHello {
	
	@Test
	public void testHello() {
		Hello hello = new Hello();
		hello.say();
	}
}
