package com.book.InterviewQuestions;

public class LengthOfLastWord {

	public static void main(String[] args) {

		String s = "Hello World";
		lengthOfLastWord(s);
		
		System.out.println(lengthOfLastWord("a"));
		
		lengthOfLastWord("   fly me   to   the moon  ");
		lengthOfLastWord("luffy is still joyboy");
	}

	public static int lengthOfLastWord(String s) {

		s = s.trim();
		int lastIndexOf = s.lastIndexOf(" ");
		
		if(lastIndexOf == -1)
			return s.length();
		
		String substring = s.substring(lastIndexOf).trim();
		
		System.out.println(substring.length());

		return substring.length();
	}
}
