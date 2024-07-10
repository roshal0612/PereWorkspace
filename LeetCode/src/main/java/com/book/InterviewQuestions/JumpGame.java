package com.book.InterviewQuestions;

public class JumpGame {

	public static void main(String[] args) {

		int[] nums = {2, 3, 1, 1, 4};
		System.out.println(canJump(nums));
		
		int[] nums1 = {3, 2, 1, 0, 4};
		System.out.println(canJump(nums1));
		
		int[] nums2 = {1};
		System.out.println(canJump(nums2));
		
	}

	public static boolean canJump(int[] nums) { // Greedy Algorithm

//		int n = nums.length;
//		
//		if (n <= 1)
//            return true;
		
		int maxReach = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) {
                return false;
            }
            maxReach = Math.max(maxReach, i + nums[i]);
        }
        return maxReach >= nums.length - 1;
		
//		for (int i = 0 ; i < n ; i++) {
//			int jump = nums[i];
//			i = i + jump; 
//			System.out.println(i);
//			
//			if (i >= (n-1))
//				return true;
//		}
//		return false;
	}
}
