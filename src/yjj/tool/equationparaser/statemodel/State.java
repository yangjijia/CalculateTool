package yjj.tool.equationparaser.statemodel;

import yjj.tool.equationparaser.ParaseException;

/**
 * �ʷ������еĸ���״̬������������̬������̬����̬������̬��С��̬
 * @author Administrator
 *
 */
public interface State {
	public State nextState(Context context)throws ParaseException;
}
