package com.neusoft.acss.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <p> Title: [串休日实体类]</p>
 * <p> Description: [读取串休日文件后，把内容保存在WorkDay实体中]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class WorkDay implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
