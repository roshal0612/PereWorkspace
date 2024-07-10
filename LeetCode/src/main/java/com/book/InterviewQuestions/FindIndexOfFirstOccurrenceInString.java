package com.book.InterviewQuestions;

public class FindIndexOfFirstOccurrenceInString {

	public static void main(String[] args) {

		int strStr = strStr("sadbutsad", "sad");
		System.out.println(strStr);
	}

	public static int strStr(String haystack, String needle) {

		if (!haystack.contains(needle))
			return -1;

		return haystack.indexOf(needle);
	}
}
