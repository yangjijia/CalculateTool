package yjj.tool.equationparaser;

import java.util.ArrayList;

public class Table {
	private static ArrayList<Character> number=new ArrayList<Character>();
	private static ArrayList<Character> symbol=new ArrayList<Character>();
	
	static{
		number.add('0');
		number.add('1');
		number.add('2');
		number.add('3');
		number.add('4');
		number.add('5');
		number.add('6');
		number.add('7');
		number.add('8');
		number.add('9');
		
		symbol.add('(');
		symbol.add(')');
		symbol.add('+');
		symbol.add('-');
		symbol.add('*');
		symbol.add('/');
		symbol.add('.');
	}
	
	public static boolean isOfNumber(char c){
		try{
			Character.valueOf(c);
		}catch(Exception e){
			return false;
		}
		return number.contains(c);
	}
	
	public static boolean isOfSymbol(char c){
		try{
			Character.valueOf(c);
		}catch(Exception e){
			return false;
		}
		return symbol.contains(c);
	}
	private Table() {}
}
