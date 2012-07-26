package com.kd.acss.bean;

import java.io.Serializable;

/**
 * <p> Title: [周末实体类]</p>
 * <p> Description: [读取周末信息后，把内容保存在Weekend实体中]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class Weekend implements Serializable {

	private static final long serialVersionUID = 1L;

	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
