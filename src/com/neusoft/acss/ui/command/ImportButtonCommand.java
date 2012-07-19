package com.neusoft.acss.ui.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.neusoft.acss.bean.EmployeeBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Info;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.DateUtil;
import com.neusoft.acss.util.ExcelUtil;
import com.neusoft.acss.util.PropUtil;
import com.neusoft.acss.util.TxtUtil;

/**
 * <p> Title: [导出详细信息表按钮]</p>
 * <p> Description: [导入考勤打卡记录，目前需要在这里同时处理几件事：]</p>
 * <p> 1、读取职工基本信息存储在List&lt;{@link EmployeeBean}&gt;中</p>
 * <p> 2、读取本年度休假记录存储在List&lt;{@link Vacation}&gt;中</p>
 * <p> 3、读取本年度串休记录(正常上班日期)存储在List&lt;{@link WorkDay}&gt;中</p>
 * <p> 4、读取本月所有人的外出登记表存储在List&lt;{@link EvectionBean}&gt;中</p>
 * <p> 5、根据法定假日和串休记录，再结合正常周六周日休息，判断RecordBean是正常上班还是休息</p>
 * <p> 6、最后把所有信息集成在Info对象中返回</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class ImportButtonCommand implements ButtonCommandImpl {

	private Info info = null;

	public ImportButtonCommand(Info info) {
		this.info = info;
	}

	@Override
	public Info handleRequest() throws BizException, Exception {

		JFileChooser fDialog = new JFileChooser();
		int result = fDialog.showOpenDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = fDialog.getName(fDialog.getSelectedFile());
			if (path.endsWith(".txt")) {
				// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
				Map<String, String> m = PropUtil.readProperties();
				String tnoon_begin = DateUtil
						.minusMinutes(m.get("work.noon.time.begin"), m.get("work.noon.grace.time"));
				String tnoon_middle = m.get("work.noon.time.middle");
				String tnoon_end = DateUtil.addMinutes(m.get("work.noon.time.end"), m.get("work.noon.grace.time"));

				// 读取考勤打卡记录
				List<RecordBean> rbList = TxtUtil.readAcssBeanFromFile(fDialog.getSelectedFile(), tnoon_begin,
						tnoon_middle, tnoon_end);
				info.setRecordBeanList(rbList);

				// 读取职工基本信息
				List<EmployeeBean> employeeBeanList = new ArrayList<EmployeeBean>();
				info.setEmployeeBeanList(employeeBeanList);

				// 读取本年度休假记录
				List<Vacation> vacationList = TxtUtil.getVacations();
				info.setVacationList(vacationList);

				// 读取本年度串休记录，正常上班日期
				List<WorkDay> workDayList = TxtUtil.getWorkDays();
				info.setWorkDayList(workDayList);

				// 读取本月所有人的外出登记表，存在List<EvectionBean>中
				List<EvectionBean> evbList = ExcelUtil.parseExcel2EvectionList();
				info.setEvectionBeanList(evbList);

				// 根据法定假日和串休记录，再结合正常周六周日休息，判断RecordBean是正常上班还是休息
				Business.checkVacation(info);

				info.setMessage("导入考勤打卡记录成功！\r\n同时也自动导入本月所有人的外出登记表！\r\n现在可以导出详细信息表和统计总表！");
			}
		} else {
			info.setMessage(null);
		}
		return info;

	}

}
