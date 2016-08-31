package yjj.tool.equationcalculate;

public class Main {
	public static void main(String[] args) {
		String inputString="3+2*(5-2.5)";//支持括号和小数
		String inputString2="jiecheng(mod(2,4)+1)";//jiecheng是自定义的，详见config.ini
		System.out.println(Calculator.getResult(inputString));
		System.out.println(Calculator.getResult(inputString2));
	}
}
