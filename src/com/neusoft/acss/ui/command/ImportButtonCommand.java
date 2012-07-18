package com.neusoft.acss.ui.command;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.RecordBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.ExcelUtil;
import com.neusoft.acss.util.TxtUtil;

public class ImportButtonCommand implements ButtonCommandImpl {

	private UIPanel ui = null;

	public ImportButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		JFileChooser fDialog = new JFileChooser();
		int result = fDialog.showOpenDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = fDialog.getName(fDialog.getSelectedFile());
			if (path.endsWith(".txt")) {
				// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
				ui = initGraceProperties(ui);
				// 读取考勤打卡记录
				List<RecordBean> rbList = TxtUtil.readAcssBeanFromFile(fDialog.getSelectedFile(), ui.getTnoon_begin(),
						ui.getTnoon_middle(), ui.getTnoon_end());
				// 读取本年度休假记录
				List<Vacation> vacationList = TxtUtil.getVacations();
				ui.setVacationList(vacationList);
				// 读取本年度串休记录，正常上班日期
				List<WorkDay> workDayList = TxtUtil.getWorkDays();
				ui.setWorkDayList(workDayList);
				// 根据法定假日和串休记录，再结合正常周六周日休息，判断AcssBean是正常上班还是休息
				Business.checkVacation(rbList, vacationList, workDayList);
				for (RecordBean rb : rbList) {
					System.out.println(rb);
				}
				ui.setRecordBeanList(rbList);
				// 读取本月所有人的外出登记表，存在List<EvectionBean>中
				List<EvectionBean> evbList = ExcelUtil.parseExcel2EvectionList();
				ui.setEvectionBeanList(evbList);
				ui.setMessage("导入考勤打卡记录成功！\r\n同时也自动导入本月所有人的外出登记表！\r\n现在可以导出详细信息表和统计总表！");
			}
		} else {
			ui.setMessage(null);
		}
		return ui;

	}

	/**
	 * <p>Description:[根据宽限时间计算最迟的上班打卡时间和最早的下班打卡时间等]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
	 */
	public UIPanel initGraceProperties(UIPanel ui) {
		try {
			Date d_tmorning = DateUtils.addMinutes(DateUtils.parseDate(ui.getTmorning(), "HH:mm:ss"),
					Integer.parseInt(ui.getTgrace()));
			ui.setTmorning(DateFormatUtils.format(d_tmorning, "HH:mm:ss"));

			Date d_tevening = DateUtils.addMinutes(DateUtils.parseDate(ui.getTevening(), "HH:mm:ss"),
					-Integer.parseInt(ui.getTgrace()));
			ui.setTevening(DateFormatUtils.format(d_tevening, "HH:mm:ss"));

			Date d_tnoon_begin = DateUtils.addMinutes(DateUtils.parseDate(ui.getTnoon_begin(), "HH:mm:ss"),
					-Integer.parseInt(ui.getTnoon_grace()));
			ui.setTnoon_begin(DateFormatUtils.format(d_tnoon_begin, "HH:mm:ss"));

			Date d_tnoon_end = DateUtils.addMinutes(DateUtils.parseDate(ui.getTnoon_end(), "HH:mm:ss"),
					Integer.parseInt(ui.getTnoon_grace()));
			ui.setTnoon_end(DateFormatUtils.format(d_tnoon_end, "HH:mm:ss"));

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ui;
	}

}
