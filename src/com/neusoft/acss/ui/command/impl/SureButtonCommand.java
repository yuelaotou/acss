package com.neusoft.acss.ui.command.impl;

import org.apache.commons.lang3.StringUtils;

import com.neusoft.acss.bean.Info;
import com.neusoft.acss.consts.Consts;
import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;
import com.neusoft.acss.ui.command.IButtonCommand;
import com.neusoft.acss.util.PropUtil;

/**
 * <p> Title: [确定按钮]</p>
 * <p> Description: [设置参数按钮，比如上下班时间，宽限时间等内容]</p>
 * <p> Created on 2012-7-19</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * <p> Company: 东软集团股份有限公司</p>
 * @author 杨光 - yang.guang@neusoft.com
 * @version 1.0
 */
public class SureButtonCommand implements IButtonCommand {

	private UIPanel ui = null;

	private Info info = null;

	public SureButtonCommand(UIPanel ui, Info info) {
		this.ui = ui;
		this.info = info;
	}

	@Override
	public Info handleRequest() throws BizException, Exception {
		if (!ui.getMorningTimeField().getText().matches(Consts.REGEX_TIME)) {
			throw new BizException("上班时间格式不正确，请检查。格式为：HH:mm:ss，如：08:30:00");
		} else if (!ui.getEveningTimeField().getText().matches(Consts.REGEX_TIME)) {
			throw new BizException("下班时间格式不正确，请检查。格式为：HH:mm:ss，如：17:30:00");
		} else if (!StringUtils.isNumeric(ui.getGraceTimeField().getText())) {
			throw new BizException("上下班宽限时间不为整数字，请检查！");
		} else if (!ui.getNoonBeginTimeField().getText().matches(Consts.REGEX_TIME)) {
			throw new BizException("午休开始时间格式不正确，请检查。格式为：HH:mm:ss，如：12:00:00");
		} else if (!ui.getNoonEndTimeField().getText().matches(Consts.REGEX_TIME)) {
			throw new BizException("午休结束时间格式不正确，请检查。格式为：HH:mm:ss，如：13:00:00");
		} else if (!ui.getNoonMiddleTimeField().getText().matches(Consts.REGEX_TIME)) {
			throw new BizException("午休分割时间格式不正确，请检查。格式为：HH:mm:ss，如：12:30:00");
		} else if (!StringUtils.isNumeric(ui.getNoongraceTimeField().getText())) {
			throw new BizException("午休宽限时间不为整数字，请检查！");
		} else {
			PropUtil.writeProperties("work.morning.time", ui.getMorningTimeField().getText(), "");
			PropUtil.writeProperties("work.evening.time", ui.getEveningTimeField().getText(), "");
			PropUtil.writeProperties("work.grace.time", ui.getGraceTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.time.begin", ui.getNoonBeginTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.time.end", ui.getNoonEndTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.time.middle", ui.getNoonMiddleTimeField().getText(), "");
			PropUtil.writeProperties("work.noon.grace.time", ui.getNoongraceTimeField().getText(), "");
			info.setMessage("设置成功！");
		}
		return info;
	}

}
