package com.neusoft.acss.ui.command;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.bs.Business;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.ClassUtil;
import com.neusoft.acss.util.ExcelUtil;

public class ExportDetailButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public ExportDetailButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		if (ui.getRecordBeanList() == null) {
			throw new BizException("还未导入打卡记录，无法导出详细信息表！");
		} else {
			List<Map<String, String>> lm = Business.generateEmployeeDetailList(ui.getEmployeeBeanList(),
					ui.getRecordBeanList(), ui.getEvectionBeanList());

			List<Class<?>> lc = ClassUtil.getAllImplClassesByInterface(ColumnDetailImpl.class, "super");

			ExcelUtil.exportEmployeeDetailExcel(lm, lc);
			ui.setMessage("导出详细信息表成功，请查看: " + Consts.PATH_EMPLOYEEDETAIL);
		}
		return ui;

	}

}
