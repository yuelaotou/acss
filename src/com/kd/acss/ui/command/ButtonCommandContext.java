package com.kd.acss.ui.command;

import com.kd.acss.bean.Info;
import com.kd.acss.exception.BizException;
import com.kd.acss.ui.UIPanel;
import com.kd.acss.ui.command.impl.ExportDetailButtonCommand;
import com.kd.acss.ui.command.impl.ExportTotalButtonCommand;
import com.kd.acss.ui.command.impl.ImportButtonCommand;
import com.kd.acss.ui.command.impl.SureButtonCommand;

/**
 * <p> Title: [ActionCommand工厂类]</p>
 * <p> Description: [根据ActionCommand上下文确定调用哪个按钮实现类]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class ButtonCommandContext {

	IButtonCommand bc = null;

	public ButtonCommandContext(String actionCommand, UIPanel ui, Info info) {
		if (actionCommand.equals("确定")) {
			bc = new SureButtonCommand(ui, info);
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
