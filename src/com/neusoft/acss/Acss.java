package com.neusoft.acss;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.neusoft.acss.bean.AcssBean;
import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EvectionBean;
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.enums.WorkSt;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.util.ExcelUtil;
import com.neusoft.acss.util.PropUtil;
import com.neusoft.acss.util.TxtUtil;

/**
 * <p> Title: [考勤统计软件主窗口]</p>
 * <p> Description: [主窗口，包含各种控件的摆放，各种监听及处理。是整个系统的入口类]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class Acss extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JPanel morningTimePanel = new JPanel();

	private final JPanel noonBeginTimePanel = new JPanel();

	private final JPanel noonEndTimePanel = new JPanel();

	private final JPanel noonMiddleTimePanel = new JPanel();

	private final JPanel noongraceTimePanel = new JPanel();

	private final JPanel eveningTimePanel = new JPanel();

	private final JPanel graceTimePanel = new JPanel();

	private final JLabel morningTimeLabel = new JLabel("上班时间：");

	private final JLabel noonBeginTimeLabel = new JLabel("午休开始时间：");

	private final JLabel noonEndTimeLabel = new JLabel("午休结束时间：");

	private final JLabel noonMiddleTimeLabel = new JLabel("午休分割时间：");

	private final JLabel noongraceTimeLabel = new JLabel("午休宽限时间（分钟）：");

	private final JLabel eveningTimeLabel = new JLabel("下班时间：");

	private final JLabel graceTimeLabel = new JLabel("上下班宽限时间（分钟）：");

	private final JTextField morningTimeField = new JTextField();

	private final JTextField noonBeginTimeField = new JTextField();

	private final JTextField noonEndTimeField = new JTextField();

	private final JTextField noonMiddleTimeField = new JTextField();

	private final JTextField noongraceTimeField = new JTextField();

	private final JTextField eveningTimeField = new JTextField();

	private final JTextField graceTimeField = new JTextField();

	private final JButton sureButton = new JButton("确定");

	private final JButton vacationButton = new JButton("导入法定假期");

	private final JButton weekendButton = new JButton("导入工作串休信息");

	private final JButton leaveButton = new JButton("导入请假信息");

	private final JButton evectionButton = new JButton("导入出差信息");

	private final JButton entranceButton = new JButton("导入打卡记录");

	private final JButton exportDetailButton = new JButton("导出详细信息表");

	private final JButton exportTotalButton = new JButton("导出统计总表");

	private JFrame frame;

	private List<AcssBean> acssBeanList = null;

	private Map<String, String> info = null;

	private String tmorning = null;

	private String tnoon_begin = null;

	private String tnoon_end = null;

	private String tnoon_middle = null;

	private String tnoon_grace = null;

	private String tevening = null;

	private String tgrace = null;

	private List<Vacation> vacationList = null;

	private List<WorkDay> workDayList = null;

	private List<EvectionBean> evectionBeanList = null;

	public Acss() {
		this.setTitle("考勤统计系统");
		initProperties();
		initComponents();
		// 添加关闭事件
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initProperties() {
		// 读取所有属性
		info = PropUtil.readProperties();

		// 初始化所有属性，若属性为空，设置默认值
		tmorning = info.get("work.morning.time") == null ? "08:30:00" : info.get("work.morning.time");
		tnoon_begin = info.get("work.noon.time.begin") == null ? "12:00:00" : info.get("work.noon.time.begin");
		tnoon_end = info.get("work.noon.time.end") == null ? "13:00:00" : info.get("work.noon.time.end");
		tnoon_middle = info.get("work.noon.time.middle") == null ? "12:30:00" : info.get("work.noon.time.middle");
		tnoon_grace = info.get("work.noon.grace.time") == null ? "25" : info.get("work.noon.grace.time");
		tevening = info.get("work.evening.time") == null ? "17:30:00" : info.get("work.evening.time");
		tgrace = info.get("work.grace.time") == null ? "5" : info.get("work.grace.time");
	}

	/**
	 * <p>Discription:[根据宽限时间计算最迟的上班打卡时间和最早的下班打卡时间等]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
	 */
	private void initGraceProperties() {
		Date d_tmorning;
		try {
			d_tmorning = DateUtils.addMinutes(DateUtils.parseDate(tmorning, "HH:mm:ss"), Integer.parseInt(tgrace));
		} catch (NumberFormatException e) {
			throw new BizException("上下班宽限时间不为分钟（数字），请检查", e);
		} catch (ParseException e) {
			throw new BizException("上班时间格式错误，请确认格式为HH:mm:ss，如：08:30:00", e);
		}
		tmorning = DateFormatUtils.format(d_tmorning, "HH:mm:ss");
		Date d_tevening;
		try {
			d_tevening = DateUtils.addMinutes(DateUtils.parseDate(tevening, "HH:mm:ss"), -Integer.parseInt(tgrace));
		} catch (NumberFormatException e) {
			throw new BizException("上下班宽限时间不为分钟（数字），请检查", e);
		} catch (ParseException e) {
			throw new BizException("下班时间格式错误，请确认格式为HH:mm:ss，如：17:30:00", e);
		}
		tevening = DateFormatUtils.format(d_tevening, "HH:mm:ss");

		Date d_tnoon_begin;
		try {
			d_tnoon_begin = DateUtils.addMinutes(DateUtils.parseDate(tnoon_begin, "HH:mm:ss"),
					-Integer.parseInt(tnoon_grace));
		} catch (NumberFormatException e) {
			throw new BizException("午休宽限时间不为分钟（数字），请检查", e);
		} catch (ParseException e) {
			throw new BizException("午休开始时间格式错误，请确认格式为HH:mm:ss，如：12:00:00", e);
		}
		tnoon_begin = DateFormatUtils.format(d_tnoon_begin, "HH:mm:ss");

		Date d_tnoon_end;
		try {
			d_tnoon_end = DateUtils.addMinutes(DateUtils.parseDate(tnoon_end, "HH:mm:ss"),
					Integer.parseInt(tnoon_grace));
		} catch (NumberFormatException e) {
			throw new BizException("午休宽限时间不为分钟（数字），请检查", e);
		} catch (ParseException e) {
			throw new BizException("午休结束时间格式错误，请确认格式为HH:mm:ss，如：13:00:00", e);
		}
		tnoon_end = DateFormatUtils.format(d_tnoon_end, "HH:mm:ss");
	}

	private void initComponents() {

		Container frame1ContentPane = getContentPane();
		frame1ContentPane.setLayout(new FlowLayout());

		morningTimePanel.setLayout(new GridLayout());
		morningTimePanel.add(morningTimeLabel);
		morningTimeField.setText(tmorning);
		morningTimePanel.add(morningTimeField);
		frame1ContentPane.add(morningTimePanel);

		eveningTimePanel.setLayout(new GridLayout());
		eveningTimePanel.add(eveningTimeLabel);
		eveningTimeField.setText(tevening);
		eveningTimePanel.add(eveningTimeField);
		frame1ContentPane.add(eveningTimePanel);

		graceTimePanel.setLayout(new GridLayout());
		graceTimePanel.add(graceTimeLabel);
		graceTimeField.setText(tgrace);
		graceTimePanel.add(graceTimeField);
		frame1ContentPane.add(graceTimePanel);

		noonBeginTimePanel.setLayout(new GridLayout());
		noonBeginTimePanel.add(noonBeginTimeLabel);
		noonBeginTimeField.setText(tnoon_begin);
		noonBeginTimePanel.add(noonBeginTimeField);
		frame1ContentPane.add(noonBeginTimePanel);

		noonEndTimePanel.setLayout(new GridLayout());
		noonEndTimePanel.add(noonEndTimeLabel);
		noonEndTimeField.setText(tnoon_end);
		noonEndTimePanel.add(noonEndTimeField);
		frame1ContentPane.add(noonEndTimePanel);

		noonMiddleTimePanel.setLayout(new GridLayout());
		noonMiddleTimePanel.add(noonMiddleTimeLabel);
		noonMiddleTimeField.setText(tnoon_middle);
		noonMiddleTimePanel.add(noonMiddleTimeField);
		frame1ContentPane.add(noonMiddleTimePanel);

		noongraceTimePanel.setLayout(new GridLayout());
		noongraceTimePanel.add(noongraceTimeLabel);
		noongraceTimeField.setText(tnoon_grace);
		noongraceTimePanel.add(noongraceTimeField);
		frame1ContentPane.add(noongraceTimePanel);

		frame1ContentPane.add(sureButton);
		frame1ContentPane.add(vacationButton);
		frame1ContentPane.add(weekendButton);
		frame1ContentPane.add(leaveButton);
		frame1ContentPane.add(evectionButton);
		frame1ContentPane.add(entranceButton);
		frame1ContentPane.add(exportDetailButton);
		frame1ContentPane.add(exportTotalButton);
		sureButton.addActionListener(new ButtonActionListener());
		vacationButton.addActionListener(new ButtonActionListener());
		weekendButton.addActionListener(new ButtonActionListener());
		leaveButton.addActionListener(new ButtonActionListener());
		evectionButton.addActionListener(new ButtonActionListener());
		entranceButton.addActionListener(new ButtonActionListener());
		exportDetailButton.addActionListener(new ButtonActionListener());
		exportTotalButton.addActionListener(new ButtonActionListener());

	}

	class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (e.getActionCommand().equals("确定")) {
					if (morningTimeField.getText() == null || "".equals(morningTimeField.getText().toString())) {
						JOptionPane.showMessageDialog(frame, "上班时间为空，请检查！");
						morningTimeField.setText(tmorning);
						return;
					} else if (noonBeginTimeField.getText() == null
							|| "".equals(noonBeginTimeField.getText().toString())) {
						JOptionPane.showMessageDialog(frame, "午休开始时间为空，请检查！");
						noonBeginTimeField.setText(tnoon_begin);
						return;
					} else if (noonEndTimeField.getText() == null || "".equals(noonEndTimeField.getText().toString())) {
						JOptionPane.showMessageDialog(frame, "午休结束时间为空，请检查！");
						noonEndTimeField.setText(tnoon_end);
						return;
					} else if (noonMiddleTimeField.getText() == null
							|| "".equals(noonMiddleTimeField.getText().toString())) {
						JOptionPane.showMessageDialog(frame, "午休分割时间为空，请检查！");
						noonMiddleTimeField.setText(tnoon_middle);
						return;
					} else if (noongraceTimeField.getText() == null
							|| "".equals(noongraceTimeField.getText().toString())) {
						JOptionPane.showMessageDialog(frame, "午休宽限时间为空，请检查！");
						noongraceTimeField.setText(tnoon_grace);
					} else if (eveningTimeField.getText() == null || "".equals(eveningTimeField.getText().toString())) {
						JOptionPane.showMessageDialog(frame, "下班时间为空，请检查！");
						eveningTimeField.setText(tevening);
						return;
					} else if (graceTimeField.getText() == null || "".equals(graceTimeField.getText().toString())) {
						JOptionPane.showMessageDialog(frame, "上下班宽限时间为空，请检查！");
						graceTimeField.setText(tgrace);
						return;
					} else {
						PropUtil.writeProperties("work.morning.time", morningTimeField.getText(), "");
						PropUtil.writeProperties("work.evening.time", eveningTimeField.getText(), "");
						PropUtil.writeProperties("work.grace.time", graceTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.time.begin", noonBeginTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.time.end", noonEndTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.time.middle", noonMiddleTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.grace.time", noongraceTimeField.getText(), "");
						initProperties();
						JOptionPane.showMessageDialog(frame, "设置成功！");
					}

				} else if (e.getActionCommand().equals("导入法定假期")) {
					JFileChooser fDialog = new JFileChooser();
					int result = fDialog.showOpenDialog(frame);
					if (result == JFileChooser.APPROVE_OPTION) {
						TxtUtil.saveVacations(fDialog.getSelectedFile());
						JOptionPane.showMessageDialog(frame, "导入法定假期文本成功！");
					}
				} else if (e.getActionCommand().equals("导入工作串休信息")) {
					JFileChooser fDialog = new JFileChooser();
					int result = fDialog.showOpenDialog(frame);
					if (result == JFileChooser.APPROVE_OPTION) {
						TxtUtil.saveWorkDays(fDialog.getSelectedFile());
						JOptionPane.showMessageDialog(frame, "导入工作串休信息文本成功！");
					}
				} else if (e.getActionCommand().equals("导入请假信息")) {
					JOptionPane.showMessageDialog(frame, "目前还没有请假信息，请忽略！");
				} else if (e.getActionCommand().equals("导入出差信息")) {
					JOptionPane.showMessageDialog(frame, "目前还没有出差信息，请忽略！");
				} else if (e.getActionCommand().equals("导入打卡记录")) {
					JFileChooser fDialog = new JFileChooser();
					int result = fDialog.showOpenDialog(frame);
					if (result == JFileChooser.APPROVE_OPTION) {
						String path = fDialog.getName(fDialog.getSelectedFile());
						if (path.endsWith(".txt")) {
							// 根据宽限时间重新计算上班下班等时间和中午休息打卡时间。
							initGraceProperties();

							// 读取考勤打卡记录
							acssBeanList = TxtUtil.readAcssBeanFromFile(fDialog.getSelectedFile(), tnoon_begin,
									tnoon_middle, tnoon_end);

							// 读取本年度休假记录
							vacationList = TxtUtil.getVacations();

							// 读取本年度串休记录，正常上班日期
							workDayList = TxtUtil.getWorkDays();

							// 根据法定假日和串休记录，再结合正常周六周日休息，判断AcssBean是正常上班还是休息
							checkVacation(acssBeanList, vacationList, workDayList);

							// 读取本月所有人的外出登记表，存在List<EvectionBean>中
							evectionBeanList = ExcelUtil.parseExcel2EvectionList();

							JOptionPane.showMessageDialog(frame,
									"导入考勤打卡记录成功！\r\n同时也自动导入本月所有人的外出登记表！\r\n现在可以导出详细信息表和统计总表！");
						}
					}
				} else if (e.getActionCommand().equals("导出详细信息表")) {
					if (acssBeanList == null) {
						JOptionPane.showMessageDialog(frame, "还未导入打卡记录，无法导出详细信息表！");
						return;
					} else {
						List<EmployeeDetailBean> employeeDetailBeanList = generateEmployeeDetailList(acssBeanList,
								evectionBeanList);
						Map<String, String> detailMap = EmployeeDetailBean.getDetailMap();
						ExcelUtil.exportEmployeeDetailExcel(employeeDetailBeanList, detailMap);
						JOptionPane.showMessageDialog(frame, "导出成功，请查看: " + Consts.PATH_EMPLOYEEDETAIL);
					}
				} else if (e.getActionCommand().equals("导出统计总表")) {
					if (acssBeanList == null) {
						JOptionPane.showMessageDialog(frame, "还未导入打卡记录，无法导出统计总表！");
						return;
					} else {
						List<EmployeeDetailBean> employeeDetailBeanList = generateEmployeeDetailList(acssBeanList,
								evectionBeanList);
						Map<String, String> totalMap = EmployeeTotalBean.getTotalMap();
						List<EmployeeTotalBean> employeeTotalBeanList = convertDetail2Total(employeeDetailBeanList);
						ExcelUtil.exportEmployeeTotalExcel(employeeTotalBeanList, totalMap);
						JOptionPane.showMessageDialog(frame, "导出成功，请查看: " + Consts.PATH_EMPLOYEETOTAL);
					}
				}
			} catch (BizException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame, ex.getMessage());
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame, "未知错误！");
			}
		}
	}

	/**
	 * 
	 * <p>Discription:[根据法定假日和串休记录，再结合正常周六周日休息，判断AcssBean是正常上班还是休息]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static void checkVacation(List<AcssBean> acssBeanList, List<Vacation> vacationList, List<WorkDay> workDayList) {
		for (AcssBean acssBean : acssBeanList) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(DateUtils.parseDate(acssBean.getDate(), "yyyy-MM-dd"));
			} catch (ParseException e) {
				throw new BizException("考勤文件中日期格式错误，请确认格式为：yyyy-MM-dd，" + acssBean.getDate(), e);
			}

			// 判断是否为周六周日
			if (c.get(Calendar.DAY_OF_WEEK) == 7 || c.get(Calendar.DAY_OF_WEEK) == 1) {
				acssBean.setVacation(true);
				// 如果在串休列表中，直接return flase，不再计算Vacation了。
				for (WorkDay wd : workDayList) {
					if (wd.getDate().compareTo(c.getTime()) == 0) {
						acssBean.setVacation(false);
						break;
					}
				}
			} else {
				// 如果在国家规定的休假列表中，return true。
				for (Vacation vac : vacationList) {
					if (vac.getDate().compareTo(c.getTime()) == 0) {
						acssBean.setVacation(true);
					}
				}
			}
		}
	}

	/**
	 * 
	 * <p>Discription:[通过导入的考勤记录、出差登记表和相关设置计算职工本月详细考勤记录]</p>
	 * Created on 2012-6-30
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<EmployeeDetailBean> generateEmployeeDetailList(List<AcssBean> acssBeanList,
			List<EvectionBean> evectionBeanList) {
		EmployeeDetailBean employeeDetailBean = null;
		List<EmployeeDetailBean> employeeDetailBeanList = new ArrayList<EmployeeDetailBean>();

		String mdate = "";
		for (AcssBean acssBean : acssBeanList) {

			// 新开始一天的打卡记录
			if (!mdate.equals(acssBean.getId() + acssBean.getDate())) {
				if (!mdate.equals("")) {
					employeeDetailBeanList.add(employeeDetailBean);
				}

				employeeDetailBean = new EmployeeDetailBean();
				employeeDetailBean.setDepartment(acssBean.getDepartment());
				employeeDetailBean.setName(acssBean.getName());
				employeeDetailBean.setId(acssBean.getId());
				employeeDetailBean.setDate(acssBean.getDate());
				employeeDetailBean.setTEarly("");
				employeeDetailBean.setTEarly("");
				if (acssBean.isVacation()) {
					// 如果是休息，则认为今天打卡是加班。否则先不处理，等以后再统一处理。
					employeeDetailBean.setStatus(WorkSt.EVECTION);
				}

				// 确保是一个人同一天
				mdate = acssBean.getId() + acssBean.getDate();
			}

			String acssBeanTime = acssBean.getTMorning();
			if (acssBeanTime.compareTo("12:00:00") < 0) {// 上午
				employeeDetailBean.setTEarly(acssBeanTime);
			} else if (acssBeanTime.compareTo("12:00:00") >= 0 && acssBeanTime.compareTo("13:00:00") <= 0) {// 中午
				if (employeeDetailBean.getTEarly().equals("")) {
					employeeDetailBean.setTEarly(acssBeanTime);
				} else {
					employeeDetailBean.setTEarly(acssBeanTime);
				}
			} else if (acssBeanTime.compareTo("13:00:00") > 0) {// 下午
				employeeDetailBean.setTEarly(acssBeanTime);
			}
		}
		employeeDetailBeanList = getEmployeeDetailWorkSt(employeeDetailBeanList);
		return employeeDetailBeanList;
	}

	/**
	 * 
	 * <p>Discription:[通过EmployeeDetailBean的上班时间，下班时间计算EmployeeDetailBean的打卡情况]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<EmployeeDetailBean> getEmployeeDetailWorkSt(List<EmployeeDetailBean> employeeDetailBeanList) {

		// 读取所有设置的信息。
		String beginTime = info.get("work.begin.time");
		String endTime = info.get("work.end.time");

		for (EmployeeDetailBean employeeDetailBean : employeeDetailBeanList) {
			if (employeeDetailBean.getStatus() != null
					&& employeeDetailBean.getStatus().compareTo(WorkSt.EVECTION) == 0) {
				continue;
			}

			if ("".equals(employeeDetailBean.getTEarly())) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else if ("".equals(employeeDetailBean.getTEarly())) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else if (employeeDetailBean.getTEarly().compareTo(beginTime) > 0) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else if (employeeDetailBean.getTEarly().compareTo(endTime) < 0) {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			} else {
				employeeDetailBean.setStatus(WorkSt.EVECTION);
			}
		}
		return employeeDetailBeanList;
	}

	/**
	 * 
	 * <p>Discription:[通过employeeDetailBeanList计算出employeeTotalBeanList]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws IOException, ParseException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static List<EmployeeTotalBean> convertDetail2Total(List<EmployeeDetailBean> employeeDetailBeanList) {
		List<EmployeeTotalBean> employeeTotalBeanList = new ArrayList<EmployeeTotalBean>();
		EmployeeTotalBean employeeTotalBean = null;
		int id = 0;
		int c_late = 0;
		int c_early = 0;
		for (EmployeeDetailBean employeeDetailBean : employeeDetailBeanList) {
			if (id != employeeDetailBean.getId()) {
				if (id != 0) {
					employeeTotalBean.setC_late(c_late);
					employeeTotalBean.setC_early(c_early);
					employeeTotalBeanList.add(employeeTotalBean);
				}
				employeeTotalBean = new EmployeeTotalBean();
				employeeTotalBean.setDepartment(employeeDetailBean.getDepartment());
				employeeTotalBean.setName(employeeDetailBean.getName());
				employeeTotalBean.setId(employeeDetailBean.getId());
				// 换人了
				c_late = 0;
				c_early = 0;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {
				c_late++;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {
				c_early++;
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {
			}
			if (employeeDetailBean.getStatus().compareTo(WorkSt.LEAVE) == 0) {

				// 判断，若上班或下班时间未打卡，但是因为是加班，就算成是属性设置中的上班时间和下班时间
				if (employeeDetailBean.getTEarly().equals("")) {
					employeeDetailBean.setTEarly("");
				}
				if (employeeDetailBean.getTEarly().equals("")) {
					employeeDetailBean.setTEarly("");
				}

				// 计算两个时间的小时差，也就是加班多少时间
				c_early += minusDate(employeeDetailBean.getTEarly(), employeeDetailBean.getTEarly());
			}
			id = employeeDetailBean.getId();
		}
		return employeeTotalBeanList;
	}

	/**
	 * <p>Discription:[计算两个时间之间相差多少小时]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public static int minusDate(String beginTime, String endTime) {
		long begin = 0;
		long end = 0;
		try {
			begin = DateUtils.parseDate(beginTime, "HH:mm:ss").getTime();
			end = DateUtils.parseDate(endTime, "HH:mm:ss").getTime();
		} catch (ParseException e) {
			throw new BizException("计算日期相差时间出错:" + beginTime + "，" + endTime, e);
		}
		return (int) ((end - begin) / (1000 * 60 * 60)) + 1;
	}

	public static void main(String args[]) {
		Acss a = new Acss();
		a.setSize(500, 300);
		a.setVisible(true);
	}
}
