package com.Xbiom.testNGClasses;

import org.testng.annotations.Test;

public class A {
	
	@Test(groups="gold")
	public void testA()
	{
		System.out.println("class A is run ");
	}
	@Test(groups="silver")
	public void testB()
	{
		System.out.println("method B is run ");
	}
	@Test(groups="gold")
	public void testC()
	{
		System.out.println("method C is run ");
	}
	@Test(groups="gold")
	public void testD()
	{
		System.out.println("method D is run ");
	}

}
