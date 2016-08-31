package yjj.tool.equationparaser.statemodel;

public class LeftBoundState implements State{
	
	public static final String STATE_NAME="LeftBoundState";

	@Override
	public State nextState(Context context) {
		//设置下一个状态
		char currentChar=context.getCurrentChar();
		State nextState=null;
		switch(currentChar){
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
		case '(':
			nextState=new LeftBracketState();
			break;
		case '-':
			nextState=new NagetiveNumState();
			break;
		case '0':
			nextState=new ZeroState();
			break;
		case '+':
		case '*':
		case '/':
		case ')':
			nextState=new SymbolState();
			break;	
		default:
			nextState=new InitState();
		}
		context.setLastStateName(STATE_NAME);
		return nextState;
	}

}
