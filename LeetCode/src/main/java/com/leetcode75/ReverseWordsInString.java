package com.leetcode75;

import java.util.Arrays;

public class ReverseWordsInString {

	public static void main(String[] args) {
		
		String b = "example   ";
		System.out.println(b.trim());
		System.out.println(b.replace(" ", "7"));
				
		String s = "the sky is blue";
		System.out.println(reverseWords(s));
		
		s = "  hello world  ";
		System.out.println(reverseWords(s));
		
		s = "a good   example";
		System.out.println(reverseWords(s));
	}

	public static String reverseWords(String s) {

		String[] sArr = s.split(" ");
		StringBuilder reverse = new StringBuilder();
		int len = sArr.length;
		System.out.println(Arrays.toString(sArr));
		
		for (int i = 0; i < len; i++) {
			String word = sArr[len - i - 1];
			if (word.isEmpty()) {
				continue;
			}
			reverse.append(word.trim() + " ");
		}

		return reverse.toString().substring(0, reverse.length() - 1);
	}
}
