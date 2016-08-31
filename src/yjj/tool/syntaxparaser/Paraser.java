package yjj.tool.syntaxparaser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import yjj.tool.equationparaser.Element;
import yjj.tool.equationparaser.ParaseException;

public class Paraser {

	private ArrayList<Element> elements;
	private ArrayList<BracketPosition> bracketPositions=new ArrayList<BracketPosition>();
	private HashMap<Integer,Element> symbolPositions=new HashMap<Integer,Element>();
	//��¼���﷨�������ĺ�������ķ�������
	private ArrayList<Element> suffix=new ArrayList<Element>();
	private static class ElementNode{
		public Element root;
		public ElementNode leftNode;
		public ElementNode rightNode;
		
		public ElementNode(Element root) {
			this.root=root;
		}
		
		public ElementNode() {}
	}
	
	private static class BracketPosition{
		public int leftBracket;
		public int rightBracket;
		public BracketPosition(int leftBracket,int rightBracket) {
			this.leftBracket=leftBracket;
			this.rightBracket=rightBracket;
		}
		
		/**
		 * �Ƿ��ڵ�ǰ���������
		 * @param position
		 * @return
		 */
		public boolean isInBracket(int position){
			if(leftBracket<position&&position<rightBracket){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public Paraser(ArrayList<Element> elements) {

		this.elements=elements;
	}
	
	/**
	 * ��������Ƿ�Ϸ�
	 * @return
	 */
	private boolean isBracketAllowed(){
		int balance=0;
		Stack<Integer> stack=new Stack<Integer>();
		
		for(int i=0;i<elements.size();i++){
			if(elements.get(i).element.equals("(")){
				balance++;
				stack.push(i);
				continue;
			}
			if(elements.get(i).element.equals(")")){
				balance--;
				if(stack.empty())
					return false;
				bracketPositions.add(new BracketPosition(stack.pop(), i));
				continue;
			}
		}
		if(balance==0){
			return true;
		}else{
			//System.out.println("balance:"+balance);
			bracketPositions.clear();
			return false;
		}
	}
	
	/**
	 * �����������¼���������λ��
	 */
	private void createSymbolPositionsTable(){
		int pointer=0;
		for(Element e:elements){
			if(e.type==Element.Type.SYMBOL&&!e.element.equals("(")
					&&!e.element.equals(")")){
				symbolPositions.put(pointer, e);
			}
			pointer++;
		}
	}
	
	/**
	 * ���������﷨��
	 * @param start ��ʼλ��
	 * @param end ��ֹλ��
	 * @return �﷨���ĸ��ڵ�
	 */
	private ElementNode createSyntaxTree(int start,int end){
		ElementNode root=new ElementNode();
		//��ͷȥβժ������
		for(BracketPosition bracket:bracketPositions){
			if(bracket.leftBracket==start&&bracket.rightBracket==end){
				start++;
				end--;
				break;
			}
		}
		
		int lowSymbolPosition=getLowestPriortyPosition(start,end);
		if(lowSymbolPosition!=-1){
			Element rootNode=symbolPositions.get(lowSymbolPosition);
			root.root=rootNode;
			
			int tmp_start1=start;
			int tmp_end1=lowSymbolPosition-1;
			int tmp_start2=lowSymbolPosition+1;
			int tmp_end2=end;			
			//��ͷȥβժ������
			for(BracketPosition bracket:bracketPositions){
				if(bracket.leftBracket==tmp_start1&&bracket.rightBracket==tmp_end1){
					tmp_start1++;
					tmp_end1--;
					break;
				}
			}
			for(BracketPosition bracket:bracketPositions){
				if(bracket.leftBracket==tmp_start2&&bracket.rightBracket==tmp_end2){
					tmp_start2++;
					tmp_end2--;
					break;
				}
			}
			root.leftNode=createSyntaxTree(tmp_start1,tmp_end1);
			root.rightNode=createSyntaxTree(tmp_start2,tmp_end2);
		}else{
			root.root=elements.get(start);
		}
		return root;
	}
	
	/**
	 * 
	 * @param root
	 */
	private void postSearch(ElementNode root){
		if(root==null){
			return;
		}else{
			postSearch(root.leftNode);
			postSearch(root.rightNode);
			//System.out.print(root.root.element+",");
			suffix.add(root.root);
		}
	}
	
	/**
	 * �ҵ����ȼ���С��λ��,���򷵻�-1
	 * @param start
	 * @param end
	 * @return
	 */
	private int getLowestPriortyPosition(int start,int end){
		if(start==end){
			return -1;
		}
		//��ͷȥβժ������
		for(BracketPosition bracket:bracketPositions){
			if(bracket.leftBracket==start&&bracket.rightBracket==end){
				start++;
				end--;
				break;
			}
		}
		//������Χ�ڵ�����
		ArrayList<BracketPosition> currentBracketPositions=new ArrayList<BracketPosition>();
		for(BracketPosition position:bracketPositions){
			if(start<=position.leftBracket&&position.leftBracket<=end){
				currentBracketPositions.add(position);
			}
		}
		/*for(BracketPosition b:currentBracketPositions){
			System.out.println(b.leftBracket+","+b.rightBracket);
		}*/
		//������Χ�ڵ������
		HashMap<Integer,Element> currentSymbolPositions=new HashMap<Integer,Element>();
		for(Integer position:symbolPositions.keySet()){
			if(start<=position&&position<=end){
				currentSymbolPositions.put(position, symbolPositions.get(position));
			}
		}
		//System.out.println(currentSymbolPositions.keySet());
		//�ų��������ڵ������
		ArrayList<Integer> document=new ArrayList<Integer>();
		for(Integer position:currentSymbolPositions.keySet()){
			for(BracketPosition bracket:currentBracketPositions){
				if(bracket.isInBracket(position)){
					document.add(position);
				}
			}
		}
		//System.out.println(currentSymbolPositions.size());
		for(Integer integer:document){
			currentSymbolPositions.remove(integer);
		}
		
		//��ȡ���ȼ�
		ArrayList<Integer> symbolPositions=new ArrayList<Integer>();
		for(Integer position:currentSymbolPositions.keySet()){
			symbolPositions.add(position);
		}
		
		for(int i=symbolPositions.size()-1;i>=0;i--){
			if(currentSymbolPositions.get(symbolPositions.get(i)).element.equals("+")||
					currentSymbolPositions.get(symbolPositions.get(i)).element.equals("-")){
				return symbolPositions.get(i);
			}
		}
		return symbolPositions.get(symbolPositions.size()-1);
	}
	
	/**
	 * ���ؼ�����
	 * @return
	 * @throws UnallowedException 
	 */
	public float getResult() throws UnallowedException{
		//�����﷨��
		ElementNode syntaxTree=createSyntaxTree(0,elements.size()-1);
		postSearch(syntaxTree);
		//���ֻ�к��������ֻ��һ�����ţ�˵��������ű�����Ҫ����Ľ��
		if(suffix.size()==1){
			return Float.valueOf(suffix.get(0).element);
		}
		Stack<Element> stack=new Stack<Element>();
		float result=0.0f;
		for(Element e:suffix){
			if(e.type!=Element.Type.SYMBOL){
				stack.push(e);
			}else{
				Element e2=stack.pop();
				Element e1=stack.pop();
				float num2=Float.parseFloat(e2.element);
				float num1=Float.parseFloat(e1.element);
				switch(e.element){
				case "+":
					num1+=num2;
					break;
				case "-":
					num1-=num2;
					break;
				case "*":
					num1*=num2;
					break;
				case "/":
					if(Math.abs(num2)<0.00001f)
						throw new UnallowedException("����Ϊ0");
					num1/=num2;
					break;
				default:
					throw new UnallowedException();
				}
				result=num1;
				Element element=new Element(Float.toString(num1),Element.Type.FLOAT);
				stack.push(element);
			}
		}
		return result;
	}
	
	public static float calculate(String equation) throws ParaseException, UnallowedException{
		Paraser paraser=new Paraser(yjj.tool.equationparaser.Paraser.parase(equation));
		if(!paraser.isBracketAllowed()){
			System.err.println("���Ų��Գ�");
			throw new UnallowedException("���Ų��Գ�");
		}
		paraser.createSymbolPositionsTable();
		float result=paraser.getResult();
		//����3λС��
		result=(float)Math.round(result*1000)/1000;
		return result;
	}
	public static void main(String[] args) throws ParaseException, UnallowedException {
		/*String test="(3+2+3+2*3.4)*5+2";
		Paraser paraser=new Paraser(yjj.tool.equationparaser.Paraser.parase(test));
		for(Element e:paraser.elements){
			System.out.println(e.element);
		}
		System.out.println(paraser.isBracketAllowed());
		for(BracketPosition b:paraser.bracketPositions){
			System.out.println(b.leftBracket+","+b.rightBracket);
		}
		
		paraser.createSymbolPositionsTable();
		Set<Integer> set=paraser.symbolPositions.keySet();
		for(Integer inter:set){
			System.out.println("position:"+inter+"  symbol:"+paraser.elements.get(inter).element);
		}
		System.out.println(paraser.getLowestPriortyPosition(0, paraser.elements.size()-1));
		paraser.postSearch(paraser.createSyntaxTree(0, paraser.elements.size()-1));
		System.out.println("result:"+paraser.getResult());*/
		//System.out.println("������ʽ��((3+8)*(2-1))/3");
		//System.out.println(calculate("20+0+3+2+6+0+4+11+3+26+37+1+1+4+44+0+2+3+1+2+3*2+1+8+10+8+16+7+0+0+3+3"));
		System.out.println(calculate("-3+(-2)"));
	}
}
