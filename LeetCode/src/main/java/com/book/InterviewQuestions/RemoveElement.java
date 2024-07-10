package com.book.InterviewQuestions;

import java.util.Arrays;

public class RemoveElement {

	public static void main(String[] args) {

		int[] nums = {0,1,2,2,3,0,4,2};
		removeElements2(nums, 2);
		
		int[] nums1 = {3,2,2,3};
		removeElements2(nums1, 3);
	}

	public static int removeElements(int[] nums, int val) {
		int l = nums.length;
		int j = l - 1;
		int k = 0;

		for (int i = 0; i < j; i++) {
		
			if (nums[i] == val) {
				if (nums[j] == val) {
					nums[j] = 0;
					j--;
				}
				k++;
				nums[i] = nums[j];
				nums[j--] = 0;
			}
		}
		
		System.out.println(Arrays.toString(nums));
		System.out.println(l-k);
		System.out.println();

		return l - k;
	}
	
	public static int removeElements2(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k++] = nums[i];
            }
        }
        
        System.out.println(Arrays.toString(nums));
		System.out.println(k);
		
        return k;
    }
}
