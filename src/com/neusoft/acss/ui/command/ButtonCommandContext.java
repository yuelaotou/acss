package com.neusoft.acss.ui.command;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.EvectionButtonCommand;
import com.neusoft.acss.ui.command.impl.ExportDetailButtonCommand;
import com.neusoft.acss.ui.command.impl.ExportTotalButtonCommand;
import com.neusoft.acss.ui.command.impl.ImportButtonCommand;
import com.neusoft.acss.ui.command.impl.LeaveButtonCommand;
import com.neusoft.acss.ui.command.impl.SureButtonCommand;
import com.neusoft.acss.ui.command.impl.VacationButtonCommand;
import com.neusoft.acss.ui.command.impl.WeekendButtonCommand;

/**
 * <p> Title: [ActionCommand工厂类]</p>
 * <p> Description: [根据ActionCommand上下文确定调用哪个按钮实现类]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class ButtonCommandContext {

	IButtonCommand bc = null;

	public ButtonCommandContext(String actionCommand, UIPanel ui, Info info) {
		if (actionCommand.equals("确定")) {
			bc = new SureButtonCommand(ui, info);
		} else if (actionCommand.equals("导入法定假期")) {
			bc = new VacationButtonCommand(info);
		} else if (actionCommand.equals("导入工作串休信息")) {
			bc = new WeekendButtonCommand(info);
		} else if (actionCommand.equals("导入请假信息")) {
			bc = new LeaveButtonCommand(info);
		} else if (actionCommand.equals("导入出差信息")) {
			bc = new EvectionButtonCommand(info);
		} else if (actionCommand.equals("导入考勤记录")) {
			bc = new ImportButtonCommand(info);
		} else if (actionCommand.equals("导出详细信息表")) {
			bc = new ExportDetailButtonCommand(info);
		} else if (actionCommand.equals("导出统计总表")) {
			bc = new ExportTotalButtonCommand(info);
		}
	}

	public Info handleRequest() throws BizException, Exception {
		return bc.handleRequest();
	}
}
