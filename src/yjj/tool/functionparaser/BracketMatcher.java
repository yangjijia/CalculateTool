package yjj.tool.functionparaser;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BracketMatcher {
	
	/**
	 * »ñµÃÊäÈë×Ö·û´®µÄ×óÓÒÀ¨ºÅÆ¥ÅäµÄÎ»ÖÃ
	 * @param inputString ÊäÈë×Ö·û´®
	 * @return key=×óÀ¨ºÅÎ»ÖÃ£»value=ÓÒÀ¨ºÅÎ»ÖÃ
	 */
	public static Map<Integer,Integer> getBrackPositionsOfString(String inputString){
		Map<Integer,Integer> brackPositionsForMatched=new HashMap<Integer,Integer>();
		Stack<Integer> stack=new Stack<Integer>();
		for(int i=0;i<inputString.length();i++){
			if(inputString.charAt(i)=='('){
				stack.push(i);
				continue;
			}
			if(inputString.charAt(i)==')'){
				int leftPosition=stack.pop();
				brackPositionsForMatched.put(leftPosition, i);
				continue;
			}
		}
		return brackPositionsForMatched;
	}
}
