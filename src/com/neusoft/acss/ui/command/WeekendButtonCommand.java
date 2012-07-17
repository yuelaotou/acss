package com.neusoft.acss.ui.command;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.TxtUtil;

public class WeekendButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public WeekendButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		JFileChooser fDialog = new JFileChooser();
		int result = fDialog.showOpenDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION) {
			TxtUtil.saveWorkDays(fDialog.getSelectedFile());
			ui.setMessage("导入工作串休信息文本成功！");
		} else {
			ui.setMessage(null);
		}
		return ui;

	}

}
