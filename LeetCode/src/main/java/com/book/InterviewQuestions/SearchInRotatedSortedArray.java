package com.book.InterviewQuestions;

public class SearchInRotatedSortedArray {

	public static void main(String[] args) {

		int[] nums = { 4, 5, 6, 7, 0, 1, 2 };
		System.out.println(searchTarget(nums, 0));
		System.out.println(searchTarget(nums, 3));

		int[] nums1 = { 1 };
		System.out.println(searchTarget(nums1, 0));
	}

	public static int searchTarget(int[] nums, int target) {

		int start = 0, end = nums.length - 1;

		while (start <= end) {

			int mid = start + (end - start) / 2;

			if (nums[mid] == target)
				return mid;

			if (nums[start] <= nums[mid]) {
				if (nums[start] <= target && target < nums[mid])
					end = mid - 1;
				else
					start = mid + 1;
			} else {
				if (nums[mid] < target && target <= nums[end])
					start = mid + 1;
				else
					end = mid - 1;
			}
		}
		return -1;
	}
}
