package sait.control.main;

public class Utility {
	/*
	 * Diese Methode ersetzt alle Vorkommnisse von "ReplaceThis" mit "WithThat".
	 * (In einem String-Array.)
	 */
	public static String[] cutString(String[] WorkingArray, String ReplaceThis, String WithThat){

		String[] WorkingArrayCopy = (String[])WorkingArray.clone();
        for (int i = 0; i < WorkingArrayCopy.length; i ++) {
        	WorkingArrayCopy[i] = WorkingArrayCopy[i].replaceAll(ReplaceThis, WithThat);
    }
		return WorkingArrayCopy;

	}
}
