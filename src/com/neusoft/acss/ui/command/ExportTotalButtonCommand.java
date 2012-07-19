package com.neusoft.acss.ui.command;

import java.util.List;
import java.util.Map;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.column.total.impl.ColumnTotalImpl;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.ClassUtil;
import com.neusoft.acss.util.ExcelUtil;

/**
 * <p> Title: [导出统计总表按钮]</p>
 * <p> Description: [此方法基本思路是把每人每天的详细打卡情况统计成每人每月一条记录]</p>
 * <p> Description: [目前此方法不用修改，实现{@link ColumnTotalImpl}接口的所有类都会作为导出的一列，所以请关注{@link ColumnTotalImpl}接口实现类]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class ExportTotalButtonCommand implements ButtonCommandImpl {

	private Info info = null;

	public ExportTotalButtonCommand(Info info) {
		this.info = info;
	}

	@Override
	public Info handleRequest() throws BizException, Exception {

		if (info.getRecordBeanList() == null) {
			throw new BizException("还未导入打卡记录，无法导出统计总表！");
		} else {
			// 防止未导出详细考勤信息表而直接导出统计总表，所以再次执行以下计算详细考勤信息表
			List<Map<String, String>> lm = null;
			if (info.getDetailList() == null || info.getDetailList().size() == 0) {
				lm = Business.generateEmployeeDetailList(info.getEmployeeBeanList(), info.getRecordBeanList(),
						info.getEvectionBeanList());
				info.setDetailList(lm);
			}

			Business.convertDetail2Total(info);

			List<Class<?>> lc = ClassUtil.getAllImplClassesByInterface(ColumnTotalImpl.class, "super");

			ExcelUtil.exportEmployeeTotalExcel(info, lc);

			info.setMessage("导出统计总表成功，请查看: " + Consts.PATH_EMPLOYEETOTAL);
		}
		return info;

	}

}
