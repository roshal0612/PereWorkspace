package com.leetcode75;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ReverseVowelsInAString {

	public static void main(String[] args) {
		String str1 = "hello";
		System.out.println("reverse : " + reverseVowelsInAString(str1));
		System.out.println("reverse : " + reverseVowelsInStringWithStream(str1));
		
		String str2 = "leetcode";
		System.out.println("reverse : " + reverseVowelsInAString(str2));
		System.out.println("reverse : " + reverseVowelsInStringWithStream(str2));
		
		String str3 = "aA";
		System.out.println("reverse : " + reverseVowelsInAString(str3));
		System.out.println("reverse : " + reverseVowelsInStringWithStream(str3));
	}
	
	public static String reverseVowelsInAString(String str) {
		
		List<Character> vowels = List.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
		List<Integer> indexes = new ArrayList<>();
		List<Character> vowelsInString = new ArrayList<>();
		StringBuilder reverse = new StringBuilder(str);
		
		char[] charArray = str.toCharArray();
		int len = str.length();
		for (int i = 0 ; i < len ; i++) {
			char v = str.charAt(i);
			if (vowels.contains(v)) {
				indexes.add(i);
				vowelsInString.add(0,v);
			}
		}
		
		for (int i = 0 ; i < indexes.size(); i++) {
			int pos = indexes.get(i);
			reverse.replace(pos, pos+1, vowelsInString.get(i) + "");
		}
		
		return reverse.toString();
	}
	
	public static String reverseVowelsInStringWithStream(String str) {
		List<Character> vowels = List.of('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
		List<Character> vowelsInString = new ArrayList<>();
		StringBuilder reverse = new StringBuilder();
		
		vowelsInString = str.chars()
			.mapToObj(c -> (char) c)
			.filter(vowels::contains)
			.collect(Collectors.toList());
		
		Collections.reverse(vowelsInString);
		
		Iterator<Character> vowelIterator = vowelsInString.iterator();
		
		str.chars()
			.mapToObj(c -> (char) c)
			.forEach(c -> {
				 if (vowels.contains(c)) 
					 reverse.append(vowelIterator.next());
					 else
					 reverse.append(c);
			});
		
		return reverse.toString();
	}
	
}
