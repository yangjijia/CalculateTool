package yjj.tool.functionparaser;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
	
	/**
	 * �ж������ַ����Ƿ���һ���Զ��庯��
	 * @param inputString
	 * @return
	 */
	public static boolean isFunction(String inputString){
		Pattern p=Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*");
		Matcher m=p.matcher(inputString);
		if(m.find()){
			int leftBracketPointer=m.end();//���������ŵ�λ��
			Map<Integer,Integer> brackPositions=BracketMatcher.getBrackPositionsOfString(inputString);//���inputString���������ŵ�λ��
			int rightBracketPointer=brackPositions.get(leftBracketPointer);
			return (rightBracketPointer==(inputString.length()-1));
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ�Ϊ��ͨ��������ʽ
	 * @param equation
	 * @return
	 */
	public static boolean isNormalEquation(String equation){
		
		Pattern p=Pattern.compile("^[[0-9]|(|-]");
		Matcher m=p.matcher(equation);
		return m.find();
	}
	
	/**
	 * �������ַ����е�function��ȡ��������FunctionPosition�ڴ����function����ʼ����ֹλ��
	 * @param inputString
	 * @return
	 */
	public static ArrayList<FunctionPosition> getFunctionPositions(String inputString){
		ArrayList<FunctionPosition> functionPositions=new ArrayList<FunctionPosition>();//�洢inputString��function����ʼ����ֹλ��
		Map<Integer,Integer> bracketPositions=BracketMatcher.getBrackPositionsOfString(inputString);//�洢�������ŵ����Ҷ�Ӧ��ϵ
		//����function����ʼλ��
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
	 * �洢�ַ����е�function����ʼ����ֹλ��,�ֱ��Ӧ��leftBoundary��rightBoundary
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
