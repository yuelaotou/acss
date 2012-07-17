package com.neusoft.acss.ui.command;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.bs.EmployeeDetailBS;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.ExcelUtil;

public class ExportDetailButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public ExportDetailButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		if (ui.getEmployeeDetailBeanList() == null) {
			throw new BizException("还未导入打卡记录，无法导出详细信息表！");
		} else {
			List<EmployeeDetailBean> edblist = Business.generateEmployeeDetailList(ui.getEmployeeDetailBeanList(),
					ui.getEvectionBeanList(), ui.getTmorning(), ui.getTevening(), ui.getTnoon_begin(),
					ui.getTnoon_end());

			for (EmployeeDetailBean edb : edblist) {
				System.out.println(edb);
			}
			Map<String, Object[]> propertyMap = EmployeeDetailBS.getPropertyMap();
			ExcelUtil.exportEmployeeDetailExcel(edblist, propertyMap);
			ui.setMessage("导出详细信息表成功，请查看: " + Consts.PATH_EMPLOYEEDETAIL);
		}
		return ui;

	}

}
