package com.neusoft.acss.column.detail;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;

/**
 * <p>考勤详细信息表的列字段接口。
 * 
 * <p>本实现类的命名规则如下：<br>
 * 第一个字母为D，代表的是Detail的列<br>
 * 第二个字母代表了三种意义：<br>
 * &nbsp;&nbsp;&nbsp;1、B开头，意思是Base基础字段。包括公司，部门等信息。<br>
 * &nbsp;&nbsp;&nbsp;2、I开头，意思是Info，一般的属性。包括请假类型，加班类型等。<br>
 * &nbsp;&nbsp;&nbsp;3、T开头，意思是Time，时间。包括迟到总时间，请假时间等。<br>
 * <p> Created on 2012-7-26</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public interface IColumnDetail {

	/**
	 * <p>Description:[排序编号，编号小的排在前面，相同编号的再按照类名排序]</p>
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
	 * <p>1、{@link EmployeeBean}，用info.getEmployeeBean()获得（需要扩展）。存储的是职工的基本信息，每人一条记录</p>
	 * <p>2、{@link EvectionBean}，用info.getEvectionBean()获得。若有出差情况，则每人每天一条记录，也有可能当天没有记录。</p>
	 * <p>3、{@link RecordBean}，用info.getRecordBean()获得。打卡记录，若有打卡记录，则每人每天一条记录，也有可能当天没有记录。</p>
	 * <p>4、{@link EmployeeBean}、{@link EvectionBean}、{@link RecordBean}通过name关联</p>
	 * Created on 2012-7-19
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateColumn(Info info);
}
