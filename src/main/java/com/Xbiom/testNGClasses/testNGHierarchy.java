package com.Xbiom.testNGClasses;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testNGHierarchy {
	
	
	@BeforeTest
	public void beforeTest()
	{
		System.out.println("this is the before test annotations");
	}
	@AfterTest
	public void afterTest()
	{
		System.out.println("this is the after test annotations");
	}
	
	@Test
	public void firstTestMethod()
	{
		System.out.println("this is the first test method");
	}
	@Test
	public void secondTestMethod()
	{
		System.out.println("this is the second test method");
	}
	@Test
	public void thirdTestMethod()
	{
		System.out.println("this is the third test method");
	}
	@BeforeClass
	public void beforeClass()
	{
		System.out.println("this is the before class method");
	}
	@AfterClass
	public void AfterClass()
	{
		System.out.println("this is the affter class method");
	}
	@AfterSuite
	public void afterSuite()
	{
		System.out.println("this is the after suite method");
	}
	@BeforeSuite
	public void beforeSuite()
	{
		System.out.println("this is the before suite method");
	}
}
