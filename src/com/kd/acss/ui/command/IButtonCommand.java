package com.kd.acss.ui.command;

import com.kd.acss.bean.Info;
import com.kd.acss.exception.BizException;

/**
 * <p> Title: [按钮实现接口]</p>
 * <p> Description: [描述]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public interface IButtonCommand {

	/**
	 * <p>Description:[按钮操作实现具体方法]</p>
	 * Created on 2012-7-19
	 */
	public Info handleRequest() throws BizException, Exception;
}
