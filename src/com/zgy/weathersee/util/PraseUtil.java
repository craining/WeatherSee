package com.zgy.weathersee.util;

import java.util.ArrayList;

import com.zgy.weathersee.Debug;
import com.zgy.weathersee.bean.Result;

public class PraseUtil {
	public static ArrayList<Result> Parse(String str) {
		
		Debug.i("", "response str=" + str);
		
		ArrayList<Result> list = new ArrayList<Result>();

		String str1 = str.replace("anyType{", "");
//		System.out.println("str1======" + str1);
		String str2 = str1.trim().replace("; }", "");
//		System.out.println("str2======" + str2);
		String[] result = new String[str2.split(";").length];
		for (int i = 0; i < result.length; i++) {
			Result city = new Result();
			result[i] = str2.split(";")[i].replace("string=", "").split(",")[0];
			city.setName(result[i].split(",")[0].trim());
			list.add(city);
		}
//		city = result.clone();
		return list;
	}
}
