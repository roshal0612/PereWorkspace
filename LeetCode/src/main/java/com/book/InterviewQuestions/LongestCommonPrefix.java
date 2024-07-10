package com.book.InterviewQuestions;

import java.util.Arrays;
import java.util.Comparator;

public class LongestCommonPrefix {

	public static void main(String[] args) {

		String[] strs = { "flower", "flow", "flight" };
		findLongestCommonPrefix(strs);
		
		String[] strs1 = {"ab", "a"};
		findLongestCommonPrefix(strs1);
		
		String[] strs3 = {"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(strs3)); // Output: "fl"

        String[] strs2 = {"dog", "racecar", "car"};
        System.out.println(longestCommonPrefix(strs2)); // Output: ""

	}

	public static String findLongestCommonPrefix(String[] strs) {

		StringBuilder prefix = new StringBuilder("");		
		boolean flag = false;

		int minLength = Arrays.stream(strs).map(s -> s.length()).min(Comparator.comparing(Integer::intValue)).get();

		for (int j = 0; j < minLength ; j++) {
			
			char testChar = strs[0].charAt(j);

			for (int i = 1; i < strs.length ; i++) {
				
				char testChar1 = strs[i].charAt(j);
				
				if ( !(testChar == testChar1) ) {
					flag = true;
					break;
				}
			}
			
			if (flag)
				break;

			prefix.append(testChar);
		}

		System.out.println(prefix.toString());

		return prefix.toString();
	}
	
	
	public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // Start with the first string as the prefix
        String prefix = strs[0];

        // Compare the prefix with each string in the array
        for (int i = 1; i < strs.length; i++) {
            // Shorten the prefix until it matches the beginning of the current string
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }
}
