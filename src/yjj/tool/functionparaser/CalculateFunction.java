package yjj.tool.functionparaser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CalculateFunction {
	
	/**
	 * 根据类名，方法名，以及输入参数，求运算结果。结果以String形式输出
	 * @param funName 方法名
	 * @param ClassName 类名（包括包名）
	 * @param args 方法所需要的参数
	 * @return 运算结果
	 */
	public static String getResult(String funName,String className,Object[] args){
		String result=null;
		try {
			Class classInstance=Class.forName(className);
			Class[] argTypes=null;
			if(args!=null){
				argTypes=new Class[args.length];
				for(int i=0;i<argTypes.length;i++){
					argTypes[i]=args[i].getClass();
				}
			}
			Method method=classInstance.getMethod(funName,argTypes);
			double dResult=(double) method.invoke(null, args);
			result=Double.valueOf(dResult).toString();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(getResult("square","yjj.tool.funcalculator.Square",new Object[]{Double.valueOf(3)}));
	}
}
