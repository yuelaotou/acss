package com.neusoft.acss.test;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;

public class Test {

	/**
	 * <p>Discription:[方法功能中文描述]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @throws: 
	 */
	public static void main(String[] args) {
		// Map<String, Integer> map = new IdentityHashMap<String, Integer>();
		// map.put("a", 1);
		//

		System.out.println(StringUtils.isEmpty(null));
		System.out.println(StringUtils.isNumeric("5d"));

		// String str1 = "xx";
		// String str2 = "xx";
		//
		// Map<String, String> map = new IdentityHashMap<String, String>();
		//
		// String str1 = new String("xx");
		// String str2 = new String("xx");
		// map.put(str1, "hello");
		// map.put(str2, "world");
		//
		// str1 = new String("yy");
		// str2 = new String("yy");
		// map.put(str1, "hello1");
		// map.put(str2, "world1");
		//
		// for (Entry<String, String> entry : map.entrySet()) {
		// System.out.println(entry.getKey() + "   " + entry.getValue() + " " + map.get(entry.getKey()) + " "
		// + map.get(str2));
		// }
		// System.out.println("     containsKey---> " + map.containsKey("xx"));
		// System.out.println("str1 containsKey---> " + map.containsKey(str1));
		// System.out.println("str2 containsKey---> " + map.containsKey(str2));
		// System.out.println("      value----> " + map.get("xx"));
		// System.out.println("str1  value----> " + map.get(str1));
		// System.out.println("str2  value----> " + map.get(str2));
	}
}
