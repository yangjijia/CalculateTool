package yjj.tool.equationparaser;

import java.util.ArrayList;

import yjj.tool.equationparaser.statemodel.Context;

public class Paraser {
	
	public Paraser() {
		// TODO 自动生成的构造函数存根
	}
	
	public static ArrayList<Element> parase(String equation) throws ParaseException{		
		Context context=new Context(); 
		context.init(equation);
		context.calculate();
		ArrayList<Element> elements=context.getWordList();
		return elements;
	}
	
	public static void main(String[] args) throws ParaseException {
		 ArrayList<Element> tmp=parase("(-3.14+2.123)-4.82*4");
		 for(Element e:tmp){
			 System.out.println(e.element+"   "+e.type);
		 }
	}
}
