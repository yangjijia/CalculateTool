package yjj.tool.equationparaser.statemodel;

import java.util.ArrayList;

import yjj.tool.equationparaser.Element;
import yjj.tool.equationparaser.ParaseException;

public class Context {
	private State currentState;
	private int currrentPointer;
	private ArrayList<Character> currentCharSet=new ArrayList<Character>();
	private String inputString;
	private ArrayList<Element> wordList=new ArrayList<Element>();
	private String lastStateName;
	
	/**
	 * ���ݵ�ǰɨ�赽��λ�ú͵�ǰ״̬��������һ��״̬
	 * @throws ParaseException 
	 */
	public void setNextState() throws ParaseException{
		currentState=currentState.nextState(this);
	}
	
	/**
	 * ��ʼ��״̬
	 * @param inputString
	 */
	public void init(String inputString){
		this.inputString=inputString;
		currrentPointer=0;
		currentState=new LeftBoundState();
	}
	
	/**
	 * ����ǰcurrentCharSet�е��ַ���ɵ��ʵ�wordList��
	 */
	public void saveCurrentWord(){
		//�����������ʱ�ַ���
		if(!getCurrentCharSet().isEmpty()){
			StringBuilder sb=new StringBuilder();
			ArrayList<Character> tmpCharSet=getCurrentCharSet();
			for(Character c:tmpCharSet){
				sb.append(c);
			}
			//������һ��״ֵ̬��ȡ���ʵ�����
			int type=-1;
			switch(getLastStateName()){
			case IntegerState.STATE_NAME:
				type=Element.Type.INT;
				break;
			case FloatNumState.STATE_NAME:
				type=Element.Type.FLOAT;
				break;
			case LeftBracketState.STATE_NAME:
			case SymbolState.STATE_NAME:
				type=Element.Type.SYMBOL;
				break;
			}
			Element element=new Element(sb.toString(),type);
			getWordList().add(element);
			getCurrentCharSet().clear();//�����ʱ�ַ���
		}		
	}
	
	/**
	 * �����ַ���
	 */
	public void calculate(){
		while(currentState!=null){
			try {
				setNextState();
			} catch (ParaseException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ������ս�������ַ���
	 * @return
	 */
	public ArrayList<Element> getWordList() {
		return wordList;
	}
	
	/**
	 * ��ȡ��ʱ���Ѿ�������ַ�����
	 * @return
	 */
	public ArrayList<Character> getCurrentCharSet() {
		return currentCharSet;
	}
	
	/**
	 * ��ȡ��һ���ַ�������Ѿ���ĩβ������'\0'
	 * @return
	 */
	public char getNextChar(){
		if((currrentPointer+1)<inputString.length()){
			return inputString.charAt(currrentPointer+1);
		}else{
			return '\0';
		}
	}
	
	/**
	 * ��ָ��������һ���ַ�
	 */
	public void moveToNextChar(){
		currrentPointer++;
	}
	
	/**
	 * ��ȡ��ǰ���ַ�
	 * @return
	 */
	public char getCurrentChar(){
		if(currrentPointer<inputString.length())
			return inputString.charAt(currrentPointer);	
		else
			return '\0';
	}

	public String getLastStateName() {
		return lastStateName;
	}

	public void setLastStateName(String lastStateName) {
		this.lastStateName = lastStateName;
	}
	
	public String getInputString() {
		return inputString;
	}
	
	public int getCurrrentPointer(){
		return currrentPointer;
	}
	
	public static void main(String[] args) {
		 Context context=new Context(); 
		 //context.init("(-3.14+2.123)-4.82*4");
		 //context.init("-3.14+5.0*(-3.12)2");
		 //context.init("-3.5-2");
		 context.init("(-3.14");
		 context.calculate();
		 ArrayList<Element> tmp=context.getWordList();
		 for(Element e:tmp){
			 System.out.println(e.element+"   "+e.type);
		 }
	}
}
