package yjj.tool.equationparaser;

/**
 * ��¼����������Ԫ���Լ�Ԫ�����ͣ�
 * Ԫ�����Ͱ�����������С��������
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
