package com.book.InterviewQuestions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicatesII {

	public static void main(String[] args) {

		int[] nums = { 1, 1, 1, 2, 2, 3 };
		removeDuplicates(nums);

		int[] nums1 = { 0, 0, 1, 1, 1, 1, 2, 3, 3 };
		removeDuplicates(nums1);

	}

	public static int removeDuplicates(int[] nums) {

		int l = nums.length;
		int k = 2;

		if (nums.length <= 2) return nums.length;

		for (int i = 2; i < l ; i++) {
			if (nums[i] != nums[k - 2]) {
				nums[k] = nums[i];
				k++;
			} 
		}
		

		System.out.println(Arrays.toString(nums));
		System.out.println(k + "\n");
		return k;
	}

	public static int removeDuplicates2(int[] nums) {

		int k = 0;

		List<Integer> list = Arrays.stream(nums).boxed()
				.collect(Collectors.groupingBy(num -> num, Collectors.counting()))
				.entrySet().stream()
				.flatMap(entry -> entry.getValue() > 2 ? 
		                Arrays.stream(new Integer[]{entry.getKey(), entry.getKey()}) : 
		                Arrays.stream(new Integer[entry.getValue().intValue()]).map(i -> entry.getKey()))
		            .collect(Collectors.toList());

		for (int i = 0; i < list.size(); i++) {
            nums[i] = list.get(i);
        }

		return list.size();
	}
}
