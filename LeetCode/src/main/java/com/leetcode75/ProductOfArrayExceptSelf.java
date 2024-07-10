package com.leetcode75;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ProductOfArrayExceptSelf {

	public static void main(String[] args) {

		int[] nums = { 1, 2, 3, 4 };
		productExceptSelf1(nums);
		
		int[] nums1 = { -1,1,0,-3,3 };
		productExceptSelf1(nums1);
	}

	public static int[] productExceptSelf(int[] nums) {

		int len = nums.length;
		int[] productArray = new int[len];

		int product = Arrays.stream(nums).reduce(1, Math::multiplyExact);

		System.out.println(product);

		for (int i = 0; i < len; i++) {

			int reduce = IntStream.concat(Arrays.stream(nums, 0, i), Arrays.stream(nums, i + 1, len)).reduce(1,
					Math::multiplyExact);
			productArray[i] = reduce;

		}
		
		System.out.println(Arrays.toString(productArray));

		return productArray;
	}
	
	public static int[] productExceptSelf1(int[] nums) {
		
		int len = nums.length;
		int[] productArray = new int[len];
		
		productArray[0] = 1;
		
		for(int i = 1 ; i < len ; i++) {
			productArray[i] = nums[i-1] * productArray[i-1];
		}
		
		int suffixProduct = 1;
		for (int i = len-1 ; i >= 0 ; i--) {
			productArray[i] = productArray[i] * suffixProduct;
			suffixProduct *= nums[i];
		}
		
		System.out.println(Arrays.toString(productArray));
		
		return productArray;
	}
}
