package com.Xbiom.testNGClasses;

import org.testng.annotations.Test;

public class A3 {

	@Test(dependsOnMethods="testA2")
	public void testA3()
	{
		System.out.println("class A3 is run ");
	}
	
	@Test(enabled=false)
	public void testA2()
	{
		System.out.println("Newly added method ");
	}
}
