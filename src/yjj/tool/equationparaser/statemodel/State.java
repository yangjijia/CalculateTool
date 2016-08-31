package yjj.tool.equationparaser.statemodel;

import yjj.tool.equationparaser.ParaseException;

/**
 * 词法分析中的各个状态，包括左括号态，负数态，零态，整数态和小数态
 * @author Administrator
 *
 */
public interface State {
	public State nextState(Context context)throws ParaseException;
}
