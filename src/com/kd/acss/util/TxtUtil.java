package com.kd.acss.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kd.acss.bean.RecordBean;
import com.kd.acss.bs.Business;
import com.kd.acss.exception.BizException;

/**
 * <p> Title: [文本操作类]</p>
 * <p> Description: [读取、导入文本。主要操作：每月考勤文件、法定假日、串休等文本]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public final class TxtUtil {

	/**
	 * 隐藏公用构造方法<p>
	 */
	private TxtUtil() {
	}

	/**
	 * <p>Description:[读取考勤结果txt文件，存入到List&lt;{@link RecordBean}&gt;中]</p>
	 * Created on 2012-7-10
	 */
	public static List<RecordBean> readAcssBeanFromFile(File file, String tnoon_begin, String tnoon_middle,
			String tnoon_end) {
		List<RecordBean> list = new ArrayList<RecordBean>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
			String str = "";
			RecordBean rb = null;
			while ((str = br.readLine()) != null) {
				if (StringUtils.isNotEmpty(str)) {
					rb = Business.parseStrToAcssBean(str.trim(), tnoon_begin, tnoon_middle, tnoon_end);
					list.add(rb);
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			throw new BizException("考勤结果txt编码格式错误，目前编码格式要求为：GBK", e);
		} catch (FileNotFoundException e) {
			throw new BizException("不会发生的异常，吗的", e);
		} catch (IOException e) {
			throw new BizException("读取文件：" + file.getName() + " 内容异常", e);
		}

		return list;
	}

}
