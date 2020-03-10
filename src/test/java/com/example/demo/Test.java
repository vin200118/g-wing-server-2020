package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
	
	public static void main(String args[]) {
		List<Integer> result = new ArrayList<>();
		result.add(12);
	
		
		String eventIdlist="";
		int count = 0;
		int size = result.size();
		for(Integer id:result) {
			count+=1;
			eventIdlist+=id;
			if(count != size) {
				eventIdlist+=",";
			}
		}
		System.out.println(eventIdlist);
	}
}
