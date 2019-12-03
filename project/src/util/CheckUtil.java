package project.src.util;

public class CheckUtil {
	
	public static final int LOGIN = 1;

	public static final int CLIENT_MESSAGE = 2;
	
	public static final int SERVER_MESSAGE = 3;
	
	public static final int USER_LIST = 4;
	
	public static final int CLOSE_CLIENT_WINDOW = 5;
	
	public static final int CLOSE_SERVER_WINDOW = 6;
	
	public static final int CLOSE_CLIENT_WINDOW_CONFIRMATION = 7;
	
	
	public static boolean isEmpty(String str){
		if("".equals(str)){
			return true;
		}
		return false;
	}	
	
	public static boolean isNumber(String str){
		for(int i=0; i<str.length();  i++){
			if(!(Character.isDigit(str.charAt(i)))){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isBigThan65535(String str){
		if(Integer.parseInt(str)<=65535&&Integer.parseInt(str)>=1024){
			return false;
		}else{
			return true;
		}
		
	}
	
}
