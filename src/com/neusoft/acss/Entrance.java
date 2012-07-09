package com.neusoft.acss;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.neusoft.acss.bean.EmployeeDetailBean;
import com.neusoft.acss.bean.EmployeeTotalBean;
import com.neusoft.acss.bean.EntranceBean;
import com.neusoft.acss.bs.Business;
import com.neusoft.acss.consts.FileConst;

public class Entrance extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JPanel beginTimePanel = new JPanel();

	private final JLabel beginTimeLabel = new JLabel("上班时间设定：");

	private final JTextField beginTimeField = new JTextField();

	private final JPanel endTimePanel = new JPanel();

	private final JLabel endTimeLabel = new JLabel("下班时间设定：");

	private final JTextField endTimeField = new JTextField();

	private final JButton sureButton = new JButton("确定");

	private final JButton vacationButton = new JButton("导入法定假期");

	private final JButton weekendButton = new JButton("导入工作串休信息");

	private final JButton leaveButton = new JButton("导入请假信息");

	private final JButton evectionButton = new JButton("导入出差信息");

	private final JButton entranceButton = new JButton("导入打卡记录");

	private final JButton exportDetailButton = new JButton("导出详细信息表");

	private final JButton exportTotalButton = new JButton("导出统计总表");

	private JFrame frame;

	private List<EntranceBean> entranceBeanList = null;

	public Entrance() {
		this.setTitle("考勤统计系统");
		initComponents();
		// 添加关闭事件
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initComponents() {

		Container frame1ContentPane = getContentPane();
		frame1ContentPane.setLayout(new FlowLayout());

		beginTimePanel.setLayout(new GridLayout());
		beginTimePanel.add(beginTimeLabel);
		beginTimeField.setText(Business.readValue("work.begin.time"));
		beginTimePanel.add(beginTimeField);
		frame1ContentPane.add(beginTimePanel);

		endTimePanel.setLayout(new GridLayout());
		endTimePanel.add(endTimeLabel);
		endTimeField.setText(Business.readValue("work.end.time"));
		endTimePanel.add(endTimeField);
		frame1ContentPane.add(endTimePanel);

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
				if (beginTimeField.getText() == null || "".equals(beginTimeField.getText().toString())) {
					JOptionPane.showMessageDialog(frame, "上班时间为空，请检查！");
					beginTimeField.setText(Business.readValue("work.begin.time"));
					return;
				} else if (endTimeField.getText() == null || "".equals(endTimeField.getText().toString())) {
					JOptionPane.showMessageDialog(frame, "下班时间为空，请检查！");
					endTimeField.setText(Business.readValue("work.end.time"));
					return;
				} else {
					try {
						Business.writeProperties("work.begin.time", beginTimeField.getText(), "");
						Business.writeProperties("work.end.time", endTimeField.getText(), "");
						JOptionPane.showMessageDialog(frame, "设置成功！");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			} else if (e.getActionCommand().equals("导入法定假期")) {
				JFileChooser fDialog = new JFileChooser();
				int result = fDialog.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						Business.saveVacations(fDialog.getSelectedFile());
						JOptionPane.showMessageDialog(frame, "导入法定假期文本成功！");
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			} else if (e.getActionCommand().equals("导入工作串休信息")) {
				JFileChooser fDialog = new JFileChooser();
				int result = fDialog.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						Business.saveWorkDates(fDialog.getSelectedFile());
						JOptionPane.showMessageDialog(frame, "导入工作串休信息文本成功！");
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
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
						if (path.endsWith(".xls")) {
							entranceBeanList = Business.ParseExcel2003(fDialog.getSelectedFile());
							JOptionPane.showMessageDialog(frame, "导入成功，可以查看导出统计报表！");
						} else if (path.endsWith(".xlsx")) {
							entranceBeanList = Business.ParseExcel2007(fDialog.getSelectedFile());
							JOptionPane.showMessageDialog(frame, "导入成功，可以查看导出统计报表！");
						} else {
							JOptionPane.showMessageDialog(frame, "导入的文件不是Excel2003或2007版本，请确认！");
							return;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				}
			} else if (e.getActionCommand().equals("导出详细信息表")) {
				if (entranceBeanList == null) {
					JOptionPane.showMessageDialog(frame, "还未导入打卡记录，无法导出详细信息表！");
					return;
				} else {
					List<EmployeeDetailBean> employeeDetailBeanList = Business
							.generateEmployeeDetailList(entranceBeanList);
					try {
						Business.GenerateDetailExcel(FileConst.PATH_EMPLOYEEDETAIL, employeeDetailBeanList);
						JOptionPane.showMessageDialog(frame, "导出成功，请查看: " + FileConst.PATH_EMPLOYEEDETAIL);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			} else if (e.getActionCommand().equals("导出统计总表")) {
				if (entranceBeanList == null) {
					JOptionPane.showMessageDialog(frame, "还未导入打卡记录，无法导出统计总表！");
					return;
				} else {
					List<EmployeeDetailBean> employeeDetailBeanList = Business
							.generateEmployeeDetailList(entranceBeanList);
					try {
						List<EmployeeTotalBean> employeeTotalBeanList = Business
								.ConvertDetail2Total(employeeDetailBeanList);
						Business.GenerateTotalExcel(FileConst.PATH_EMPLOYEETOTAL, employeeTotalBeanList);
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

	public static void main(String args[]) {
		Entrance entrance = new Entrance();
		entrance.setSize(500, 300);
		entrance.setVisible(true);
	}
}
