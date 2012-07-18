package com.neusoft.acss.ui.command.context;

import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.EvectionButtonCommand;
import com.neusoft.acss.ui.command.ExportDetailButtonCommand;
import com.neusoft.acss.ui.command.ExportTotalButtonCommand;
import com.neusoft.acss.ui.command.ImportButtonCommand;
import com.neusoft.acss.ui.command.LeaveButtonCommand;
import com.neusoft.acss.ui.command.SureButtonCommand;
import com.neusoft.acss.ui.command.VacationButtonCommand;
import com.neusoft.acss.ui.command.WeekendButtonCommand;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;

public class ActionCommandContext {

	ButtonCommandImpl bc = null;

	UIPanel ui = null;

	public ActionCommandContext(String actionCommand, UIPanel uipanel) {
		if (ui == null)
			ui = uipanel;
		if (actionCommand.equals("确定")) {
			bc = new SureButtonCommand(ui);
		} else if (actionCommand.equals("导入法定假期")) {
			bc = new VacationButtonCommand(ui);
		} else if (actionCommand.equals("导入工作串休信息")) {
			bc = new WeekendButtonCommand(ui);
		} else if (actionCommand.equals("导入请假信息")) {
			bc = new LeaveButtonCommand(ui);
		} else if (actionCommand.equals("导入出差信息")) {
			bc = new EvectionButtonCommand(ui);
		} else if (actionCommand.equals("导入考勤记录")) {
			bc = new ImportButtonCommand(ui);
		} else if (actionCommand.equals("导出详细信息表")) {
			bc = new ExportDetailButtonCommand(ui);
		} else if (actionCommand.equals("导出统计总表")) {
			bc = new ExportTotalButtonCommand(ui);
		}
	}

	public String handleRequest() throws BizException, Exception {
		ui = bc.handleRequest();
		return ui.getMessage();
	}
}
