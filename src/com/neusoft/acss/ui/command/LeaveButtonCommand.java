package com.neusoft.acss.ui.command;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;

/**
 * <p> Title: [导入请假信息按钮]</p>
 * <p> Description: [目前是自动导入的，所以此方法可以忽略]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class LeaveButtonCommand implements ButtonCommandImpl {

	private Info info = null;

	public LeaveButtonCommand(Info info) {
		this.info = info;
	}

	@Override
	public Info handleRequest() throws BizException, Exception {

		info.setMessage("目前还没有请假信息，请忽略！");
		return info;
	}

}
