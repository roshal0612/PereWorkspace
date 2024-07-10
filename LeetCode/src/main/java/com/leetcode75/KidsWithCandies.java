package com.leetcode75;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KidsWithCandies {

	public static void main(String[] args) {

		int[] array = { 2, 3, 5, 1, 3 };
		int[] array1 = { 4, 2, 1, 1, 2 };
		int extraCandies = 1;

		List<Boolean> kidsWithCandies = kidsWithCandies(array, extraCandies);

		System.out.println(kidsWithCandies);
	}

	public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
		int maxCandies = Arrays.stream(candies).max().orElse(0);
		System.out.println();

		return Arrays.stream(candies).mapToObj(candy -> candy + extraCandies >= maxCandies)
				.collect(Collectors.toList());
	}
}
