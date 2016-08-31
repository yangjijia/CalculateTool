package yjj.tool.equationparaser.statemodel;

public class ZeroState implements State{
	
	public static final String STATE_NAME="ZeroState";

	@Override
	public State nextState(Context context) {
		//设置下一个状态
		char nextChar=context.getNextChar();
		State nextState=null;
		switch(nextChar){
		case '.':
			nextState=new FloatNumState();
			break;
		default:
			nextState=new InitState();
		}
		context.getCurrentCharSet().add(context.getCurrentChar());
		context.moveToNextChar();
		context.setLastStateName(STATE_NAME);
		return nextState;
	}

}
