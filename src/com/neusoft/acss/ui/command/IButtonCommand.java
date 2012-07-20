package com.neusoft.acss.ui.command;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.exception.BizException;

/**
 * <p> Title: [按钮实现接口]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public interface IButtonCommand {

	/**
	 * <p>Description:[按钮操作实现具体方法]</p>
	 * Created on 2012-7-19
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public Info handleRequest() throws BizException, Exception;
}
