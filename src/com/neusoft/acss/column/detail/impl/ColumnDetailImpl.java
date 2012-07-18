package com.neusoft.acss.column.detail.impl;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.RecordBean;

public interface ColumnDetailImpl {

	public String getDescription();

	public String generateColumn(EmployeeBean eb, RecordBean rb, EvectionBean evb);
}
