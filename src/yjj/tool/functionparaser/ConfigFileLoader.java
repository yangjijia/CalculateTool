package yjj.tool.functionparaser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 将配置文件中的方法名和类型读取出来，存储到operatorMap这个表中
 * @author Administrator
 *
 */
public class ConfigFileLoader {
	
	public static final String CONFIG_FILE_NAME="config.ini";
	
	static{//加载类文件
		loader=getInstance();
	}
	
	private static ConfigFileLoader loader;
	private Map<String,String> operatorMap=new HashMap<String,String>();//key=运算函数的名称；value=所需要加载的类名
	
	private ConfigFileLoader() {
		loadIniFile(CONFIG_FILE_NAME);
	}
	
	public Map<String, String> getOperatorMap() {
		return operatorMap;
	}
	
	public static ConfigFileLoader getInstance(){
		if(loader==null){
			synchronized(ConfigFileLoader.class){
				if(loader==null){
					loader=new ConfigFileLoader();
				}
			}
		}
		return loader;
	}
	
	/**
	 * 把ini文件中的运算符号以及所需要加载的类加入运算符号表中
	 * @param filePath ini文件路径
	 */
	private void loadIniFile(String filePath){
		try {
			FileInputStream fis=new FileInputStream(filePath);
			Scanner sc=new Scanner(fis);
			String line=null;
			while(sc.hasNextLine()){
				line=sc.nextLine();
				String[] contents;
				if(line==null||line.isEmpty()||line.charAt(0)=='#'){//如果为空行，或者是注释，则跳过该行
					continue;
				}else{
					line=line.trim();
					contents=line.split(" ");
				}
				operatorMap.put(contents[0], contents[1]);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ConfigFileLoader cfl=ConfigFileLoader.getInstance();
		cfl.loadIniFile(CONFIG_FILE_NAME);
		System.out.println(cfl.getOperatorMap());
	}
}
