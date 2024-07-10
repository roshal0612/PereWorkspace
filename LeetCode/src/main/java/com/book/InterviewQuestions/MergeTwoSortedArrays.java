package com.book.InterviewQuestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MergeTwoSortedArrays {

	public static void main(String[] args) {

		int[] nums1 = { 1, 2, 3, 0, 0, 0 };
		int[] nums2 = { 2, 5, 6 };
		merge2(nums1, 3, nums2, 3);

	}

	public static void merge(int[] nums1, int m, int[] nums2, int n) {

		Stream<Integer> stream1 = Arrays.stream(nums1).boxed();
		Stream<Integer> stream2 = Arrays.stream(nums2).boxed();

		List<Integer> merged = Stream.concat(stream1, stream2).filter(i -> i != 0)
				.sorted(Comparator.comparing(Integer::intValue)).toList();

		System.out.println(merged);

	}

	public static void merge1(int[] nums1, int m, int[] nums2, int n) {

		int j = 0;
        int l = m + n;

        if (m==0)
            m=1;
		
		for(int i = m-1 ; i < l ; i++) {
			if (nums1[i] == 0) {
				nums1[i] = nums2[j++];
			}
            if (j >= n)
                break;
		}
		
		Arrays.sort(nums1);
		
		System.out.println(Arrays.toString(nums1));

	}
	
	public static void merge2(int[] nums1, int m, int[] nums2, int n) {
		
		int[] merged = IntStream.concat(Arrays.stream(nums1, 0, m), Arrays.stream(nums2))
				.sorted()
				.toArray();
		
		System.arraycopy(merged, m, nums1, m, n);
		
		System.out.println(Arrays.toString(nums1));
		
	}
}