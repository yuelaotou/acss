package com.neusoft.acss.ui.command;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.bs.Business;
import com.neusoft.acss.column.total.impl.ColumnTotalImpl;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.ClassUtil;
import com.neusoft.acss.util.ExcelUtil;

public class ExportTotalButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public ExportTotalButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		if (ui.getRecordBeanList() == null) {
			throw new BizException("还未导入打卡记录，无法导出统计总表！");
		} else {

			// 防止未导出详细考勤信息表而直接导出统计总表，所以再次执行以下计算详细考勤信息表
			List<Map<String, String>> lm = Business.generateEmployeeDetailList(ui.getEmployeeBeanList(),
					ui.getRecordBeanList(), ui.getEvectionBeanList());

			List<Map<String, String>> m = Business.convertDetail2Total(lm);

			List<Class<?>> lc = ClassUtil.getAllImplClassesByInterface(ColumnTotalImpl.class, "super");

			ExcelUtil.exportEmployeeTotalExcel(m, lc);

			ui.setMessage("导出统计总表成功，请查看: " + Consts.PATH_EMPLOYEETOTAL);
		}
		return ui;

	}

}
