package yjj.tool.equationparaser;

/**
 * 记录解析出来的元素以及元素类型，
 * 元素类型包括：整数，小数，符号
 * @author Administrator
 *
 */
public class Element {
	public String element;
	public int type;
	
	public static interface Type{
		public int INT=0;
		public int FLOAT=1;
		public int SYMBOL=2;
	}
	
	public Element(String element,int type) {
		this.element=element;
		this.type=type;
	}
}
