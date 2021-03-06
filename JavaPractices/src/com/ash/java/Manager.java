package com.ash.java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Manager {

	public static void main(String[] args) {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println(availableProcessors);
		System.out.println(String.format("calculated EventCreatedDate(%s) is not valid!!!",LocalDate.now()));
		
		List<Integer> petIds = Arrays.asList(1,2,3);
		String s = String.format("Soft deleted PET records have count(%d) are : %s)", petIds.size(),petIds);
		System.out.println(s);
	}
	
	static void checkJavaByType() {
		 A a1 = new A();
		 A a2 = a1;
		 a1.setI(9);
		 a1.setO(99);
		 System.out.println(a1);
		 System.out.println(a2);
		 
		 A a3 = a1;
		 changeState(a1, 8, new Integer(98));
		 System.out.println(a1);
		 System.out.println(a2);
		 System.out.println(a3);
	}
	
	static void changeState(A a, int i, Integer o) {
		a.setI(i);
		a.setO(o);
	}

	public static <T, U> List<U> convertStringListToIntList(List<T> listOfString, Function<T, U> function) {
		return listOfString.stream().map(function).collect(Collectors.toList());
	}
	
	public static void userOfconvertStringListToIntList() {
		String[] arr = new String[2];
		arr[0] = "123";
		arr[1] = "245";

		List<String> listOfString = Arrays.asList(arr);
		List<Integer> listOfInteger = convertStringListToIntList(listOfString, Integer::parseInt);
		System.out.println(listOfInteger);
	}
}

class A {
	int i =10;
	Integer o = new Integer(100);
	public void setI(int i) {
		this.i = i;
	}
	public void setO(Integer o) {
		this.o = o;
	}
	@Override
	public String toString() {
		return "A [i=" + i + ", o=" + o + "]";
	}
	
}
