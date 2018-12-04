package com.own.test.designpattern.factorymethod;

public class Client {

	public Food getFood(String name){
		
		Food food = null;
		
		if("A".equals(name)){
			food = FoodFactory.getA();
		}else if("B".equals(name)){
			food = FoodFactory.getB();
		}else if("C".equals(name)){
			food = FoodFactory.getC();
		}
		return food;
	}
}
