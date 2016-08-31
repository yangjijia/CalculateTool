package yjj.tool.syntaxparaser;

public class UnallowedException extends Exception{
	
	private String description;
	
	public UnallowedException() {
		super();
	}
	
	public UnallowedException(String description) {
		super();
		this.description=description;
	}
	@Override
	public void printStackTrace() {
		super.printStackTrace();
		System.err.println(description);
	}
}
