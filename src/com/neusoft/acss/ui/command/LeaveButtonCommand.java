package com.neusoft.acss.ui.command;

import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;

public class LeaveButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public LeaveButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		ui.setMessage("目前还没有请假信息，请忽略！");
		return ui;
	}

}
