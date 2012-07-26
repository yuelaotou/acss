package com.kd.acss.ui.command.impl;

import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.kd.acss.bean.EmployeeBean;
import com.kd.acss.bean.EvectionBean;
import com.kd.acss.bean.Holiday;
import com.kd.acss.bean.Info;
import com.kd.acss.bean.RecordBean;
import com.kd.acss.bean.Weekend;
import com.kd.acss.bean.Workday;
import com.kd.acss.bs.Business;
import com.kd.acss.exception.BizException;
import com.kd.acss.ui.command.IButtonCommand;
import com.kd.acss.util.DateUtil;
import com.kd.acss.util.ExcelUtil;
import com.kd.acss.util.PropUtil;
import com.kd.acss.util.TxtUtil;

/**
 * <p> Title: [导出详细信息表按钮]</p>
 * <p> Description: [导入考勤打卡记录，目前需要在这里同时处理几件事：]</p>
 * <p> 1、读取职工基本信息存储在List&lt;{@link EmployeeBean}&gt;中</p>
 * <p> 2、读取假日维护表记录，把周末信息存储在List&lt;{@link Weekend}&gt;中</p>
 * <p> 3、读取假日维护表记录，把上班日信息存储在List&lt;{@link Workday}&gt;中</p>
 * <p> 3、读取假日维护表记录，把法定假日信息存储在List&lt;{@link Holiday}&gt;中</p>
 * <p> 4、读取本月所有人的外出登记表存储在List&lt;{@link EvectionBean}&gt;中</p>
 * <p> 5、根据法定假日，上班日和周末，判断RecordBean是正常上班还是休息</p>
 * <p> 6、最后把所有信息集成在Info对象中返回</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class ImportButtonCommand implements IButtonCommand {

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
				List<EmployeeBean> employeeBeanList = ExcelUtil.parseExcel2EmployeeList();
				info.setEmployeeBeanList(employeeBeanList);

				// 解析假期维护表,WorkDayList,WeekendList,HoliDayList
				info = ExcelUtil.getHolidaysFromExcel(info);

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
