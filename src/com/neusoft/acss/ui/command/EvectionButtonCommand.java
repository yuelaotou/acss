package com.neusoft.acss.ui.command;

import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;

public class EvectionButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public EvectionButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		ui.setMessage("目前还没有出差信息，请忽略！");
		return ui;
	}

}
