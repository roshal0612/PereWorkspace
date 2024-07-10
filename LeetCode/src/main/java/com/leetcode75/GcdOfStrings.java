package com.leetcode75;

public class GcdOfStrings {

	public static void main(String[] args) {
		
		String str1 = "ABABABAB";
		String str2 = "ABAB";
		String gcdOfStrings = gcdOfStrings2(str1, str2);
		System.out.println("Gcd Of Strings :" + gcdOfStrings);

		str1 = "TAUXXTAUXXTAUXXTAUXXTAUXX";
		str2 = "TAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXX";
		
//		System.out.println(str1.length());
//		System.out.println(str2.length());

		System.out.println();
		gcdOfStrings = gcdOfStrings2(str1, str2);
		System.out.println("Gcd Of Strings :" + gcdOfStrings);
		gcdOfStrings = gcdOfStrings2("ABCABC", "ABC");
		System.out.println("Gcd Of Strings :" + gcdOfStrings);
		gcdOfStrings = gcdOfStrings2("LEET", "CODE");
		System.out.println("Gcd Of Strings :" + gcdOfStrings);
		gcdOfStrings = gcdOfStrings2("ABABAB", "ABAB");
		System.out.println("Gcd Of Strings :" + gcdOfStrings);
	}
	
	public static String gcdOfStrings(String str3, String str4) {

		int maxLength = 0, minLength = 0;
		String largeStr = "";
		String smallStr = "";

		if (str3.length() > str4.length()) {
			maxLength = str3.length();
			minLength = str4.length();
			largeStr = str3;
			smallStr = str4;
		} else {
			maxLength = str4.length();
			minLength = str3.length();
			largeStr = str4;
			smallStr = str3;
		}

		String result = "";

		for (int i = minLength; i > 0; i--) {

			StringBuilder subString = new StringBuilder();
			StringBuilder br = new StringBuilder();
			subString = new StringBuilder(smallStr.substring(0, i));
//			System.out.println(subString);

//			System.out.println("str1" + largeStr);

			while (br.length() <= largeStr.length()) {
				br.append(subString);
//				System.out.println("br " + br);
				
				if (checkIfForming(br.toString(), smallStr) && checkIfForming(br.toString(), largeStr)) {
					result = subString.toString();
//					System.out.println(result);
					break;
				}
			}
			if (result != "")
				break;

		}

		return result;
	}

	public static boolean checkIfForming(String test, String str) {
		int testLength = test.length();
		int strLength = str.length();

		if (strLength % testLength != 0)
			return false;
		String repeated = test.repeat(strLength / testLength);
		
		return str.equals(repeated);
	}
	
	public static String gcdOfStrings2(String str3, String str4) {
		
		int maxLength = 0, minLength = 0;
		String largeStr = "";
		String smallStr = "";

		
		if (str3.length() > str4.length()) {
			maxLength = str3.length();
			minLength = str4.length();
			largeStr = str3;
			smallStr = str4;
		} else {
			maxLength = str4.length();
			minLength = str3.length();
			largeStr = str4;
			smallStr = str3;
		}
		
		int gcdLen = gcd(maxLength, minLength);
		System.out.println(gcdLen);
		String test = smallStr.substring(0,gcdLen);
		System.out.println(test);
		if (checkIfForming(test, smallStr) && checkIfForming(test, largeStr)) {
			return test;
		} 
		
		return "";
	}
	
	static int gcd (int a , int b) {
		
		while(b % a != 0) {
			 int temp = b;
             b = a % b;
             a = temp;
		}
		
		return a;
	}
	
}
