package com.neusoft.acss.ui.command;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.bean.AcssBean;
import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.util.ExcelUtil;
import com.neusoft.acss.util.PropUtil;
import com.neusoft.acss.util.TxtUtil;

public abstract class ButtonCommand extends JFrame {

	private static final long serialVersionUID = 1L;

	public abstract UIPanel handleRequest() throws BizException, Exception;

	/**
	 * <p>Discription:[根据宽限时间计算最迟的上班打卡时间和最早的下班打卡时间等]</p>
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

class SureButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public SureButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {
		if (!ui.getMorningTimeField().getText().matches(Consts.REGEX_TIME)) {
			JOptionPane.showMessageDialog(this, "上班时间格式不正确，请检查。格式为：HH:mm:ss，如：08:30:00");
		} else if (!ui.getEveningTimeField().getText().matches(Consts.REGEX_TIME)) {
			JOptionPane.showMessageDialog(this, "下班时间格式不正确，请检查。格式为：HH:mm:ss，如：17:30:00");
		} else if (!StringUtils.isNumeric(ui.getGraceTimeField().getText())) {
			JOptionPane.showMessageDialog(this, "上下班宽限时间不为整数字，请检查！");
		} else if (!ui.getNoonBeginTimeField().getText().matches(Consts.REGEX_TIME)) {
			JOptionPane.showMessageDialog(this, "午休开始时间格式不正确，请检查。格式为：HH:mm:ss，如：12:00:00");
		} else if (!ui.getNoonEndTimeField().getText().matches(Consts.REGEX_TIME)) {
			JOptionPane.showMessageDialog(this, "午休结束时间格式不正确，请检查。格式为：HH:mm:ss，如：13:00:00");
		} else if (!ui.getNoonMiddleTimeField().getText().matches(Consts.REGEX_TIME)) {
			JOptionPane.showMessageDialog(this, "午休分割时间格式不正确，请检查。格式为：HH:mm:ss，如：12:30:00");
		} else if (!StringUtils.isNumeric(ui.getNoongraceTimeField().getText())) {
			JOptionPane.showMessageDialog(this, "午休宽限时间不为整数字，请检查！");
		} else {
			PropUtil.writeProperties("work.morning.time", ui.getMorningTimeField().getText(), "");
			PropUtil.writeProperties("work.evening.time", ui.getEveningTimeField().getText(), "");
			PropUtil.writeProperties("work.grace.time", ui.getGraceTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.time.begin", ui.getNoonBeginTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.time.end", ui.getNoonEndTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.time.middle", ui.getNoonMiddleTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.grace.time", ui.getNoongraceTimeField().getText(), "");
			JOptionPane.showMessageDialog(this, "设置成功！");
		}
		return ui;
	}
}

class VacationButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public VacationButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {
		JFileChooser fDialog = new JFileChooser();
		int result = fDialog.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			TxtUtil.saveVacations(fDialog.getSelectedFile());
			JOptionPane.showMessageDialog(this, "导入法定假期文本成功！");
		}
		return ui;
	}
}

class WeekendButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public WeekendButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {
		JFileChooser fDialog = new JFileChooser();
		int result = fDialog.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			TxtUtil.saveWorkDays(fDialog.getSelectedFile());
			JOptionPane.showMessageDialog(this, "导入工作串休信息文本成功！");
		}
		return ui;
	}
}

class LeaveButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public LeaveButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {
		JOptionPane.showMessageDialog(this, "目前还没有请假信息，请忽略！");
		return ui;
	}
}

class EvectionButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public EvectionButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {
		JOptionPane.showMessageDialog(this, "目前还没有出差信息，请忽略！");
		return ui;
	}
}

class EntranceButtonButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public EntranceButtonButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {

		JFileChooser fDialog = new JFileChooser();
		int result = fDialog.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = fDialog.getName(fDialog.getSelectedFile());
			if (path.endsWith(".txt")) {
				// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
				ui = initGraceProperties(ui);
				// 读取考勤打卡记录
				List<AcssBean> acssBeanList = TxtUtil.readAcssBeanFromFile(fDialog.getSelectedFile(),
						ui.getTnoon_begin(), ui.getTnoon_middle(), ui.getTnoon_end());
				ui.setAcssBeanList(acssBeanList);
				// 读取本年度休假记录
				List<Vacation> vacationList = TxtUtil.getVacations();
				ui.setVacationList(vacationList);
				// 读取本年度串休记录，正常上班日期
				List<WorkDay> workDayList = TxtUtil.getWorkDays();
				ui.setWorkDayList(workDayList);
				// 根据法定假日和串休记录，再结合正常周六周日休息，判断AcssBean是正常上班还是休息
				Business.checkVacation(acssBeanList, vacationList, workDayList);

				// 读取本月所有人的外出登记表，存在List<EvectionBean>中
				List<EvectionBean> evectionBeanList = ExcelUtil.parseExcel2EvectionList();
				ui.setEvectionBeanList(evectionBeanList);
				JOptionPane.showMessageDialog(this, "导入考勤打卡记录成功！\r\n同时也自动导入本月所有人的外出登记表！\r\n现在可以导出详细信息表和统计总表！");
			}
		}
		return ui;
	}
}

class ExportDetailButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public ExportDetailButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {
		if (ui.getAcssBeanList() == null) {
			JOptionPane.showMessageDialog(this, "还未导入打卡记录，无法导出详细信息表！");
		} else {
			List<EmployeeDetailBean> employeeDetailBeanList = Business.generateEmployeeDetailList(ui.getAcssBeanList(),
					ui.getEvectionBeanList(), ui.getTmorning(), ui.getTevening(), ui.getTnoon_begin(),
					ui.getTnoon_end());
			Map<String, String> detailMap = EmployeeDetailBean.getDetailMap();
			ExcelUtil.exportEmployeeDetailExcel(employeeDetailBeanList, detailMap);
			JOptionPane.showMessageDialog(this, "导出成功，请查看: " + Consts.PATH_EMPLOYEEDETAIL);
		}
		return ui;
	}
}

class ExportTotalButtonCommand extends ButtonCommand {

	private static final long serialVersionUID = 1L;

	private UIPanel ui = null;

	public ExportTotalButtonCommand(UIPanel ui) {
		this.ui = ui;
	}

	@Override
	public UIPanel handleRequest() throws BizException, Exception {
		if (ui.getAcssBeanList() == null) {
			JOptionPane.showMessageDialog(this, "还未导入打卡记录，无法导出统计总表！");
		} else {
			List<EmployeeDetailBean> employeeDetailBeanList = Business.generateEmployeeDetailList(ui.getAcssBeanList(),
					ui.getEvectionBeanList(), ui.getTmorning(), ui.getTevening(), ui.getTnoon_begin(),
					ui.getTnoon_end());
			Map<String, String> totalMap = EmployeeTotalBean.getTotalMap();
			List<EmployeeTotalBean> employeeTotalBeanList = Business.convertDetail2Total(employeeDetailBeanList);
			ExcelUtil.exportEmployeeTotalExcel(employeeTotalBeanList, totalMap);
			JOptionPane.showMessageDialog(this, "导出成功，请查看: " + Consts.PATH_EMPLOYEETOTAL);
		}
		return ui;
	}
}
