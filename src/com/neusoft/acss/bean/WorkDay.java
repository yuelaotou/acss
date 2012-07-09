package com.neusoft.acss.bean;

import java.io.Serializable;
import java.util.Date;

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
