package yjj.tool.equationparaser;

import java.io.PrintStream;

public class ParaseException extends Exception{

	String exception;
	public ParaseException(String exception) {
		super();
		this.exception=exception;
	}
	
	public void printParaseExcetption(){
		System.err.println(exception);
	}
	
	@Override
	public void printStackTrace(PrintStream s) {
		// TODO 自动生成的方法存根
		super.printStackTrace(s);
		printParaseExcetption();
	}
}
