package com.neusoft.acss.ui.command;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.command.impl.ButtonCommandImpl;
import com.neusoft.acss.util.TxtUtil;

/**
 * <p> Title: [导入法定假期按钮]</p>
 * <p> Description: [目前是自动导入的，虽然已经实现功能,但是所以此方法可以忽略]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class VacationButtonCommand implements ButtonCommandImpl {

	private Info info = null;

	public VacationButtonCommand(Info info) {
		this.info = info;
	}

	@Override
	public Info handleRequest() throws BizException, Exception {

		JFileChooser fDialog = new JFileChooser();
		int result = fDialog.showOpenDialog(new JFrame());
		if (result == JFileChooser.APPROVE_OPTION) {
			TxtUtil.saveVacations(fDialog.getSelectedFile());
			info.setMessage("导入法定假期文本成功！");
		} else {
			info.setMessage(null);
		}
		return info;
	}

}
