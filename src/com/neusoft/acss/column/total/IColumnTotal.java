package com.neusoft.acss.column.total;

import com.neusoft.acss.bean.Info;

/**
 * <p>考勤统计总表的列字段接口。
 * 
 * <p>本实现类的命名规则如下：<br>
 * 第一个字母为D，代表的是Detail的列<br>
 * 第二个字母代表了三种意义：<br>
 * &nbsp;&nbsp;&nbsp;1、B开头，意思是Base基础字段。包括公司，部门等信息<br>
 * &nbsp;&nbsp;&nbsp;2、C开头，意思是Count求和，合计。迟到次数，早退次数等<br>
 * &nbsp;&nbsp;&nbsp;3、T开头，意思是Time，时间。包括迟到总时间，请假时间等。<br>
 * &nbsp;&nbsp;&nbsp;4、I开头，意思是Info，一般的属性。是否提交外出登记表等等<br>
 * <p> Created on 2012-7-26</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public interface IColumnTotal {

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
	 * <p>Description:[计算统计信息的列信息]</p>
	 * <p>1、Info中存的是每人每月的详细统计信息，一般存储30条记录</p>
	 * <p>2、需要info.getSubList()来获得List&lt;Map&lt;String, String&gt;&gt; 即：职工每月的详细统计信息</p>
	 * <p>3、遍历后，每个Map&lt;String, String&gt;存储的是每人每天的详细信息。</p>
	 * <p>4、提取时具体列时需要根据com.neusoft.acss.column.detail.impl包下的class查看。</p>
	 * <p>5、比如想获得职工姓名字段，需要这样：map.get(D_B_Name.class.getName());</p>
	 * Created on 2012-7-19
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateColumn(Info info);
}
