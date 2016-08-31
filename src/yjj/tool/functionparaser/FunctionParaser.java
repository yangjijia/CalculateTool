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
	 * �������ֵ����ʼ����ֹλ��
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
	 * ��������������ֵ��λ��
	 * @param inputString �����ַ���
	 * @return �����ĸ�������λ��
	 */
	public static ArrayList<ArgPosition> getArgsPosition(String inputString){
		Map<Integer,Integer> bracketPositions=BracketMatcher.getBrackPositionsOfString(inputString);//�����ַ���������λ��
		ArrayList<ArgPosition> argsPosition=new ArrayList<ArgPosition>();//�������������¼��λ��
		//���������ŵ�λ�ã������ű��е�Ԫ�ؽ�������,��������
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
		//���Ҳ����ķָ���","��λ��;�Զ������ڲ�������
		ArrayList<Integer> separators=new ArrayList<Integer>();
		int pointer=list.get(0).getKey()+1;
		while(pointer<list.get(0).getValue()){
			if(inputString.charAt(pointer)=='('){//���Ҷ�Ӧ������λ�ã�����
				pointer=bracketPositions.get(pointer)+1;
				continue;
			}
			if(inputString.charAt(pointer)==','){
				separators.add(pointer);				
			}
			pointer++;
		}
		//�����ţ��ָ�����������λ��
		ArrayList<Integer> nodePositions=new ArrayList<Integer>();
		nodePositions.add(list.get(0).getKey());//������λ��
		nodePositions.addAll(separators);//�ָ���λ��
		nodePositions.add(list.get(0).getValue());//������λ��
		//�������������λ��
		for(int i=0;i<nodePositions.size()-1;i++){
			argsPosition.add(new ArgPosition(nodePositions.get(i)+1,nodePositions.get(i+1)-1));
		}
		return argsPosition;
	}
	
	/**
	 * ������뺯���Ĳ���
	 * @param inputString �����ַ���
	 * @return �����ĸ�������
	 */
	public static ArrayList<String> getArgs(String inputString){
		ArrayList<String> args=new ArrayList<String>();//�����ַ����ĸ�������
		ArrayList<ArgPosition> argPositions=getArgsPosition(inputString);//����������λ��
		for(ArgPosition arg:argPositions){
			args.add(inputString.substring(arg.leftPosition, arg.rightPosition+1));
		}
		/*Map<Integer,Integer> bracketPositions=BracketMatcher.getBrackPositionsOfString(inputString);//�����ַ���������λ��
		//���������ŵ�λ�ã������ű��е�Ԫ�ؽ�������,��������
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
		//���Ҳ����ķָ���","��λ��;
		ArrayList<Integer> separators=new ArrayList<Integer>();
		int pointer=list.get(0).getKey()+1;
		while(pointer<list.get(0).getValue()){
			if(inputString.charAt(pointer)=='('){//���Ҷ�Ӧ������λ�ã�����
				pointer=bracketPositions.get(pointer)+1;
				continue;
			}
			if(inputString.charAt(pointer)==','){
				separators.add(pointer);				
			}
			pointer++;
		}
		//����������args��
		if(separators.isEmpty()){
			args.add(inputString.substring(list.get(0).getKey()+1, list.get(0).getValue()));
		}else{
			//nodePositions�ǹؼ��ڵ㣬����������������λ�úͷָ�������λ��
			//ͨ����ȡ�ַ����ķ�ʽ��ȡ�����Ĳ���
			 
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
	 * ������ַ���Ϊ�Զ��庯��ʽ���Զ��庯��ʽ�Ĳ�������Ϊ�������ͣ����������
	 * @param inputString 
	 * @return
	 */
	public static String calculateFunctionString(String inputString){
		//��ò���
		ArrayList<String> args=getArgs(inputString);
		Double[] doubleArgs=new Double[args.size()];
		for(int i=0;i<doubleArgs.length;i++){
			doubleArgs[i]=Double.valueOf(args.get(i));
		}
		//��ú�������
		Pattern p=Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*");
		Matcher m=p.matcher(inputString);
		m.find();
		String funName=m.group();
		//������
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
			// TODO �Զ����ɵ� catch ��
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
