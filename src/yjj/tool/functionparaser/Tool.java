package yjj.tool.functionparaser;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
	
	/**
	 * 判断输入字符串是否是一个自定义函数
	 * @param inputString
	 * @return
	 */
	public static boolean isFunction(String inputString){
		Pattern p=Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*");
		Matcher m=p.matcher(inputString);
		if(m.find()){
			int leftBracketPointer=m.end();//函数左括号的位置
			Map<Integer,Integer> brackPositions=BracketMatcher.getBrackPositionsOfString(inputString);//获得inputString中所有括号的位置
			int rightBracketPointer=brackPositions.get(leftBracketPointer);
			return (rightBracketPointer==(inputString.length()-1));
		}
		return false;
	}
	
	/**
	 * 判断是否为普通的运算表达式
	 * @param equation
	 * @return
	 */
	public static boolean isNormalEquation(String equation){
		
		Pattern p=Pattern.compile("^[[0-9]|(|-]");
		Matcher m=p.matcher(equation);
		return m.find();
	}
	
	/**
	 * 把输入字符串中的function提取出来，在FunctionPosition内存的是function的起始和终止位置
	 * @param inputString
	 * @return
	 */
	public static ArrayList<FunctionPosition> getFunctionPositions(String inputString){
		ArrayList<FunctionPosition> functionPositions=new ArrayList<FunctionPosition>();//存储inputString中function的起始和终止位置
		Map<Integer,Integer> bracketPositions=BracketMatcher.getBrackPositionsOfString(inputString);//存储所有括号的左右对应关系
		//计算function的起始位置
		Pattern p=Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");
		Matcher m=p.matcher(inputString);
		int rightBoundary=-1;
		while(m.find()){
			if(m.start()>rightBoundary){
				int leftBoundary=m.start();
				int leftBracketPosition=m.end();
				rightBoundary=bracketPositions.get(leftBracketPosition);
				functionPositions.add(new FunctionPosition(leftBoundary, rightBoundary));
			}
		}
		return functionPositions;
	}
	
	/**
	 * 存储字符串中的function的起始和终止位置,分别对应着leftBoundary和rightBoundary
	 * @author Administrator
	 *
	 */
	public static class FunctionPosition{
		public int leftBoundary;
		public int rightBoundary;
		public FunctionPosition(int leftBoundary,int rightBoundary) {
			this.leftBoundary=leftBoundary;
			this.rightBoundary=rightBoundary;
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(isNormalEquation("-23"));
		ArrayList<FunctionPosition> positions=getFunctionPositions("fun(3+2)+fun(4)");
		for(FunctionPosition p:positions){
			System.out.println(p.leftBoundary+" "+p.rightBoundary);
		}
	}
}
