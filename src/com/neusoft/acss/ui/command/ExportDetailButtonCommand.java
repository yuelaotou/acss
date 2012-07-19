package com.neusoft.acss.ui.command;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.column.detail.impl.ColumnDetailImpl;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.ClassUtil;
import com.neusoft.acss.util.ExcelUtil;

/**
 * <p> Title: [导出详细信息表按钮]</p>
 * <p> Description: [此方法基本思路是把导入的考勤信息，职工基本信息，出差统计信息和节假日等信息综合起来去计算每人每天的详细打卡情况。]</p>
 * <p> Description: [目前此方法不用修改，实现{@link ColumnDetailImpl}接口的所有类都会作为导出的一列，所以请关注{@link ColumnDetailImpl}接口实现类]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class ExportDetailButtonCommand implements ButtonCommandImpl {

	private Info info = null;

	public ExportDetailButtonCommand(Info info) {
		this.info = info;
	}

	@Override
	public Info handleRequest() throws BizException, Exception {

		if (info.getRecordBeanList() == null) {
			throw new BizException("还未导入打卡记录，无法导出详细信息表！");
		} else {
			List<Map<String, String>> lm = Business.generateEmployeeDetailList(info.getEmployeeBeanList(),
					info.getRecordBeanList(), info.getEvectionBeanList());

			List<Class<?>> lc = ClassUtil.getAllImplClassesByInterface(ColumnDetailImpl.class, "super");

			ExcelUtil.exportEmployeeDetailExcel(lm, lc);

			info.setDetailList(lm);
			info.setMessage("导出详细信息表成功，请查看: " + Consts.PATH_EMPLOYEEDETAIL);
		}
		return info;

	}

}
