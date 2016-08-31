package yjj.selffunctions;

public class Square {
	public static Double square(Double i){
		return i*i;
	}
	
	public static Double sum(Double[] i){
		double result=0;
		for(double r:i){
			result+=r;
		}
		return result;
	}
	
	public static Double jiecheng(Double i){
		double result=1;
		while(i>1){
			result*=i--;
		}
		return result;
	}
	
	public static Double mod(Double i ,Double j){
		return i%j;
	}
	
	public static Double fun(Double i){
		return ++i;
	}
	
	public static Double funM(Double i){
		return --i;
	}
	
	public static void main(String[] args) {
		//System.out.println(sum(1.0,2.0,3.0,4.0));
	}
}
