package com.own.test.designpattern.factorymethod;

public class Test {

	public static void main(String[] args) {
		
		Client c = new Client();
		Food food = c.getFood("B");
		System.out.println(food.toString());
	}
}
