package com.own.test.designpattern.factorymethod;

public class FoodFactory {

	private FoodFactory(){};
	
	public static A getA(){
		System.out.println("得到A对象");
		return new A();
	}
	
	public static B getB(){
		System.out.println("得到B对象");
		return new B();
	}
	
	public static C getC(){
		System.out.println("得到C对象");
		return new C();
	}
}
