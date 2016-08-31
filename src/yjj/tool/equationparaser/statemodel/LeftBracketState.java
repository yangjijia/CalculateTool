package yjj.tool.equationparaser.statemodel;

public class LeftBracketState implements State{
	
	public static final String STATE_NAME="LeftBracketState";

	@Override
	public State nextState(Context context) {
		//设置下一个状态
		char nextChar=context.getNextChar();
		State nextState=null;
		switch(nextChar){
		case '-':
			nextState=new NagetiveNumState();
			break;
		default:
			nextState=new InitState();
		}
		context.getCurrentCharSet().add(context.getCurrentChar());
		if(nextChar!='\0'){
			context.moveToNextChar();
		}
		context.setLastStateName(STATE_NAME);
		context.saveCurrentWord();
		return nextState;
	}

}
