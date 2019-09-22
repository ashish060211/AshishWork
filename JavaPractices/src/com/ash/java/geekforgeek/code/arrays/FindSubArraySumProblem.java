package com.ash.java.geekforgeek.code.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Problem link:-
 * https://practice.geeksforgeeks.org/problems/subarray-with-given-sum/0
 *
 * 
 */
public class FindSubArraySumProblem {

	public static void main(String[] args) {
		//mySolution();
		 //mySolutionSampleRun();
		//myOptimizeSolutionSampleRun();
		runTextSuit();
	}
	
	static void runTextSuit() {
		long start = System.currentTimeMillis();
		Map<ArrayInfo, String> map = new HashMap<>();
		map.put(new ArrayInfo(10, 15, new int[] { 6, 7, 3, 4, 1, 5, 2, 8, 9, 10 }),"2 5");
		map.put(new ArrayInfo(42, 468, new int[] {135,101,170,125,79,159,163,65,106,146,82,28,162,92,196,143,28,37,192,5,103,154,93,183,22,117,119,96,48,127,172,139,70,113,68,100,36,95,104,12,123,134})
				,"38 42");
		map.put(new ArrayInfo(5, 12, new int[] {1,2,3,7,5}),"2 4");
		map.put(new ArrayInfo(10, 15, new int[] {1,2,3,4,5,6,7,8,9,10}),"1 5");
		map.forEach((e,r) -> {
			Result result = getOptimizeResult(e);
			//Result result = getResult(e);
			if(!r.equals(result.getStart()+" "+result.getEnd()))  {
				System.out.println("Fail : "+result);
			} else {
				System.out.println("Pass");
			}
		});
		long end = (System.currentTimeMillis() - start);
		System.out.println("Total time taken==>" + (end) + " ms.");
	}

	static void myOptimizeSolutionSampleRun() {
		long start = System.currentTimeMillis();
		//ArrayInfo info = new ArrayInfo(10, 15, new int[] { 6, 7, 3, 4, 1, 5, 2, 8, 9, 10 });//start=3, end=5
		ArrayInfo info = new ArrayInfo(42, 468, new int[] {135,101,170,125,79,159,163,65,106,146,82,28,162,92,196,143,28,37,192,5,103,154,93,183,22,117,119,96,48,127,172,139,70,113,68,100,36,95,104,12,123,134});
		//above answer is 38 42
		Result result = getOptimizeResult(info);
		System.out.println(result);
		long end = (System.currentTimeMillis() - start);
		System.out.println("Total time taken==>" + (end) + " ms.");
	}

	static void mySolutionSampleRun() {
		long start = System.currentTimeMillis();
		//ArrayInfo info = new ArrayInfo(10, 15, new int[] { 6, 7, 3, 4, 1, 5, 2, 8, 9, 10 });
		ArrayInfo info = new ArrayInfo(42, 468, new int[] {135,101,170,125,79,159,163,65,106,146,82,28,162,92,196,143,28,37,192,5,103,154,93,183,22,117,119,96,48,127,172,139,70,113,68,100,36,95,104,12,123,134});
		Result result = getResult(info);
		System.out.println(result);
		long end = (System.currentTimeMillis() - start);
		System.out.println("Total time taken==>" + (end) + " ms.");
	}

	static void mySolution() {
		Scanner sc = new Scanner(System.in);
		int tcNum = sc.nextInt();
		sc.nextLine(); // break the line here

		List<ArrayInfo> inputList = new ArrayList<>();
		for (int i = 0; i < tcNum; i++) {
			ArrayInfo arrayInfo = getArrayInfo(sc);
			inputList.add(arrayInfo);
		}
		sc.close();

		inputList.forEach(arr -> {
			// Result rs = getResult(arr);
			Result rs = getOptimizeResult(arr);
			if (rs.getEnd() == 0) {
				System.out.println("-1");
			} else {
				System.out.println(rs.getStart() + " " + rs.getEnd());
			}
		});
	}

	static int[] convertStringIntoArray(Scanner numArrScanner, int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			if (numArrScanner.hasNextInt()) {
				arr[i] = numArrScanner.nextInt();
			}
		}
		return arr;
	}

	static ArrayInfo getArrayInfo(Scanner sc) {
		Scanner numScanner = new Scanner(sc.nextLine());
		int size = numScanner.nextInt();
		int sum = numScanner.nextInt();
		numScanner.close();
		Scanner numArrScanner = new Scanner(sc.nextLine());
		int[] array = convertStringIntoArray(numArrScanner, size);
		numArrScanner.close();
		return new ArrayInfo(size, sum, array);
	}

	static class ArrayInfo {
		int size;
		int sum;
		int[] array;

		public ArrayInfo(int size, int sum, int[] array) {
			super();
			this.size = size;
			this.sum = sum;
			this.array = array;
		}

		public int getSize() {
			return size;
		}

		public int getSum() {
			return sum;
		}

		public int[] getArray() {
			return array;
		}

		@Override
		public String toString() {
			return "ArrayInfo [size=" + size + ", sum=" + sum + ", array=" + Arrays.toString(array) + "]";
		}
	}

	static class Result {
		int start;
		int end;
		ArrayInfo testCase;

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}

		public ArrayInfo getTestCase() {
			return testCase;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public void setEnd(int end) {
			this.end = end;
		}

		public void setTestCase(ArrayInfo testCase) {
			this.testCase = testCase;
		}

		public Result() {

		}

		public void reset() {
			this.end = 0;
			this.start = 0;
		}

		@Override
		public String toString() {
			return "Result [start=" + start + ", end=" + end + ", testCase=" + testCase + "]";
		}
	}

	static Result getResult(ArrayInfo info) {
		Result rs = new Result();
		int tempSum = 0;
		boolean status = true;
		for (int i = 0; i < info.getSize(); i++) {
			if (status) {
				rs.setStart(i + 1);
				status = false;
			}
			tempSum += info.getArray()[i];
			if (tempSum == info.getSum()) {
				rs.setEnd(i + 1);
				break;
			} else if (tempSum > info.getSum()) {
				i = rs.getStart() - 1;
				status = true;
				tempSum = 0;
				rs.reset();
			}
		}
		rs.setTestCase(info);
		return rs;
	}

	static Result getOptimizeResult(ArrayInfo info) {
		Result rs = new Result();
		int tempSum = 0;
		for (int i = 0; i < info.getSize(); i++) {
			if (i==0) {
				rs.setStart(i + 1);
			}
			tempSum += info.getArray()[i];
			if (tempSum == info.getSum()) {
				rs.setEnd(i + 1);
				break;
			} 
			while(tempSum > info.getSum()) {
				tempSum = tempSum - info.getArray()[rs.getStart() - 1];
				rs.setStart(rs.getStart() + 1);
			}
			if (tempSum == info.getSum()) {
				rs.setEnd(i + 1);
				break;
			} 
		}
		rs.setTestCase(info);
		return rs;
	}
	
}
