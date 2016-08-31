package yjj.tool.equationparaser.statemodel;

import yjj.tool.equationparaser.ParaseException;

public class SymbolState implements State{
	
	public static final String STATE_NAME="SymbolState";

	@Override
	public State nextState(Context context) throws ParaseException {
		//设置下一个状态
		State nextState=new InitState();
		context.getCurrentCharSet().add(context.getCurrentChar());
		context.moveToNextChar();
		context.setLastStateName(STATE_NAME);
		return nextState;
	}

}
