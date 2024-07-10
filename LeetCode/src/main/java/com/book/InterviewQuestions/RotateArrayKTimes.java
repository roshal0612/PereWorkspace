package com.book.InterviewQuestions;

import java.util.Arrays;

public class RotateArrayKTimes {

	public static void main(String[] args) {
		int[] nums = {1,2,3,4,5,6,7};
		rotate1(nums, 3);
	}
	
	public static void rotate(int[] nums, int k) {
		
		int l = nums.length;

        for (int i = 0 ; i < k ; i++){
            for (int j = l-1 ; j > 0 ; j--){
                int temp = nums[j];
                nums[j] = nums[j-1];
                nums[j-1] = temp;

            }
        }
        
        System.out.println(nums);
	}
	
	public static void rotate1(int[] nums, int k) {
		
		int l = nums.length;
		
		int[] temp = new int[k];
		
		for (int i = 0 ; i < k ; i ++) {
			temp[i] = nums[l-k+i];
		}
		
		for (int i = l-1 ; i >= k ; i--) {
			nums[i] = nums[i-k];
		}
		
		for (int i = 0 ; i < k ; i++) {
			nums[i] = temp[i];
		}
		
		System.out.println(Arrays.toString(temp));
		
		System.out.println(Arrays.toString(nums));
	}
}
