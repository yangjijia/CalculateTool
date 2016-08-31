package yjj.tool.equationparaser.statemodel;

public class NagetiveNumState implements State{
	
	public static final String STATE_NAME="NagetiveNumState";

	@Override
	public State nextState(Context context) {
		//设置下一个状态
		char nextChar=context.getNextChar();
		State nextState=null;
		switch(nextChar){
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
		default:
			nextState=new InitState();
		}
		context.getCurrentCharSet().add(context.getCurrentChar());
		if(nextChar!='\0'){
			context.moveToNextChar();
		}
		context.setLastStateName(STATE_NAME);
		return nextState;
	}

}
