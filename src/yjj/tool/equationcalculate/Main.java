package yjj.tool.equationcalculate;

public class Main {
	public static void main(String[] args) {
		String inputString="3+2*(5-2.5)";//֧�����ź�С��
		String inputString2="jiecheng(mod(2,4)+1)";//jiecheng���Զ���ģ����config.ini
		System.out.println(Calculator.getResult(inputString));
		System.out.println(Calculator.getResult(inputString2));
	}
}
