package yjj.tool.syntaxparaser;

import java.util.HashMap;

public class Priority {
	
	public static interface Number{
		public final int HIGH=8;
		public final int MIDDLE=4;
		public final int LOW=2;
	}
	
	private static HashMap<String,Integer> priority=new HashMap<String,Integer>();
	
	static{
		priority.put("+", Number.LOW);
		priority.put("-", Number.LOW);
		priority.put("*", Number.HIGH);
		priority.put("/", Number.HIGH);
	}
	
	public static int getPriority(String symbol) throws UnallowedException{
		if(!symbol.equals("+")&&!symbol.equals("-")&&!symbol.equals("*")&&!symbol.equals("/")){
			throw new UnallowedException();
		}
		return priority.get(symbol);
	}
	private Priority() {}
}
