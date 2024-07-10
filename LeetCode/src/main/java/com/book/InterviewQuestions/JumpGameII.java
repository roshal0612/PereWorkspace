package com.book.InterviewQuestions;

public class JumpGameII {
	
	public static void main(String[] args) {
		int[] nums = {2, 3, 1, 1, 4};
		jump(nums);
		
		int[] nums1 = {3, 2, 1, 0, 4};
		jump(nums1);
		
		int[] nums2 = {1};
		jump(nums2);
	}

	public static int jump(int[] nums) {

		if (nums == null || nums.length == 0) {
			return 0;
		}

		int minJump = 0;
		int maxReach = 0;
		int currentEnd = 0;

		for (int i = 0; i < nums.length - 1; i++) {
			maxReach = Math.max(maxReach, i + nums[i]);
			if (i == maxReach) {
				minJump++;
				currentEnd = maxReach;
			}
			if (currentEnd >= nums.length - 1)
				break;
		}

		System.out.println(minJump);
		return minJump;
	}
}
