package utils;

public class ValidatorUtils {

	/**
	 * Comprueba si un string está dentro de una lista
	 * @param str
	 * @param list
	 * @return
	 */
	public static boolean stringInLowerCase(String str, String...list){
		if(str == null) throw new IllegalArgumentException();
		String strLowerCase = str.toLowerCase();
		for(String s : list){
			if(strLowerCase.equals(s.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
}
