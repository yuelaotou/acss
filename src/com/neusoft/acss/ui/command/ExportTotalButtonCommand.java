package com.neusoft.acss.ui.command;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.bs.EmployeeTotalBS;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.ExcelUtil;

public class ExportTotalButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public ExportTotalButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		if (ui.getEmployeeDetailBeanList() == null) {
			throw new BizException("还未导入打卡记录，无法导出统计总表！");
		} else {
			// 防止未导出详细考勤信息表而直接导出统计总表，所以再次执行以下计算详细考勤信息表
			List<EmployeeDetailBean> edblist = Business.generateEmployeeDetailList(ui.getEmployeeDetailBeanList(),
					ui.getEvectionBeanList(), ui.getTmorning(), ui.getTevening(), ui.getTnoon_begin(),
					ui.getTnoon_end());
			ui.setEmployeeDetailBeanList(edblist);

			Map<String, Object[]> propertyMap = EmployeeTotalBS.getPropertyMap();
			List<EmployeeTotalBean> employeeTotalBeanList = Business
					.convertDetail2Total(ui.getEmployeeDetailBeanList());
			ExcelUtil.exportEmployeeTotalExcel(employeeTotalBeanList, propertyMap);
			ui.setMessage("导出统计总表成功，请查看: " + Consts.PATH_EMPLOYEETOTAL);
		}
		return ui;

	}

}
