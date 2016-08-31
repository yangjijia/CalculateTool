package yjj.tool.equationparaser.statemodel;

import yjj.tool.equationparaser.ParaseException;

public class InitState implements State{
	
	public static final String STATE_NAME="InitState";

	/**
	 * 当返回为null时，说明已到达字符串末尾
	 */
	@Override
	public State nextState(Context context) throws ParaseException {
		
		//保存当前产生的临时字符表
		context.saveCurrentWord();
		//获取下一个状态
		char currentChar=context.getCurrentChar();
		State nextState=null;
		if(currentChar!='\0'){//没有到字符串的末尾
			switch(currentChar){
			case '(':
				nextState=new LeftBracketState();
				break;
			case '0':
				nextState=new ZeroState();
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				nextState=new IntegerState();
				break;
			case '+':
			case '*':
			case '/':
			case '-':
			case ')':
				nextState=new SymbolState();
				break;
			default:
				throw new ParaseException("第"+context.getCurrrentPointer()+"个字符出现了未知的字符");
			}
			context.setLastStateName(STATE_NAME);//设置上一个状态
		}		
		return nextState;
	}

}
