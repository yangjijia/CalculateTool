package yjj.tool.functionparaser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FunctionParaser {
	
	/**
	 * 保存参数值的起始和终止位置
	 * @author Administrator
	 *
	 */
	public static class ArgPosition{
		public int leftPosition;
		public int rightPosition;
		public ArgPosition(int leftPosition,int rightPosition) {
			this.leftPosition=leftPosition;
			this.rightPosition=rightPosition;
		}
	}
	
	/**
	 * 获得输入参数参数值的位置
	 * @param inputString 输入字符串
	 * @return 函数的各个参数位置
	 */
	public static ArrayList<ArgPosition> getArgsPosition(String inputString){
		Map<Integer,Integer> bracketPositions=BracketMatcher.getBrackPositionsOfString(inputString);//输入字符串的括号位置
		ArrayList<ArgPosition> argsPosition=new ArrayList<ArgPosition>();//保存各个参数记录的位置
		//根据左括号的位置，对括号表中的元素进行排序,升序排列
		List<Map.Entry<Integer, Integer>> list=new ArrayList<Map.Entry<Integer, Integer>>(bracketPositions.entrySet());
		list.sort(new Comparator<Map.Entry<Integer, Integer>>(){

			@Override
			public int compare(Entry<Integer, Integer> e1,
					Entry<Integer, Integer> e2) {
				if(e1.getKey()==e2.getKey())
					return 0;
				return (e1.getKey()>e2.getKey())?1:-1;
			}
			
		});
		//查找参数的分隔符","的位置;自动跳过内部的括号
		ArrayList<Integer> separators=new ArrayList<Integer>();
		int pointer=list.get(0).getKey()+1;
		while(pointer<list.get(0).getValue()){
			if(inputString.charAt(pointer)=='('){//查找对应右括号位置，跳过
				pointer=bracketPositions.get(pointer)+1;
				continue;
			}
			if(inputString.charAt(pointer)==','){
				separators.add(pointer);				
			}
			pointer++;
		}
		//左括号，分隔符，右括号位置
		ArrayList<Integer> nodePositions=new ArrayList<Integer>();
		nodePositions.add(list.get(0).getKey());//左括号位置
		nodePositions.addAll(separators);//分隔符位置
		nodePositions.add(list.get(0).getValue());//右括号位置
		//保存各个参数的位置
		for(int i=0;i<nodePositions.size()-1;i++){
			argsPosition.add(new ArgPosition(nodePositions.get(i)+1,nodePositions.get(i+1)-1));
		}
		return argsPosition;
	}
	
	/**
	 * 获得输入函数的参数
	 * @param inputString 输入字符串
	 * @return 函数的各个参数
	 */
	public static ArrayList<String> getArgs(String inputString){
		ArrayList<String> args=new ArrayList<String>();//输入字符串的各个参数
		ArrayList<ArgPosition> argPositions=getArgsPosition(inputString);//各个参数的位置
		for(ArgPosition arg:argPositions){
			args.add(inputString.substring(arg.leftPosition, arg.rightPosition+1));
		}
		/*Map<Integer,Integer> bracketPositions=BracketMatcher.getBrackPositionsOfString(inputString);//输入字符串的括号位置
		//根据左括号的位置，对括号表中的元素进行排序,升序排列
		List<Map.Entry<Integer, Integer>> list=new ArrayList<Map.Entry<Integer, Integer>>(bracketPositions.entrySet());
		list.sort(new Comparator<Map.Entry<Integer, Integer>>(){

			@Override
			public int compare(Entry<Integer, Integer> e1,
					Entry<Integer, Integer> e2) {
				if(e1.getKey()==e2.getKey())
					return 0;
				return (e1.getKey()>e2.getKey())?1:-1;
			}
			
		});
		//查找参数的分隔符","的位置;
		ArrayList<Integer> separators=new ArrayList<Integer>();
		int pointer=list.get(0).getKey()+1;
		while(pointer<list.get(0).getValue()){
			if(inputString.charAt(pointer)=='('){//查找对应右括号位置，跳过
				pointer=bracketPositions.get(pointer)+1;
				continue;
			}
			if(inputString.charAt(pointer)==','){
				separators.add(pointer);				
			}
			pointer++;
		}
		//将参数存入args中
		if(separators.isEmpty()){
			args.add(inputString.substring(list.get(0).getKey()+1, list.get(0).getValue()));
		}else{
			//nodePositions是关键节点，包括函数左右括号位置和分隔符，的位置
			//通过截取字符串的方式获取函数的参数
			 
			ArrayList<Integer> nodePositions=new ArrayList<Integer>();
			nodePositions.add(list.get(0).getKey());
			nodePositions.addAll(separators);
			nodePositions.add(list.get(0).getValue());
			for(int i=0;i<nodePositions.size()-1;i++){
				args.add(inputString.substring(nodePositions.get(i)+1, nodePositions.get(i+1)));
			}
		}*/
		return args;
	}
	
	/**
	 * 输入的字符串为自定义函数式，自定义函数式的参数必须为基本类型，输出运算结果
	 * @param inputString 
	 * @return
	 */
	public static String calculateFunctionString(String inputString){
		//获得参数
		ArrayList<String> args=getArgs(inputString);
		Double[] doubleArgs=new Double[args.size()];
		for(int i=0;i<doubleArgs.length;i++){
			doubleArgs[i]=Double.valueOf(args.get(i));
		}
		//获得函数名称
		Pattern p=Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*");
		Matcher m=p.matcher(inputString);
		m.find();
		String funName=m.group();
		//计算结果
		String className=ConfigFileLoader.getInstance().getOperatorMap().get(funName);
		String result=CalculateFunction.getResult(funName, className, doubleArgs);
		return result;
	}
	
	public static void main(String[] args) {
		//System.out.println(isFunction("fun(,fun2())"));
		//System.out.println(getArgs("fun(f(123,23+3),fun2(12,fun(23),11))"));
		//System.out.println(getArgs("fun(1+2,3,5)"));
		/*try {
			Class.forName("yjj.tool.functionparaser.ConfigFileLoader");
			//System.out.println(calculateFunctionString("square(25)"));
			System.out.println(calculateFunctionString("mod(4,3)"));
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
		/*ArrayList<ArgPosition> argsPositions=getArgsPosition("fun(2,3+5)");
		for(ArgPosition argP:argsPositions){
			System.out.println(argP.leftPosition+","+argP.rightPosition);
		}*/
		//ArrayList<String> args2=getArgs("fun(fun(3,4,5,6),3+5)");
		//System.out.println(args2);
		//System.out.println(calculateFunctionString("4*5+2"));
	}
}
