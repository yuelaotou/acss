package com.kd.acss;

import javax.swing.JFrame;

import com.kd.acss.ui.UIPanel;

/**
 * <p> Title: [考勤统计软件主窗口]</p>
 * <p> Description: [系统主窗口。是整个系统的入口类]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class Acss extends JFrame {

	private static final long serialVersionUID = 1L;

	public Acss() {
		this.setTitle("考勤统计系统");
		UIPanel ui = new UIPanel();
		this.setContentPane(ui.getContentPane());

		// 添加关闭事件
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String args[]) {
		Acss a = new Acss();
		a.setBounds(400, 200, 500, 300);
		a.setVisible(true);
	}
}
