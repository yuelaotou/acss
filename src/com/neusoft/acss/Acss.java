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
import com.neusoft.acss.bean.Vacation;
import com.neusoft.acss.bean.WorkDay;
import com.neusoft.acss.consts.FileConst;
import com.neusoft.acss.enums.WorkStatus;
import com.neusoft.acss.util.ExcelUtil;
import com.neusoft.acss.util.PropUtil;
import com.neusoft.acss.util.TxtUtil;

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

	public Acss() {
		this.setTitle("考勤统计系统");
		try {
			initProperties();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initComponents();
		// 添加关闭事件
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initProperties() throws IOException {
		// 读取所有属性
		info = PropUtil.readProperties();

		tmorning = info.get("work.morning.time") == null ? "08:30:00" : info.get("work.morning.time");
		tnoon_begin = info.get("work.noon.time.begin") == null ? "12:00:00" : info.get("work.noon.time.begin");
		tnoon_end = info.get("work.noon.time.end") == null ? "13:00:00" : info.get("work.noon.time.end");
		tnoon_middle = info.get("work.noon.time.middle") == null ? "12:30:00" : info.get("work.noon.time.middle");
		tnoon_grace = info.get("work.noon.grace.time") == null ? "25" : info.get("work.noon.grace.time");
		tevening = info.get("work.evening.time") == null ? "17:30:00" : info.get("work.evening.time");
		tgrace = info.get("work.grace.time") == null ? "5" : info.get("work.grace.time");

	}

	/**
	 * 
	 * <p>Discription:[根据宽限时间计算最迟的上班打卡时间和最早的下班打卡时间等]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
	 */
	private void initGraceProperties() throws NumberFormatException, ParseException {

		Date d_tmorning = DateUtils.addMinutes(DateUtils.parseDate(tmorning, "HH:mm:ss"), Integer.parseInt(tgrace));
		tmorning = DateFormatUtils.format(d_tmorning, "HH:mm:ss");

		Date d_tevening = DateUtils.addMinutes(DateUtils.parseDate(tevening, "HH:mm:ss"), -Integer.parseInt(tgrace));
		tevening = DateFormatUtils.format(d_tevening, "HH:mm:ss");

		Date d_tnoon_begin = DateUtils.addMinutes(DateUtils.parseDate(tnoon_begin, "HH:mm:ss"),
				-Integer.parseInt(tnoon_grace));
		tnoon_begin = DateFormatUtils.format(d_tnoon_begin, "HH:mm:ss");

		Date d_tnoon_end = DateUtils.addMinutes(DateUtils.parseDate(tnoon_end, "HH:mm:ss"),
				Integer.parseInt(tnoon_grace));
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
			if (e.getActionCommand().equals("确定")) {
				if (morningTimeField.getText() == null || "".equals(morningTimeField.getText().toString())) {
					JOptionPane.showMessageDialog(frame, "上班时间为空，请检查！");
					morningTimeField.setText(tmorning);
					return;
				} else if (noonBeginTimeField.getText() == null || "".equals(noonBeginTimeField.getText().toString())) {
					JOptionPane.showMessageDialog(frame, "午休开始时间为空，请检查！");
					noonBeginTimeField.setText(tnoon_begin);
					return;
				} else if (noonEndTimeField.getText() == null || "".equals(noonEndTimeField.getText().toString())) {
					JOptionPane.showMessageDialog(frame, "午休结束时间为空，请检查！");
					noonEndTimeField.setText(tnoon_end);
					return;
				} else if (noonMiddleTimeField.getText() == null || "".equals(noonMiddleTimeField.getText().toString())) {
					JOptionPane.showMessageDialog(frame, "午休分割时间为空，请检查！");
					noonMiddleTimeField.setText(tnoon_middle);
					return;
				} else if (noongraceTimeField.getText() == null || "".equals(noongraceTimeField.getText().toString())) {
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
					try {
						PropUtil.writeProperties("work.morning.time", morningTimeField.getText(), "");
						PropUtil.writeProperties("work.evening.time", eveningTimeField.getText(), "");
						PropUtil.writeProperties("work.grace.time", graceTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.time.begin", noonBeginTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.time.end", noonEndTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.time.middle", noonMiddleTimeField.getText(), "");
						PropUtil.writeProperties("work.noon.grace.time", noongraceTimeField.getText(), "");
						initProperties();
						JOptionPane.showMessageDialog(frame, "设置成功！");
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					}
				}

			} else if (e.getActionCommand().equals("导入法定假期")) {
				JFileChooser fDialog = new JFileChooser();
				int result = fDialog.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						TxtUtil.saveVacations(fDialog.getSelectedFile());
						JOptionPane.showMessageDialog(frame, "导入法定假期文本成功！");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else if (e.getActionCommand().equals("导入工作串休信息")) {
				JFileChooser fDialog = new JFileChooser();
				int result = fDialog.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						TxtUtil.saveWorkDays(fDialog.getSelectedFile());
						JOptionPane.showMessageDialog(frame, "导入工作串休信息文本成功！");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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
					try {
						if (path.endsWith(".txt")) {
							initGraceProperties();
							acssBeanList = TxtUtil.readAcssBeanFromFile(fDialog.getSelectedFile(), tnoon_begin,
									tnoon_middle, tnoon_end);
							List<Vacation> vacationList = TxtUtil.getVacations();
							List<WorkDay> workDayList = TxtUtil.getWorkDays();
							System.out.println("a.." + acssBeanList.size());
							checkVacation(acssBeanList, vacationList, workDayList);
							System.out.println("b.." + acssBeanList.size());
							for(AcssBean acssBean:acssBeanList){
								System.out.println(acssBean);
							}
							JOptionPane.showMessageDialog(frame, "导入成功，可以查看导出统计报表！");
							// } else if (path.endsWith(".xls")) {
							// acssBeanList = ExcelUtil.ParseExcel2003(fDialog.getSelectedFile());
							// JOptionPane.showMessageDialog(frame, "导入成功，可以查看导出统计报表！");
							// } else if (path.endsWith(".xlsx")) {
							// acssBeanList = ExcelUtil.ParseExcel2007(fDialog.getSelectedFile());
							// JOptionPane.showMessageDialog(frame, "导入成功，可以查看导出统计报表！");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			} else if (e.getActionCommand().equals("导出详细信息表")) {
				if (acssBeanList == null) {
					JOptionPane.showMessageDialog(frame, "还未导入打卡记录，无法导出详细信息表！");
					return;
				} else {
					List<EmployeeDetailBean> employeeDetailBeanList = generateEmployeeDetailList(acssBeanList);
					try {
						ExcelUtil.GenerateDetailExcel(FileConst.PATH_EMPLOYEEDETAIL, employeeDetailBeanList);
						JOptionPane.showMessageDialog(frame, "导出成功，请查看: " + FileConst.PATH_EMPLOYEEDETAIL);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else if (e.getActionCommand().equals("导出统计总表")) {
				if (acssBeanList == null) {
					JOptionPane.showMessageDialog(frame, "还未导入打卡记录，无法导出统计总表！");
					return;
				} else {
					List<EmployeeDetailBean> employeeDetailBeanList = generateEmployeeDetailList(acssBeanList);
					try {
						List<EmployeeTotalBean> employeeTotalBeanList = ExcelUtil
								.ConvertDetail2Total(employeeDetailBeanList);
						ExcelUtil.GenerateTotalExcel(FileConst.PATH_EMPLOYEETOTAL, employeeTotalBeanList);
						JOptionPane.showMessageDialog(frame, "导出成功，请查看: " + FileConst.PATH_EMPLOYEETOTAL);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 
	 * <p>Discription:[判断AcssBean是正常上班还是休息]</p>
	 * Created on 2012-7-9
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 * @throws:
	 */
	public static List<AcssBean> checkVacation(List<AcssBean> acssBeanList, List<Vacation> vacationList,
			List<WorkDay> workDayList) throws ParseException {
		for (AcssBean acssBean : acssBeanList) {
			Calendar c = Calendar.getInstance();
			c.setTime(DateUtils.parseDate(acssBean.getDate(), "yyyy-MM-dd"));

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
		return acssBeanList;
	}

	/**
	 * 
	 * <p>Discription:[通过导入的考勤记录和相关设置计算职工本月详细考勤记录]</p>
	 * Created on 2012-6-30
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<EmployeeDetailBean> generateEmployeeDetailList(List<AcssBean> acssBeanList) {
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
				employeeDetailBean.setBeginTime("");
				employeeDetailBean.setEndTime("");
				if (acssBean.isVacation()) {
					// 如果是休息，则认为今天打卡是加班。否则先不处理，等以后再统一处理。
					employeeDetailBean.setStatus(WorkStatus.JIABAN);
				}

				// 确保是一个人同一天
				mdate = acssBean.getId() + acssBean.getDate();
			}

			String acssBeanTime = acssBean.getTMorning();
			if (acssBeanTime.compareTo("12:00:00") < 0) {// 上午
				employeeDetailBean.setBeginTime(acssBeanTime);
			} else if (acssBeanTime.compareTo("12:00:00") >= 0 && acssBeanTime.compareTo("13:00:00") <= 0) {// 中午
				if (employeeDetailBean.getBeginTime().equals("")) {
					employeeDetailBean.setBeginTime(acssBeanTime);
				} else {
					employeeDetailBean.setEndTime(acssBeanTime);
				}
			} else if (acssBeanTime.compareTo("13:00:00") > 0) {// 下午
				employeeDetailBean.setEndTime(acssBeanTime);
			}
		}
		employeeDetailBeanList = getEmployeeDetailWorkStatus(employeeDetailBeanList);
		return employeeDetailBeanList;
	}

	/**
	 * 
	 * <p>Discription:[通过EmployeeDetailBean的上班时间，下班时间计算EmployeeDetailBean的打卡情况]</p>
	 * Created on 2012-7-2
	 * @author: 杨光 - yang.guang@neusoft.com
	 * @update: [日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<EmployeeDetailBean> getEmployeeDetailWorkStatus(List<EmployeeDetailBean> employeeDetailBeanList) {

		// 读取所有设置的信息。
		String beginTime = info.get("work.begin.time");
		String endTime = info.get("work.end.time");

		for (EmployeeDetailBean employeeDetailBean : employeeDetailBeanList) {
			if (employeeDetailBean.getStatus() != null
					&& employeeDetailBean.getStatus().compareTo(WorkStatus.JIABAN) == 0) {
				continue;
			}

			if ("".equals(employeeDetailBean.getBeginTime())) {
				employeeDetailBean.setStatus(WorkStatus.CHIDAO);
			} else if ("".equals(employeeDetailBean.getEndTime())) {
				employeeDetailBean.setStatus(WorkStatus.ZAOTUI);
			} else if (employeeDetailBean.getBeginTime().compareTo(beginTime) > 0) {
				employeeDetailBean.setStatus(WorkStatus.CHIDAO);
			} else if (employeeDetailBean.getEndTime().compareTo(endTime) < 0) {
				employeeDetailBean.setStatus(WorkStatus.ZAOTUI);
			} else {
				employeeDetailBean.setStatus(WorkStatus.ZHENGCHANG);
			}
		}
		return employeeDetailBeanList;
	}

	public static void main(String args[]) {
		Acss a = new Acss();
		a.setSize(500, 300);
		a.setVisible(true);
	}
}
