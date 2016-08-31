package yjj.tool.equationparaser.statemodel;

import yjj.tool.equationparaser.ParaseException;

public class InitState implements State{
	
	public static final String STATE_NAME="InitState";

	/**
	 * ������Ϊnullʱ��˵���ѵ����ַ���ĩβ
	 */
	@Override
	public State nextState(Context context) throws ParaseException {
		
		//���浱ǰ��������ʱ�ַ���
		context.saveCurrentWord();
		//��ȡ��һ��״̬
		char currentChar=context.getCurrentChar();
		State nextState=null;
		if(currentChar!='\0'){//û�е��ַ�����ĩβ
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
				throw new ParaseException("��"+context.getCurrrentPointer()+"���ַ�������δ֪���ַ�");
			}
			context.setLastStateName(STATE_NAME);//������һ��״̬
		}		
		return nextState;
	}

}
