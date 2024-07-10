package com.book.InterviewQuestions;

public class IntegerToRoman {

	public static void main(String[] args) {
		
		convertIntegerToRoman(58);
		convertIntegerToRoman(3749);
	}
	
	public static String convertIntegerToRoman (int num) {
		
		StringBuilder roman = new StringBuilder();
		
		int[] intValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		String[] romanValues = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
		
		for (int i = 0 ; i < intValues.length ; i++) {
			
			while(intValues[i] <= num) {
				num = num - intValues[i];
				roman = roman.append(romanValues[i]);
			}
		}
		
		System.out.println(roman);
		
		return roman.toString();
	}
}
