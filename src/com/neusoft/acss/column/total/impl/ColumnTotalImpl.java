package com.neusoft.acss.column.total.impl;

import com.neusoft.acss.bean.Info;

public interface ColumnTotalImpl {

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
	 * <p>Description:[计算统计信息的列信息]</p>
	 * <p>1、Info中存的是每人每月的详细统计信息，一般存储30条记录</p>
	 * <p>2、需要info.getSubList()来获得List&lt;Map&lt;String, String&gt;&gt; 即：职工每月的详细统计信息</p>
	 * <p>3、遍历后，每个Map&lt;String, String&gt;存储的是每人每天的详细信息。</p>
	 * <p>4、提取时具体列时需要根据com.neusoft.acss.column.detail包下的class查看。</p>
	 * <p>5、比如想获得职工姓名字段，需要这样：map.get("com.neusoft.acss.column.detail.NameColumn");</p>
	 * Created on 2012-7-19
	 * @author: 杨光 - yang.guang@neusoft.com
	 */
	public String generateColumn(Info info);
}
