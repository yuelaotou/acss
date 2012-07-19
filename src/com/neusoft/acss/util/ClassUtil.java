package com.neusoft.acss.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.column.total.impl.ColumnTotalImpl;

public class ClassUtil {

	/**
	 * 隐藏公用构造方法<p>
	 */
	private ClassUtil() {
		// noop
	}

	public static List<Class<?>> getAllImplClassesByInterface(Class<?> c) {

		// 给一个接口，返回这个接口的所有实现类
		List<Class<?>> returnClassList = new ArrayList<Class<?>>();// 返回结果
		// 如果不是一个接口，则不做处理
		if (c.isInterface()) {
			String packageName = c.getPackage().getName();// 获得当前包名
			try {
				List<Class<?>> allClass = getClassesByPackageName(packageName);// 获得当前包下以及包下的所有类
				for (int i = 0; i < allClass.size(); i++) {
					/**
					 * 判定此 Class 对象所表示的类或接口与指定的 Class 参数cls所表示的类或接口是否相同，
					 * 或是否是其超类或(超)接口，如果是则返回 true，否则返回 false。
					 */
					if (c.isAssignableFrom(allClass.get(i))) {
						if (!c.equals(allClass.get(i))) {// 本身加不进去
							returnClassList.add(allClass.get(i));
						}
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnClassList;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Class<?>> getAllImplClassesByInterface(Class<?> c, String packageName) {

		// 给一个接口，返回这个接口的所有实现类
		List<Class<?>> tmpList = new ArrayList<Class<?>>();// 返回结果
		List<Class<?>> returnClassList = new ArrayList<Class<?>>();// 返回结果
		// 如果不是一个接口，则不做处理
		if (c.isInterface()) {
			if (packageName.equals("super")) {
				packageName = c.getPackage().getName();// 获得当前包名
				packageName = packageName.substring(0, packageName.lastIndexOf("."));
			}
			try {
				List<Class<?>> allClass = getClassesByPackageName(packageName);// 获得当前包下以及包下的所有类
				for (int i = 0; i < allClass.size(); i++) {
					/**
					 * 判定此 Class 对象所表示的类或接口与指定的 Class 参数cls所表示的类或接口是否相同，
					 * 或是否是其超类或(超)接口，如果是则返回 true，否则返回 false。
					 */
					if (c.isAssignableFrom(allClass.get(i))) {
						if (!c.equals(allClass.get(i))) {// 本身加不进去
							tmpList.add(allClass.get(i));
						}
					}
				}

				if (c.equals(ColumnDetailImpl.class)) {
					Map<Integer, Class<?>> map = new HashMap<Integer, Class<?>>();
					for (Class<?> clz : tmpList) {
						ColumnDetailImpl cd = (ColumnDetailImpl) clz.newInstance();
						int order = cd.getOrder();
						map.put(order, clz);
					}
					List arrayList = new ArrayList(map.entrySet());
					Collections.sort(arrayList, new ComparatorByKey());
					for (Iterator<?> it = arrayList.iterator(); it.hasNext();) {
						Map.Entry<Integer, Class<?>> entry = (Map.Entry<Integer, Class<?>>) it.next();
						returnClassList.add(entry.getValue());
					}
				}else if (c.equals(ColumnTotalImpl.class)) {
					Map<Integer, Class<?>> map = new HashMap<Integer, Class<?>>();
					for (Class<?> clz : tmpList) {
						ColumnTotalImpl cd = (ColumnTotalImpl) clz.newInstance();
						int order = cd.getOrder();
						map.put(order, clz);
					}
					List arrayList = new ArrayList(map.entrySet());
					Collections.sort(arrayList, new ComparatorByKey());
					for (Iterator<?> it = arrayList.iterator(); it.hasNext();) {
						Map.Entry<Integer, Class<?>> entry = (Map.Entry<Integer, Class<?>>) it.next();
						returnClassList.add(entry.getValue());
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return returnClassList;

	}

	// 从一个包中查找出所有类,在jar包中不能查找
	private static List<Class<?>> getClassesByPackageName(String packageName) throws IOException,
			ClassNotFoundException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// 递归查找文件夹【即对应的包】下面的所有文件
				assert !file.getName().contains(".");
				/**
				 * J2SE 1.4在语言上提供了一个新特性，就是assertion(断言)功能，它是该版本在Java语言方面最大的革新。在软件开发中，assertion是一种经典的调试、测试方式。

				    在语法上，为了支持assertion，Java增加了一个关键字assert。它包括两种表达式，分别如下：
				    
				    　　assert expression1;
				    
				    　　assert expression1: expression2;
				    
				    　　在两种表达式中，expression1表示一个boolean表达式，expression2表示一个基本类型或者是一个对象(Object) ，基本类型包括boolean,char,double,float,int和long。由于所有类都为Object的子类，因此这个参数可以用于所有对象。
				    
				    　　assert
				    
				    　　如果为true，则程序继续执行。
				    
				    　　如果为false，则程序抛出AssertionError，并终止执行。
				 */
				classes.addAll(findClasses(file, packageName + '.' + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	@SuppressWarnings("rawtypes")
	public static class ComparatorByKey implements Comparator {

		@Override
		public int compare(Object o1, Object o2) {
			Map.Entry m1 = (Map.Entry) o1;
			Map.Entry m2 = (Map.Entry) o2;
			Integer i1 = (Integer) m1.getKey();
			Integer i2 = (Integer) m2.getKey();
			return i1.compareTo(i2);
		}
	}

}
