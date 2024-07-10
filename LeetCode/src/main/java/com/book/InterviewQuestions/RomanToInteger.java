package com.book.InterviewQuestions;

import java.util.Map;

public class RomanToInteger {

	public static void main(String[] args) {
	
		romanToInt("IV");
		romanToInt("LVIII");
		romanToInt("MCMXCIV");
	}
	
	public static void romanToInt (String s) {
		
//		Map<Character, Integer> romanSet = new HashMap<>();
//		
//		romanSet.put('I', 1);
//		romanSet.put('V', 5);
		
		Map<Character, Integer> romanSet = Map.of('I', 1, 'V', 5, 'X', 10, 'L', 50, 'C', 100, 'D', 500, 'M', 1000);
		int integerValue = 0;
		int n = s.length();
		
		for(int i = 0 ; i < n ; i++) {
			int value = romanSet.get(s.charAt(i));
			
			if (i < n-1 && value < romanSet.get(s.charAt(i+1)))
				integerValue -= value;
			else 
				integerValue += value;
		}

		System.out.println(integerValue);
	}
	
	public static void romanToInt1 (String s) { //old
		
		String str = "IIX";
        
        char[] chars = str.toCharArray();
        int value = 0;
        
        // Map<String, Integer> romanMap = new HashMap<>();
        // romanMap.put("I", 1);
        // romanMap.put("V", 5);
        // romanMap.put("X", 10);
        
        if (str.contains("V"))
        	value = 5;
        else if (str.contains("X"))
        		value = 10;
    
        for (int i = 1 ; i < chars.length ; i++){
        		if (chars[i] < chars[i-1] || chars[i] == chars[i-1])
            	value += 1;
            else 
            	value -= 1;
        }
        
        System.out.println(value);
	}
}
