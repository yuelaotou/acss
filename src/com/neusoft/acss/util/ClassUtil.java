package com.neusoft.acss.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.neusoft.acss.column.detail.IColumnDetail;
import com.neusoft.acss.column.total.IColumnTotal;

public class ClassUtil {

	/** 
	 * 判断类是否是clz的实现类 <br> 
	 * 首先,类不能是抽象的,其次,类必须实现函数接口 
	 *  
	 * @param c 
	 * @return 是否是clz的实现类 
	 */
	public static boolean isFunction(Class<?> c, Class<?> clz) {
		if (c == null) {
			return false;
		}
		if (c.isInterface()) {
			return false;
		}
		if (Modifier.isAbstract(c.getModifiers())) {
			return false;// 抽象
		}
		return clz.isAssignableFrom(c);
	}

	/** 
	 * 获取项目的path下所有的文件夹和文件 
	 *  
	 * @return 文件列表 
	 */
	private static List<File> listPaths() {
		List<File> files = new ArrayList<File>();
		String jars = System.getProperty("java.class.path");
		if (jars == null) {
			System.err.println("java.class.path is null!");
			return files;
		}
		URL root = ClassUtil.class.getClassLoader().getResource("");
		if (root == null) {
			System.err.println("path root is null!");
			return files;
		}
		String path = null;
		try {
			path = URLDecoder.decode(root.getFile(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return files;
		}
		File dir = new File(path);
		String[] array = (jars).split(";");
		if (array != null) {
			for (String s : array) {
				if (s == null) {
					continue;
				}
				File f = new File(s);
				if (f.exists()) {
					files.add(f);
				} else {// 有些jar就在系统目录下,省略了路径,要加上
					File jar = new File(dir, s);
					if (jar.exists()) {
						files.add(jar);
					}
				}
			}
		}
		return files;
	}

	/** 
	 * 获取包下所有的函数实现类 
	 *  
	 * @param pkg 
	 *            包名,此处只是为了限定,防止漫无目的的查找.不用设置也可以,就要每找到一个类就要加载一次判断了 
	 * @return 类列表 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Class<?>> getAllImplClassesByInterface(Class<?> clz) {

		String pkg = clz.getPackage().getName();
		List<Class<?>> list = new ArrayList<Class<?>>();
		for (File f : ClassUtil.listPaths()) {
			// 如果是以文件的形式保存在服务器上
			if (f.isDirectory()) {
				// 获取包的物理路径
				String path = pkg.replace('.', File.separatorChar);
				ClassUtil.dirWalker(path, f, list, clz);
			} else {// 尝试是否是jar文件
				// 获取jar
				JarFile jar = null;
				try {
					jar = new JarFile(f);
				} catch (IOException e) {
					// 有可能不是一个jar
				}
				if (jar == null) {
					continue;
				}
				String path = pkg.replace('.', '/');
				// 从此jar包 得到一个枚举类
				Enumeration<JarEntry> entries = jar.entries();
				// 同样的进行循环迭代
				while (entries.hasMoreElements()) {
					// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					// 如果是以/开头的
					if (name.charAt(0) == '/') {
						// 获取后面的字符串
						name = name.substring(1);
					}
					// 如果前半部分和定义的包名相同
					if (name.contains(path)) {
						if (name.endsWith(".class") && !entry.isDirectory()) {
							name = name.replace("/", ".").substring(0, name.lastIndexOf("."));
							try {
								Class<?> c = Class.forName(name);
								if (ClassUtil.isFunction(c, clz)) {
									list.add(c);
								}
							} catch (Exception e) {
								// 找不到无所谓了
							}
						}
					}
				}
			}
		}

		List<Class<?>> returnClassList = new ArrayList<Class<?>>();// 返回结果
		try {
			if (clz.equals(IColumnDetail.class)) {
				Map<Integer, Class<?>> map = new HashMap<Integer, Class<?>>();
				for (Class<?> ccc : list) {
					IColumnDetail cd = (IColumnDetail) ccc.newInstance();
					int order = cd.getOrder();
					map.put(order, ccc);
				}
				List arrayList = new ArrayList(map.entrySet());
				Collections.sort(arrayList, new ComparatorByKey());
				for (Iterator<?> it = arrayList.iterator(); it.hasNext();) {
					Map.Entry<Integer, Class<?>> entry = (Map.Entry<Integer, Class<?>>) it.next();
					returnClassList.add(entry.getValue());
				}
			} else if (clz.equals(IColumnTotal.class)) {
				Map<Integer, Class<?>> map = new HashMap<Integer, Class<?>>();
				for (Class<?> ccc : list) {
					IColumnTotal cd = (IColumnTotal) ccc.newInstance();
					int order = cd.getOrder();
					map.put(order, ccc);
				}
				List arrayList = new ArrayList(map.entrySet());
				Collections.sort(arrayList, new ComparatorByKey());
				for (Iterator<?> it = arrayList.iterator(); it.hasNext();) {
					Map.Entry<Integer, Class<?>> entry = (Map.Entry<Integer, Class<?>>) it.next();
					returnClassList.add(entry.getValue());
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return returnClassList;
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

	/** 
	 * 遍历文件夹下所有的类 
	 *  
	 * @param path 
	 *            包路径 
	 * @param file 
	 *            文件 
	 * @param list 
	 *            保存类列表 
	 */
	private static void dirWalker(String path, File file, List<Class<?>> list, Class<?> clz) {
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					ClassUtil.dirWalker(path, f, list, clz);
				}
			} else {
				Class<?> c = ClassUtil.loadClassByFile(path, file, clz);
				if (c != null) {
					list.add(c);
				}
			}
		}
	}

	/** 
	 * 从文件加载类 
	 *  
	 * @param pkg 
	 *            包路径 
	 * @param file 
	 *            文件 
	 * @return 类或者null 
	 */
	private static Class<?> loadClassByFile(String pkg, File file, Class<?> clz) {
		if (!file.isFile()) {
			return null;
		}
		String name = file.getName();
		if (name.endsWith(".class")) {
			String ap = file.getAbsolutePath();
			if (!ap.contains(pkg)) {
				return null;
			}
			name = ap.substring(ap.indexOf(pkg) + pkg.length());
			if (name.startsWith(File.separator)) {
				name = name.substring(1);
			}
			String path = (pkg + "." + name.substring(0, name.lastIndexOf("."))).replace(File.separatorChar, '.');
			try {
				Class<?> c = Class.forName(path);
				if (ClassUtil.isFunction(c, clz)) {
					return c;
				}
			} catch (ClassNotFoundException e) {
				// do nothing
			}
		}
		return null;
	}

}
