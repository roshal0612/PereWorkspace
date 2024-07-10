package com.leetcode75;

public class CanPlaceFlowers {

	public static void main(String[] args) {
		int[] array = {1,0,0,0,1};
		boolean canPlaceFlowers1 = canPlaceFlowers1(array, 1);
		System.out.println(canPlaceFlowers1);
		canPlaceFlowers1 = canPlaceFlowers1(array, 2);
		System.out.println(canPlaceFlowers1);
		
		int[] array2 = {1,0,0,0,0,1};
		canPlaceFlowers1 = canPlaceFlowers1(array2, 2);
		System.out.println(canPlaceFlowers1);
	}
	
	public static boolean canPlaceFlowers1(int[] flowerbed, int n) {
		int length = flowerbed.length;
		int i = 0;

//		 if (flowerbed[0] != 1)
//		 i++;
		 
		 int count = 0;
		 
		 if (flowerbed[0] == 0 & flowerbed[1]== 0) {
			 i++;
			 count++;
		 }
		 
		for (i = 1 ; i < length-1 ; i++) {
			if (flowerbed[i]== 0 & flowerbed[i-1] ==0 & flowerbed[i+1]==0) {
				i++;
				count++;
			}
		}
		
		System.out.println("count :" + count);
		if (n <= count)
			return true;
			else 
		return false;
	}

	public boolean canPlaceFlowers(int[] flowerbed, int n) {
		int length = flowerbed.length;
		int i = 0;

		 if (flowerbed[0] != 1)
		 i++;

		for (i += 2; i < length - 1; i += 2) {
			System.out.println(i);
			if (flowerbed[i] == 1)
				return false;
		}

		return true;
	}
}
