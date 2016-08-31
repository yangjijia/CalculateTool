package yjj.tool.equationparaser.statemodel;

public class IntegerState implements State{
	
	public static final String STATE_NAME="IntegerState";

	@Override
	public State nextState(Context context) {
		//������һ��״̬
		char nextChar=context.getNextChar();
		State nextState=null;
		switch(nextChar){
		case '0':
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
