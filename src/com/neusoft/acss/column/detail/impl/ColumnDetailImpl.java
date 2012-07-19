package com.neusoft.acss.column.detail.impl;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;

public interface ColumnDetailImpl {

	/**
	 * <p>Description:[排序编号，编号小的排在前面]</p>
	 * Created on 2012-7-19
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public int getOrder();
	
	/**
	 * <p>Description:[获得此类的描述，目前只描述了列名，以后可能增加其他信息，因此没直接返回getName()]</p>
	 * Created on 2012-7-19
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String getDescription();

	/**
	 * <p>Description:[计算详细信息的列信息，Info中存的是三个实体信息，分别为：]</p>
	 * <p>1、{@link EmployeeBean}，用info.getEmployeeBean()获得（目前为空，以后需要扩展）。存储的是职工的基本信息，每人一条记录</p>
	 * <p>2、{@link EvectionBean}，用info.getEvectionBean()获得。若有出差情况，则每人每天一条记录，也有可能当天没有记录。</p>
	 * <p>3、{@link RecordBean}，用info.getRecordBean()获得。打卡记录，若有打卡记录，则每人每天一条记录，也有可能当天没有记录。</p>
	 * <p>4、{@link EmployeeBean}、{@link EvectionBean}、{@link RecordBean}通过name关联</p>
	 * Created on 2012-7-19
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateColumn(Info info);
}
