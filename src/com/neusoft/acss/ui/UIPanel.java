package com.neusoft.acss.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.command.ButtonCommandContext;
import com.neusoft.acss.util.PropUtil;

/**
 * <p> Title: [构造主窗体的UI界面]</p>
 * <p> Description: [用来构造主窗体的界面。里面有各个文本框，按钮]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class UIPanel extends JFrame {

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

	private final JButton entranceButton = new JButton("导入考勤记录");

	private final JButton exportDetailButton = new JButton("导出详细信息表");

	private final JButton exportTotalButton = new JButton("导出统计总表");

	private Map<String, String> p = null;

	private String tmorning = null;

	private String tnoon_begin = null;

	private String tnoon_end = null;

	private String tnoon_middle = null;

	private String tnoon_grace = null;

	private String tevening = null;

	private String tgrace = null;

	private Info info = new Info();

	public UIPanel() {
		initProperties();
		initComponents();
	}

	public void initComponents() {
		Container cp = this.getContentPane();
		cp.setLayout(new FlowLayout());
		morningTimePanel.setLayout(new GridLayout());
		morningTimePanel.add(morningTimeLabel);
		morningTimeField.setText(tmorning);
		morningTimePanel.add(morningTimeField);
		cp.add(morningTimePanel);

		eveningTimePanel.setLayout(new GridLayout());
		eveningTimePanel.add(eveningTimeLabel);
		eveningTimeField.setText(tevening);
		eveningTimePanel.add(eveningTimeField);
		cp.add(eveningTimePanel);

		graceTimePanel.setLayout(new GridLayout());
		graceTimePanel.add(graceTimeLabel);
		graceTimeField.setText(tgrace);
		graceTimePanel.add(graceTimeField);
		cp.add(graceTimePanel);

		noonBeginTimePanel.setLayout(new GridLayout());
		noonBeginTimePanel.add(noonBeginTimeLabel);
		noonBeginTimeField.setText(tnoon_begin);
		noonBeginTimePanel.add(noonBeginTimeField);
		cp.add(noonBeginTimePanel);

		noonEndTimePanel.setLayout(new GridLayout());
		noonEndTimePanel.add(noonEndTimeLabel);
		noonEndTimeField.setText(tnoon_end);
		noonEndTimePanel.add(noonEndTimeField);
		cp.add(noonEndTimePanel);

		noonMiddleTimePanel.setLayout(new GridLayout());
		noonMiddleTimePanel.add(noonMiddleTimeLabel);
		noonMiddleTimeField.setText(tnoon_middle);
		noonMiddleTimePanel.add(noonMiddleTimeField);
		cp.add(noonMiddleTimePanel);

		noongraceTimePanel.setLayout(new GridLayout());
		noongraceTimePanel.add(noongraceTimeLabel);
		noongraceTimeField.setText(tnoon_grace);
		noongraceTimePanel.add(noongraceTimeField);
		cp.add(noongraceTimePanel);

		ButtonActionListener listener = new ButtonActionListener();
		sureButton.addActionListener(listener);
		vacationButton.addActionListener(listener);
		weekendButton.addActionListener(listener);
		leaveButton.addActionListener(listener);
		evectionButton.addActionListener(listener);
		entranceButton.addActionListener(listener);
		exportDetailButton.addActionListener(listener);
		exportTotalButton.addActionListener(listener);

		cp.add(sureButton);
		cp.add(vacationButton);
		cp.add(weekendButton);
		cp.add(leaveButton);
		cp.add(evectionButton);
		cp.add(entranceButton);
		cp.add(exportDetailButton);
		cp.add(exportTotalButton);

	}

	public void initProperties() {
		// 读取所有属性
		p = PropUtil.readProperties();

		// 初始化所有属性，若属性为空，设置默认值
		tmorning = p.get("work.morning.time") == null ? "08:30:00" : p.get("work.morning.time");
		tnoon_begin = p.get("work.noon.time.begin") == null ? "12:00:00" : p.get("work.noon.time.begin");
		tnoon_end = p.get("work.noon.time.end") == null ? "13:00:00" : p.get("work.noon.time.end");
		tnoon_middle = p.get("work.noon.time.middle") == null ? "12:30:00" : p.get("work.noon.time.middle");
		tnoon_grace = p.get("work.noon.grace.time") == null ? "25" : p.get("work.noon.grace.time");
		tevening = p.get("work.evening.time") == null ? "17:30:00" : p.get("work.evening.time");
		tgrace = p.get("work.grace.time") == null ? "5" : p.get("work.grace.time");
	}

	/**
	 * <p> Title: [按钮监听类]</p>
	 * <p> Description: [各个按钮监听类]</p>
	 * <p> Created on 2012-7-19</p>
	 * <p> Copyright: Copyright (c) 2012</p>
	 * <p> Company: 东软集团股份有限公司</p>
	 * @author 杨光 - yang.guang@neusoft.com
	 * @version 1.0
	 */
	class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ButtonCommandContext acc = new ButtonCommandContext(e.getActionCommand(), UIPanel.this, info);
			try {
				info = acc.handleRequest();
				String message = info.getMessage();
				if (message != null) {
					JOptionPane.showMessageDialog(UIPanel.this, message, "提示信息", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (BizException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(UIPanel.this, ex.getMessage(), "错误信息", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(UIPanel.this, ex.getMessage(), "未知错误", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public String getTmorning() {
		return tmorning;
	}

	public void setTmorning(String tmorning) {
		this.tmorning = tmorning;
	}

	public String getTnoon_begin() {
		return tnoon_begin;
	}

	public void setTnoon_begin(String tnoon_begin) {
		this.tnoon_begin = tnoon_begin;
	}

	public String getTnoon_end() {
		return tnoon_end;
	}

	public void setTnoon_end(String tnoon_end) {
		this.tnoon_end = tnoon_end;
	}

	public String getTnoon_middle() {
		return tnoon_middle;
	}

	public void setTnoon_middle(String tnoon_middle) {
		this.tnoon_middle = tnoon_middle;
	}

	public String getTnoon_grace() {
		return tnoon_grace;
	}

	public void setTnoon_grace(String tnoon_grace) {
		this.tnoon_grace = tnoon_grace;
	}

	public String getTevening() {
		return tevening;
	}

	public void setTevening(String tevening) {
		this.tevening = tevening;
	}

	public String getTgrace() {
		return tgrace;
	}

	public void setTgrace(String tgrace) {
		this.tgrace = tgrace;
	}

	public JPanel getMorningTimePanel() {
		return morningTimePanel;
	}

	public JPanel getNoonBeginTimePanel() {
		return noonBeginTimePanel;
	}

	public JPanel getNoonEndTimePanel() {
		return noonEndTimePanel;
	}

	public JPanel getNoonMiddleTimePanel() {
		return noonMiddleTimePanel;
	}

	public JPanel getNoongraceTimePanel() {
		return noongraceTimePanel;
	}

	public JPanel getEveningTimePanel() {
		return eveningTimePanel;
	}

	public JPanel getGraceTimePanel() {
		return graceTimePanel;
	}

	public JLabel getMorningTimeLabel() {
		return morningTimeLabel;
	}

	public JLabel getNoonBeginTimeLabel() {
		return noonBeginTimeLabel;
	}

	public JLabel getNoonEndTimeLabel() {
		return noonEndTimeLabel;
	}

	public JLabel getNoonMiddleTimeLabel() {
		return noonMiddleTimeLabel;
	}

	public JLabel getNoongraceTimeLabel() {
		return noongraceTimeLabel;
	}

	public JLabel getEveningTimeLabel() {
		return eveningTimeLabel;
	}

	public JLabel getGraceTimeLabel() {
		return graceTimeLabel;
	}

	public JTextField getMorningTimeField() {
		return morningTimeField;
	}

	public JTextField getNoonBeginTimeField() {
		return noonBeginTimeField;
	}

	public JTextField getNoonEndTimeField() {
		return noonEndTimeField;
	}

	public JTextField getNoonMiddleTimeField() {
		return noonMiddleTimeField;
	}

	public JTextField getNoongraceTimeField() {
		return noongraceTimeField;
	}

	public JTextField getEveningTimeField() {
		return eveningTimeField;
	}

	public JTextField getGraceTimeField() {
		return graceTimeField;
	}

	public JButton getSureButton() {
		return sureButton;
	}

	public JButton getVacationButton() {
		return vacationButton;
	}

	public JButton getWeekendButton() {
		return weekendButton;
	}

	public JButton getLeaveButton() {
		return leaveButton;
	}

	public JButton getEvectionButton() {
		return evectionButton;
	}

	public JButton getEntranceButton() {
		return entranceButton;
	}

	public JButton getExportDetailButton() {
		return exportDetailButton;
	}

	public JButton getExportTotalButton() {
		return exportTotalButton;
	}
}
