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
	 * 根据当前扫描到的位置和当前状态，设置下一个状态
	 * @throws ParaseException 
	 */
	public void setNextState() throws ParaseException{
		currentState=currentState.nextState(this);
	}
	
	/**
	 * 初始化状态
	 * @param inputString
	 */
	public void init(String inputString){
		this.inputString=inputString;
		currrentPointer=0;
		currentState=new LeftBoundState();
	}
	
	/**
	 * 将当前currentCharSet中的字符存成单词到wordList中
	 */
	public void saveCurrentWord(){
		//处理产生的临时字符表
		if(!getCurrentCharSet().isEmpty()){
			StringBuilder sb=new StringBuilder();
			ArrayList<Character> tmpCharSet=getCurrentCharSet();
			for(Character c:tmpCharSet){
				sb.append(c);
			}
			//根据上一个状态值获取单词的类型
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
			getCurrentCharSet().clear();//清空临时字符表
		}		
	}
	
	/**
	 * 产生字符表
	 */
	public void calculate(){
		while(currentState!=null){
			try {
				setNextState();
			} catch (ParaseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获得最终解析后的字符表
	 * @return
	 */
	public ArrayList<Element> getWordList() {
		return wordList;
	}
	
	/**
	 * 获取临时的已经处理的字符表集合
	 * @return
	 */
	public ArrayList<Character> getCurrentCharSet() {
		return currentCharSet;
	}
	
	/**
	 * 获取下一个字符，如果已经到末尾，返回'\0'
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
	 * 将指针移至下一个字符
	 */
	public void moveToNextChar(){
		currrentPointer++;
	}
	
	/**
	 * 获取当前的字符
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
