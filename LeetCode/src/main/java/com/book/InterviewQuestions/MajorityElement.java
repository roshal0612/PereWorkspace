package com.book.InterviewQuestions;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MajorityElement {

	public static void main(String[] args) {

		int[] nums = { 3, 2, 3 };
		findMajorityElement(nums);

		int[] nums1 = { 2, 2, 1, 1, 1, 2, 2 };
		findMajorityElement(nums1);
	}

	public static int findMajorityElement(int[] nums) {
		int l = nums.length;

		Integer majorityElement = Arrays.stream(nums).boxed()
				.collect(Collectors.groupingBy(n -> n, Collectors.counting())).entrySet().stream()
				.filter(e -> e.getValue() > (l / 2)).map(e -> e.getKey()).findFirst().get();

		System.out.println(majorityElement);

		return majorityElement;
	}

	public int majorityElement1(int[] nums) {

		int candidate = nums[0];
		int count = 1;

		for (int i = 1; i < nums.length; i++) {
			if (nums[i] == candidate) {
				count++;
			} else {
				count--;
				if (count == 0) {
					candidate = nums[i];
					count = 1;
				}
			}
		}

		return candidate;
	}
}
