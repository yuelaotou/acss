package com.neusoft.acss.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <p> Title: [法定假日实体类]</p>
 * <p> Description: [读取法定假日文件后，把内容保存在Vacation实体中]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class Vacation implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
