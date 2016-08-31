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
 * ���Լ�����ͨ����ʽ���Զ��庯��ʽ�ļ����������
 * @author Administrator
 *
 */
public class Calculator {
	static{
		try {
			Class.forName("yjj.tool.functionparaser.ConfigFileLoader");
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	
	public static String getResult(String inputString){
		if(Tool.isFunction(inputString)){//�����ַ������Զ��庯������ģʽ
			ArrayList<String> args=FunctionParaser.getArgs(inputString);
			ArrayList<String> argsResult=new ArrayList<String>();
			for(int i=0;i<args.size();i++){//���μ���ÿ�������Ľ��
				String tmpResult=getResult(args.get(i));
				argsResult.add(tmpResult);
			}
			ArrayList<FunctionParaser.ArgPosition> argPositions=FunctionParaser.getArgsPosition(inputString);//ÿ��������λ��
			int modify=0;//��Ϊ�滻�ַ���ʱǰ����ַ�ʱ������ɺ�����滻λ�÷����䶯�������滻ʱӦ�ü����������������
						//�����������㷽�� modify=�滻�ַ�������-ԭ�ַ�������
			StringBuilder sb=new StringBuilder(inputString);
			for(int i=0;i<args.size();i++){//�滻�ַ���
				int leftPosition=argPositions.get(i).leftPosition;
				int rightPosition=argPositions.get(i).rightPosition+1;
				sb.replace(leftPosition+modify, rightPosition+modify, argsResult.get(i));
				modify=argsResult.get(i).length()-args.get(i).length();
			}
			return FunctionParaser.calculateFunctionString(sb.toString());
		}else{//�����ַ�������ͨ����ʽ
			ArrayList<FunctionPosition> funPositions=Tool.getFunctionPositions(inputString);//����ַ������Զ��庯����λ��
			if(!funPositions.isEmpty()){//�����а����Զ��庯��
				ArrayList<String> subFunctionString=new ArrayList<String>();//�洢��ȡ���Զ��庯���ַ���
				ArrayList<String> funResult=new ArrayList<String>();//�洢�Զ��庯����������
				for(int i=0;i<funPositions.size();i++){//��ȡ�Զ��庯�����ַ���,��1+fun(1,2),��ȡ��fun(1,2)
					subFunctionString.add(inputString.substring(funPositions.get(i).leftBoundary, funPositions.get(i).rightBoundary+1));
				}
				for(int i=0;i<subFunctionString.size();i++){//�����Զ��庯���Ӵ����
					String tmpResult=getResult(subFunctionString.get(i));
					funResult.add(tmpResult);
				}
				int modify=0;//��������
				StringBuilder sb=new StringBuilder(inputString);
				for(int i=0;i<funResult.size();i++){//�滻�ַ���
					int leftPosition=funPositions.get(i).leftBoundary;
					int rightPosition=funPositions.get(i).rightBoundary+1;
					sb.replace(leftPosition+modify, rightPosition+modify, "("+funResult.get(i)+")");
					modify=funResult.get(i).length()-subFunctionString.get(i).length()+2;
				}
				inputString=sb.toString();
			}
			//�򵥵ļ�����ʽ������ֱ�Ӽ���
			String result=null;
			try {
				result=Float.valueOf(Paraser.calculate(inputString)).toString();				
			} catch (ParaseException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (UnallowedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			return result;			
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getResult("(mod(4,jiecheng(3))+1)*mod(2,2+1)"));
	}
}
