package com.book.InterviewQuestions;

public class BestTimeToBuyAndSellStockI {

	public static void main(String[] args) {

		int[] prices = { 7, 1, 5, 3, 6, 4 };
		maxProfitUsingKadanesAlgo(prices);

		int[] prices1 = { 7, 6, 4, 3, 1 };
		maxProfitUsingKadanesAlgo(prices1);
		
		int[] prives2 = {1};
		maxProfitUsingKadanesAlgo(prives2);
	}

	public static int maxProfit(int[] prices) {

		int l = prices.length;
		int left = 0;
		int right = l;
		int maxProfit = 0;

		for (int i = 0; i < l; i++) {
			for (int j = i; j < l; j++) {
				int diff = prices[j] - prices[i];
				if (diff > maxProfit) {
					maxProfit = diff;
				}
			}
		}
		System.out.println(maxProfit);

		return 0;
	}

	public static int maxProfitUsingKadanesAlgo(int[] prices) {

		int maxProfit = 0;
		int l = prices.length;
		int diff;
		
		 if (l <= 1)
	            return 0;

		int buyValue = prices[0];

		for (int i = 1; i < l; i++) {
			if (prices[i] < buyValue) {
				buyValue = prices[i];
			} else if ((diff = prices[i] - buyValue) > maxProfit)
				maxProfit = diff;
		}

		System.out.println(maxProfit);

		return maxProfit;
	}

}
