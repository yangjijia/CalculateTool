package yjj.tool.functionparaser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * �������ļ��еķ����������Ͷ�ȡ�������洢��operatorMap�������
 * @author Administrator
 *
 */
public class ConfigFileLoader {
	
	public static final String CONFIG_FILE_NAME="config.ini";
	
	static{//�������ļ�
		loader=getInstance();
	}
	
	private static ConfigFileLoader loader;
	private Map<String,String> operatorMap=new HashMap<String,String>();//key=���㺯�������ƣ�value=����Ҫ���ص�����
	
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
	 * ��ini�ļ��е���������Լ�����Ҫ���ص������������ű���
	 * @param filePath ini�ļ�·��
	 */
	private void loadIniFile(String filePath){
		try {
			FileInputStream fis=new FileInputStream(filePath);
			Scanner sc=new Scanner(fis);
			String line=null;
			while(sc.hasNextLine()){
				line=sc.nextLine();
				String[] contents;
				if(line==null||line.isEmpty()||line.charAt(0)=='#'){//���Ϊ���У�������ע�ͣ�����������
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
