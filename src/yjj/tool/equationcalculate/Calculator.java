package yjj.tool.equationcalculate;

import java.util.ArrayList;

import yjj.tool.equationparaser.ParaseException;
import yjj.tool.functionparaser.CalculateFunction;
import yjj.tool.functionparaser.FunctionParaser;
import yjj.tool.functionparaser.Tool;
import yjj.tool.functionparaser.Tool.FunctionPosition;
import yjj.tool.syntaxparaser.Paraser;
import yjj.tool.syntaxparaser.UnallowedException;

/**
 * 可以计算普通运算式和自定义函数式的计算解析工具
 * @author Administrator
 *
 */
public class Calculator {
	static{
		try {
			Class.forName("yjj.tool.functionparaser.ConfigFileLoader");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public static String getResult(String inputString){
		if(Tool.isFunction(inputString)){//输入字符串是自定义函数函数模式
			ArrayList<String> args=FunctionParaser.getArgs(inputString);
			ArrayList<String> argsResult=new ArrayList<String>();
			for(int i=0;i<args.size();i++){//依次计算每个参数的结果
				String tmpResult=getResult(args.get(i));
				argsResult.add(tmpResult);
			}
			ArrayList<FunctionParaser.ArgPosition> argPositions=FunctionParaser.getArgsPosition(inputString);//每个参数的位置
			int modify=0;//因为替换字符串时前面的字符时，会造成后面的替换位置发生变动。所以替换时应该加上这个修正参数。
						//修正参数计算方法 modify=替换字符串长度-原字符串长度
			StringBuilder sb=new StringBuilder(inputString);
			for(int i=0;i<args.size();i++){//替换字符串
				int leftPosition=argPositions.get(i).leftPosition;
				int rightPosition=argPositions.get(i).rightPosition+1;
				sb.replace(leftPosition+modify, rightPosition+modify, argsResult.get(i));
				modify=argsResult.get(i).length()-args.get(i).length();
			}
			return FunctionParaser.calculateFunctionString(sb.toString());
		}else{//输入字符串是普通运算式
			ArrayList<FunctionPosition> funPositions=Tool.getFunctionPositions(inputString);//获得字符串中自定义函数的位置
			if(!funPositions.isEmpty()){//输入中包含自定义函数
				ArrayList<String> subFunctionString=new ArrayList<String>();//存储截取的自定义函数字符串
				ArrayList<String> funResult=new ArrayList<String>();//存储自定义函数的运算结果
				for(int i=0;i<funPositions.size();i++){//截取自定义函数的字符串,如1+fun(1,2),截取出fun(1,2)
					subFunctionString.add(inputString.substring(funPositions.get(i).leftBoundary, funPositions.get(i).rightBoundary+1));
				}
				for(int i=0;i<subFunctionString.size();i++){//计算自定义函数子串结果
					String tmpResult=getResult(subFunctionString.get(i));
					funResult.add(tmpResult);
				}
				int modify=0;//修正参数
				StringBuilder sb=new StringBuilder(inputString);
				for(int i=0;i<funResult.size();i++){//替换字符串
					int leftPosition=funPositions.get(i).leftBoundary;
					int rightPosition=funPositions.get(i).rightBoundary+1;
					sb.replace(leftPosition+modify, rightPosition+modify, "("+funResult.get(i)+")");
					modify=funResult.get(i).length()-subFunctionString.get(i).length()+2;
				}
				inputString=sb.toString();
			}
			//简单的计算表达式，可以直接计算
			String result=null;
			try {
				result=Float.valueOf(Paraser.calculate(inputString)).toString();				
			} catch (ParaseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (UnallowedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return result;			
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getResult("(mod(4,jiecheng(3))+1)*mod(2,2+1)"));
	}
}
