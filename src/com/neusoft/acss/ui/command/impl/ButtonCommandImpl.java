package com.neusoft.acss.ui.command.impl;

import com.neusoft.acss.exception.BizException;
import com.neusoft.acss.ui.UIPanel;

public interface ButtonCommandImpl {

	public abstract UIPanel handleRequest() throws BizException, Exception;
}
