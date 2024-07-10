package com.leetcode75;

public class IncreasingTripletSequence {

	public static void main(String[] args) {

		int[] num = { 1, 2, 3, 4, 5 };
		System.out.println(increasingTriplet1(num));

		System.out.println();
		int[] num1 = { 2, 1, 5, 0, 4, 6 };
		System.out.println(increasingTriplet1(num1));

		System.out.println();
		int[] num2 = { 1, 5, 0, 4, 1, 3 };
		System.out.println(increasingTriplet1(num2));
	}

	public static boolean increasingTriplet(int[] nums) { //not the correct solution

		int len = nums.length;

		for (int i = 2; i < len; i++) {
			if (nums[i] > nums[i - 1] && nums[i] > nums[i - 2])
				return true;
		}

		return false;
	}

	public static boolean increasingTriplet1(int[] nums) {
		
		int len = nums.length;

		if (nums == null || len < 3) {
            return false;
        }
		
		int first = Integer.MAX_VALUE;
		int second = Integer.MAX_VALUE;
		
		for (int num: nums) {
			if (num <= first)
				first = num;
			else if (num <= second)
				second = num;
			else 
				return true;
		}

		return false;
	}
}
